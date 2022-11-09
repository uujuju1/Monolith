package flow.world.meta;

import arc.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.*;
import arc.scene.ui.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import flow.world.blocks.production.MultiCrafter.ItemRecipe;

import static mindustry.world.meta.StatValues.*;

public class MonolithStatValues {
	public static StatValue itemRecipe(Seq<ItemRecipe> recipes) {
		return stat -> {
			stat.row();
			stat.table(t -> {
				for(ItemRecipe recipe : recipes) {
					t.table(((TextureRegionDrawable) Tex.whiteui).tint(Pal.darkestGray), table -> {
						table.table(Tex.underline, plan -> {
							plan.table(input -> {
								for (ItemStack stack : recipe.consumeItems) input.add(new ItemImage(stack)).pad(5f);							
								input.row();
								for (LiquidStack stack : recipe.consumeLiquids) input.add(new Stack(){{
									add(new Image(stack.liquid.uiIcon));
									Table cont = new Table().left().bottom();
									cont.add(Strings.autoFixed(stack.amount, 2)).style(Styles.outlineLabel);
									add(cont);
      	  			}}).pad(8f);
							});
							plan.image(Icon.right).color(Color.gray).pad(5f);
							plan.table(output -> {
								for (ItemStack stack : recipe.outputItems) output.add(new ItemImage(stack)).pad(5f);
								output.row();
								for (LiquidStack stack : recipe.outputLiquids) output.add(new Stack(){{
									add(new Image(stack.liquid.uiIcon));
									Table cont = new Table().left().bottom();
									cont.add(Strings.autoFixed(stack.amount, 2)).style(Styles.outlineLabel);
									add(cont);
      	  			}}).pad(8f);
							});
						}).growX().pad(10f).row();
						table.table(stats -> {
							stats.add(Core.bundle.get("stat.productiontime") + ": ").color(Color.gray);	
							stats.add(StatValues.fixValue(recipe.craftTime/60) + " " + StatUnit.seconds.localized());
						}).pad(8f);
					}).growX().pad(10f).row();
				}
			});
		};
	}

	public static StatValue payloadRecipe(Seq<PayloadRecipe> recipes) {
		return stat -> {
			stat.row();
			stat.table(t -> {
				for (PayloadRecipe recipe : recipes) t.table(((TextureRegionDrawable) Tex.whiteui).tint(Pal.darkestGray), table -> {
					table.table(Styles.black3, input -> {
						if (recipe.input != null) {
							input.image(recipe.input.uiIcon).size(48).pad(10).row();
							input.image(Icon.down).pad(5).row();
						}
						input.image(recipe.output.uiIcon).size(48).pad(10);
					});

					table.table(stat -> {
						stat.table(Tex.underline, name -> {
							name.add(recipe.output.localizedName).row();
						}).row();

						if (recipe.requirements.length != 0) {
							stat.table(input -> {
								input.add(Core.bundle.get("stat.input") + ":");
								input.table(items -> {
									int i = 0;
									for (ItemStack stack : recipe.requirements) {
										items.add(new ItemImage(stack)).pad(5);
										if (i++ % 4 == 0) items.row();
									}
								}); 
							}).left().row();	
						}
				
						stat.table(craft -> {
							craft.add(Core.bundle.get("stat.productiontime") + ": ");
							craft.add(StatValues.fixValue(time)).color(Color.gray);
						}).left().row();
					}).top().pad(10);
				});
			}).pad(5);
		};
	}
}