package monolith.world.blocks.meta;

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
import monolith.world.blocks.voidf.*;
import monolith.world.blocks.voidf.production.*;
import monolith.world.blocks.production.MultiCrafter.ItemRecipe;

import static mindustry.world.meta.StatValues.*;

public class MonolithStatValues {

	public static StatValue voidfUnit(VoidfBlock block) {
		return ta -> {
			ta.table(table -> {
				table.background(((TextureRegionDrawable) Tex.whiteui).tint(0.27f, 0.27f, 0.27f, 1f));
				table.table(t -> {
					t.background(((TextureRegionDrawable) Tex.whiteui).tint(0f, 0f, 0f, 1f));
					t.add(Core.bundle.get("stat.voidfModule")).color(Pal.accent).center().pad(10f).row();
					t.table(stats -> {
						stats.background(Tex.underline);
						stats.add(Core.bundle.get("stat.minVoidf") + ": ").left();
						stats.add(fixValue(block.minVoidf)).color(Color.gray).left().row();
				
						stats.add(Core.bundle.get("stat.maxVoidf") + ": ").left();
						stats.add(fixValue(block.maxVoidf)).color(Color.gray).left().row();
						if (block.outputVoidf) {
							stats.add(Core.bundle.get("stat.transferRate") + ": ").left();
							stats.add(fixValue(block.transferRate)).color(Color.gray).left().row();
						}
						
					}).pad(10f).row();
					
					if (block.consumeVoidf > 0 || block instanceof VoidfCrafter) {
						t.add(Core.bundle.get("category.crafting")).color(Pal.accent).center().pad(10f).row();
						t.table(input -> {
							input.add(Core.bundle.get("stat.consumeVoidf") + ": ").left();
							input.add(fixValue(block.consumeVoidf)).color(Color.gray).left().row();
							if (block instanceof VoidfCrafter) {
								input.add(Core.bundle.get("stat.voidfOutput") + ": ").left();
								input.add(fixValue(((VoidfCrafter) block).voidfOutput)).color(Color.gray).left().row();					
							}
						}).pad(10f);
					}
				}).pad(4f);
			});
		};
	}

	public static StatValue itemRecipe(Seq<ItemRecipe> recipes) {
		return t -> {
			for(ItemRecipe recipe : recipes) {
				t.table(((TextureRegionDrawable) Tex.whiteui).tint(Pal.darkestGray), table -> {
					table.table(Tex.underline, recipe -> {
						recipe.table(input -> {
							if (recipe.outputItems != null) {
								for (ItemStack stack : recipe.outputItems) {
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
						
						recipe.image(Icon.right).color(Color.gray).pad(5f);

						recipe.table(output -> {
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
						stats.add(fixValue(recipe.craftTime)).color(Color.gray);
					}).padBottom(10f);
				}).growX();
			}
		};
	}
}