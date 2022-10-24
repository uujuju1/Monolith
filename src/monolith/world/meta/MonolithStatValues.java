package flow.world.meta;

import arc.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.*;
import arc.scene.style.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import flow.world.blocks.defense.AOEBlock.BulletRecipe;
import flow.world.blocks.production.MultiCrafter.ItemRecipe;

import static mindustry.world.meta.StatValues.*;

public class MonolithStatValues {
	public static StatValue itemRecipe(Seq<ItemRecipe> recipes) {
		return t -> {
			t.row();
			for(ItemRecipe recipe : recipes) {
				t.table(((TextureRegionDrawable) Tex.whiteui).tint(Pal.darkestGray), table -> {
					table.table(Tex.underline, plan -> {
						plan.table(input -> {
							if (recipe.consumeItems != null) {
								for (ItemStack stack : recipe.consumeItems) {
									input.add(new ItemDisplay(stack.item, stack.amount, true)).pad(5f).left();
								}
							}
							
							/* input.row();

							if (recipe.outputLiquds != null) {
								for (LiquidStack stack : recipe.outputLiquds) {
									input.add(new LiquidDisplay(stack.liquid, stack.amount * 60f, false)).pad(5).left();
								}
							}

							*/
						});
						
						plan.image(Icon.right).color(Color.gray).pad(5f);

						plan.table(output -> {
							if (recipe.outputItems != null) {
								for (ItemStack stack : recipe.outputItems) {
									output.add(new ItemDisplay(stack.item, stack.amount, true)).pad(5f).left();
								}
							}

							/* input.row();

							if (recipe.outputLiquds != null) {
								for (LiquidStack stack : recipe.outputLiquds) {
									input.add(new LiquidDisplay(stack.liquid, stack.amount * 60f, false)).pad(5).left();
								}
							}

							*/
						});
					}).pad(10f).row();

					table.table(stats -> {
						stats.add(Core.bundle.get("stat.productiontime") + ": ");	
						stats.add(StatValues.fixValue(recipe.craftTime/60) + " " + StatUnit.seconds.localized()).color(Color.gray);
					}).padBottom(10f);
				}).growX().pad(10).row();
			}
		};
	}
}