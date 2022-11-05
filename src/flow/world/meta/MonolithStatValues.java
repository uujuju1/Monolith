package flow.world.meta;

import arc.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.*;
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
		return t -> {
			t.row();
			for(ItemRecipe recipe : recipes) {
				t.table(((TextureRegionDrawable) Tex.whiteui).tint(Pal.darkestGray), table -> {
					table.table(Tex.underline, plan -> {
						plan.table(input -> {
							for (ItemStack stack : recipe.consumeItems) input.add(new ItemImage(stack)).pad(5f);							
							input.row();
							for (LiquidStack stack : recipe.consumeLiquds) output.add(new Stack(){{
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
							for (LiquidStack stack : recipe.outputLiquds) output.add(new Stack(){{
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
		};
	}
}