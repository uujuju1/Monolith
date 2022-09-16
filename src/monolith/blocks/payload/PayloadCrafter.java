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
import mindustry.world.blocks.payloads.*;

public class PayloadCrafter extends PayloadBlock {
	public Seq<Recipe> plans = new Seq<>();
	public Block output = Blocks.router;

	public PayloadCrafter(String name) {
		super(name);
		solid = destructible = rotate = true;
		outputsPayload = true;
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.output, t -> {
			t.table(table -> {
				for (Recipe r : plans) {
					table.add(r.display());
				}
			});
		});
	}

	public class Recipe {
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
			});
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

		@Override
		public void updateTile() {
			if (efficiency > 0f && payload == null) {
				progress += edelta();
				if (progress >= output.buildCost) {
					consume();
					payload = new BuildPayload(output, team);
					payVector.setZero();
					progress %= 1f;
				}
			}
			moveOutPayload();
		}

		@Override
		public void draw() {
			super.draw();
			drawPayload();
		}
	}
}