package monolith.blocks.payload;

import arc.*;
import arc.struct.*;
import arc.scene.ui.*;
import arc.graphics.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.payloads.*;

public class PayloadCrafter extends PayloadBlock {
	public Seq<Recipe> plans = new Seq<>();
	public Block output = Blocks.router;

	public PayloadCrafter(String name) {
		super(name);
		solid = destructible = rotate = true;
		outputsPayload = configurable = true;

		config(Integer.class, (PayloadCrafterBuild tile, Integer i) -> {
			if(!configurable) return;

			if(tile.currentPlan == i) return;
			tile.currentPlan = i < 0 || i >= plans.size ? -1 : i;
			tile.progress = 0;
		});

		config(Block.class, (PayloadCrafterBuild tile, Block val) -> {
			if(!configurable) return;

			int next = plans.indexOf(r -> r.output == val);
			if(tile.currentPlan == next) return;
			tile.currentPlan = next;
			tile.progress = 0;
		});

		consume(new ConsumeItemDynamic((PayloadCrafterBuild e) -> e.currentPlan != -1 ? plans.get(Math.min(e.currentPlan, plans.size - 1)).requirements : ItemStack.empty));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.output, t -> {
			t.table(table -> {
				for (Recipe r : plans) {
					table.add(r.display()).pad(10f).row();
				}
			});
		});
	}

	public class Recipe {
		public @Nullable Block input = Blocks.router;
		public Block output = Blocks.router;
		public ItemStack[] requirements = ItemStack.empty;
		public float craftTime = 60f;

		public Recipe(Block output, ItemStack[] requirements, float craftTime) {
			this.output = output;
			this.requirements = requirements;
			this.craftTime = craftTime;
		}

		public Table display() {
			Table table = new Table(((TextureRegionDrawable) Tex.whiteui).tint(Pal.darkestGray));
			table.table(name -> {
				name.background(Tex.underline);
				name.add(new Image(output.uiIcon)).size(48f);
				name.add(output.localizedName).padLeft(10f);
			}).row();
			table.table(input -> {
				input.add(Core.bundle.get("stat.input") + ":");
				for (int i = 0; i < requirements.length; i++) {
					input.add(new ItemDisplay(requirements[i].item, requirements[i].amount, false)).padLeft(5);
				}
			}).left().padTop(5f).row();
			table.table(craft -> {
				craft.add(Core.bundle.get("stat.productiontime") + ": ");
				craft.add(StatValues.fixValue(craftTime)).color(Color.gray);
			}).left().row();
			table.margin(10f);
			return table;
		}
	}

	public class PayloadCrafterBuild extends PayloadBlockBuild<BuildPayload> {
		public float progress;
		public int currentPlan = -1;

		public float currentTime() {
			return currentPlan == -1 ? 0f : plans.get(currentPlan).craftTime;
		}

		@Override
		public void updateTile() {
			if (efficiency > 0f && payload == null && currentPlan != -1) {
				progress += edelta();
				if (progress >= currentTime()) {
					consume();
					payload = new BuildPayload(plans.get(currentPlan).output, team);
					payVector.setZero();
					progress %= 1f;
				}
			}
			moveOutPayload();
		}

		@Override
		public void buildConfiguration(Table table) {
			Seq<Block> blocks = Seq.with(plans).map(r -> r.output).filter(b -> b.unlockedNow());

			if (blocks.any()) {
				ItemSelection.buildTable(PayloadCrafter.this, table, blocks, () -> currentPlan == -1 ? null : plans.get(currentPlan).output, block -> configure(plans.indexOf(r -> r.output == block)));
			} else {
				table.table(Styles.black3, t -> t.add("").color(Color.lightGray));
			}
		} 

		@Override
		public void draw() {
			Draw.rect(region, x, y, 0f);
			if (currentPlan != -1) {
				if (plans.get(currentPlan).input != null) {
					for(int i = 0; i < 4; i++){
						if(blends(i) && i != rotation){
							Draw.rect(inRegion, x, y, (i * 90) - 180);
						}
					}
				}
			}
			Draw.rect(outRegion, x, y, rotdeg());
			drawPayload();
			Draw.rect(topRegion, x, y, 0f);
		}

		@Override
		public void write(Writes write) {
			super.write(write);
			write.f(progress);
			write.i(currentPlan);
		}

		@Override
		public void read(Reads read, boolean revision) {
			super(read, revision);
			progress = read.f();
			currentPlan = read.i();
		}
	}
}