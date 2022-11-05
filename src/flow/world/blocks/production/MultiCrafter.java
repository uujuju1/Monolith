package flow.world.blocks.production;

import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.util.io.*;
import arc.scene.ui.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.entities.units.*;
import mindustry.world.consumers.*;
import flow.ui.*;
import flow.content.*;
import flow.world.meta.*;
import flow.world.consumers.*;

public class MultiCrafter extends Block {
	public Seq<ItemRecipe> recipes = new Seq<>();

	public MultiCrafter(String name) {
		super(name);
		configurable = true;
		hasItems = true;
		solid = update = sync = destructible = true;

		consume(new ConsumeItemDynamic((MultiCrafterBuild e) -> e.currentPlan != -1 ? e.getRecipe().consumeItems : ItemStack.empty));
		consume(new ConsumeLiquidDynamic((MultiCrafterBuild e) -> e.currentPlan != -1 ? e.getRecipe().consumeLiquids : LiquidStack.empty));
		consume(new ConsumePowerDynamic((MultiCrafterBuild e) -> e.currentPlan != -1 ? e.getRecipe().consumePower : 0f));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.output, MonolithStatValues.itemRecipe(recipes));
	}

	@Override public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {recipes.get(0).drawer.drawPlan(this, plan, list);}
	@Override public TextureRegion[] icons() {return recipes.get(0).drawer.finalIcons(this);}
	@Override public void getRegionsToOutline(Seq<TextureRegion> out) {recipes.get(0).drawer.getRegionsToOutline(this, out);}

	@Override
	public void load() {
		super.load();
		for (ItemRecipe recipe : recipes) recipe.drawer.load(this);
	}

	public class ItemRecipe {
		public @Nullable ItemStack[]
		consumeItems = ItemStack.empty,
		outputItems = ItemStack.empty;

		public @Nullable LiquidStack[]
		consumeLiquids = LiquidStack.empty,
		outputLiquids = LiquidStack.empty;

		public Effect 
		craftEffect = Fx.none,
		updateEffect = Fx.none;

		public DrawBlock drawer = new DrawDefault();

		public float 
		consumePower = 1f,
		updateEffectChance = 0.03f,
		warmupSpeed = 0.019f,
		craftTime = 60f;
	}


	public class MultiCrafterBuild extends Building {
		public int currentPlan = -1;
		public float
		progress,
		totalProgress,
		warmup;

		public @Nullable ItemRecipe getRecipe() {return currentPlan == -1 ? null : recipes.get(currentPlan);}
		public float getPowerCons() {return getRecipe() != null ? getRecipe().consumePower : 0f;}

		public void changeRecipe(ItemRecipe recipe) {
			currentPlan = recipes.indexOf(recipe);
			FlowFx.changeRecipe.at(x, y, rotdeg(), block);
		}

		public void dumpOutputs() {
			if(getRecipe() != null && timer(timerDump, dumpTime / timeScale)){
				if (getRecipe().outputItems == null) return; 
				for(ItemStack output : getRecipe().outputItems){
					dump(output.item);
				}
			}
		}

		@Override public float warmup() {return warmup;}
		@Override public float progress() {return progress;}
		@Override public float totalProgress() {return totalProgress;}

		@Override
		public void buildConfiguration(Table table) {
			TableSelection.itemRecipeSelection(recipes, table, recipe -> changeRecipe(recipe), () -> getRecipe());
		}

		@Override
		public boolean acceptItem(Building source, Item item) {
			if (getRecipe() == null) return false;
			return items.get(item) < getMaximumAccepted(item) &&	Structs.contains(getRecipe().consumeItems, stack -> stack.item == item);
		}

		@Override
		public boolean shouldConsume() {
			if (getRecipe() == null) return false;
			return enabled;
		}


		@Override
		public void updateTile() {
			if (efficiency > 0 && getRecipe() != null) {
				warmup = Mathf.approachDelta(warmup, 1f, getRecipe().warmupSpeed);
				progress += getProgressIncrease(getRecipe().craftTime) * warmup;
				totalProgress += edelta() * warmup;

				if (wasVisible && Mathf.chance(getRecipe().updateEffectChance)) getRecipe().updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4f));
				if(getRecipe() != null) for(LiquidStack output : getRecipe().outputLiquids) handleLiquid(this, output.liquid, Math.min(output.amount * getProgressIncrease(1f), liquidCapacity - liquids.get(output.liquid)));
				if (progress >= 1f) {
					progress %= 1f;
					consume();
					if (wasVisible) getRecipe().craftEffect.at(x, y);
					if (getRecipe() != null) for (ItemStack out : getRecipe().outputItems) for (int i = 0; i < out.amount; i++) offload(out.item);
				}
			} else {
				warmup = Mathf.approachDelta(warmup, 0f, 0.019f);
			}
			dumpOutputs();
		}

		@Override
		public void draw() {
			if (getRecipe() != null) {
				getRecipe().drawer.draw(this);				
			} else {
				recipes.get(0).drawer.draw(this);
			}
		}
		@Override
		public void drawLight() {
			if (getRecipe() != null) {
				getRecipe().drawer.drawLight(this);
			} else {
				recipes.get(0).drawer.drawLight(this);
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