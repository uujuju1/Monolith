package flow.world.blocks.payload;

import arc.*;
import arc.util.*;
import arc.math.*;
import arc.struct.*;
import arc.util.io.*;
import arc.scene.ui.*;
import arc.graphics.*;
import arc.scene.style.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.*;
import mindustry.world.consumers.*;
import mindustry.world.blocks.payloads.*;
import flow.world.meta.*;

public class PayloadCrafter extends PayloadBlock {
	public Seq<PayloadRecipe> recipes = new Seq<>();
	public Effect 
	craftEffect = Fx.none,
	updateEffect = Fx.none;
	public float updateEffectChance = 0.04f;

	public PayloadCrafter(String name) {
		super(name);
		solid = destructible = rotate = true;
		outputsPayload = configurable = true;

		consume(new ConsumeItemDynamic((PayloadCrafterBuild e) -> e.currentPlan != -1 ? e.getRecipe().requirements : ItemStack.empty));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.output, FlowStatValues.payloadRecipe(recipes));
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{region, outRegion, topRegion};
	}

	public class PayloadRecipe {
		public @Nullable Block input = null;
		public Block output = Blocks.router;
		public ItemStack[] requirements = ItemStack.empty;
		public float craftTime = 60f;

		public PayloadRecipe(Block output, ItemStack[] requirements, float craftTime) {
			this.output = output;
			this.requirements = requirements;
			this.craftTime = craftTime;
		}
		public PayloadRecipe(Block input, Block output, ItemStack[] requirements, float craftTime) {
			this.output = output;
			this.requirements = requirements;
			this.craftTime = craftTime;
		}
	}

	public class PayloadCrafterBuild extends PayloadBlockBuild<BuildPayload> {
		public float progress, totalProgress, warmup;
		public int currentPlan = -1;

		public PayloadRecipe getRecipe() {return currentPlan == -1 ? null : recipes.get(currentPlan);}

		public float currentTime() {
			return currentPlan == -1 ? 0f : plans.get(currentPlan).craftTime;
		}

		@Override
		public void buildConfiguration(Table table) {
			Seq<Block> blocks = Seq.with(recipes).map(r -> r.output).filter(b -> b.unlockedNow());

			if (blocks.any()) {
				ItemSelection.buildTable(PayloadCrafter.this, table, blocks, () -> getRecipe() == null ? null : getRecipe().output, block -> currentPlan = recipes.indexOf(r -> r.output == block));
			} else {
				table.table(Styles.black3, t -> t.add("").color(Color.lightGray));
			}
		}

		@Override
		public boolean acceptItem(Building source, Item item) {
			if (getRecipe() != null) return false;
			return Structs.contains(getRecipe().requirements, stack -> stack.item == item && items.get(item) < stack.amount * 2);
		}

		@Override
		public void draw() {
			Draw.rect(region, x, y, 0f);
			if (getRecipe() != null) for(int i = 0; i < 4; i++) if(blends(i) && i != rotation) Draw.rect(inRegion, x, y, (i * 90) - 180);
			Draw.rect(outRegion, x, y, rotdeg());
			Draw.z(Layer.blockOver);
			drawPayload();
			Draw.z(Layer.blockOver + 0.1f);
			Draw.rect(topRegion, x, y, 0f);
		}

		@Override
		public boolean shouldConsume() {
			if (getRecipe() == null) return false;
			if (getRecipe().input != null && payload != null) if (((BuildPayload) payload).build.block != getRecipe().input) return false; 
			return enabled;
		}

		@Override
		public void updateTile() {
			if (efficiency > 0)  {
				warmup = Mathf.approachDelta(warmup, 1f, 0.016f);
				progress += getProgressIncrease(getRecipe().craftTime) * warmup;
				totalProgress += edelta() * warmup;

				if (wasVisible && Mathf.chance(updateEffectChance)) updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4f));

				if (progress >= 1f) {
					progress %= 1f;
					consume();
					payload = new BuildPayload(getRecipe().output, team);
					payVector.setZero();
					if (wasVisible) craftEffect.at(x, y);
				}
			}
			if (getRecipe() != null) {
				if (payload != null) if (((BuildPayload) payload).build.block == getRecipe().output) moveOutPayload();
			} else {
				moveOutPayload();
			}
		}

		@Override
		public void write(Writes w) {
			super.write(w);
			w.f(warmup);
			w.f(progress);
			w.f(totalProgress);
			w.i(currentPlan);
		}

		@Override
		public void read(Reads r, byte revision) {
			super.read(r, revision);
			warmup = r.f();
			progress = r.f();
			totalProgress = r.f();
			currentPlan = r.i();
		}
	}
}