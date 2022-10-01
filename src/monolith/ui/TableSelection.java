package monolith.ui;

import arc.func.*;
import arc.struct.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import monolith.world.blocks.defense.AOEBlock.BulletRecipe;
import monolith.world.blocks.production.MultiCrafter.ItemRecipe;

public class TableSelection {
	public static void itemRecipeSelection(Seq<ItemRecipe> recipes, Table table, Cons<ItemRecipe> consumer, Prov<ItemRecipe> provider) {
		Table cont = new Table();
		for (ItemRecipe recipe : recipes) {
			Button button = cont.button(b -> {
				if (recipe.consumeItems != null) {
					for (ItemStack stack : recipe.consumeItems) {
						b.add(new ItemDisplay(stack.item, stack.amount, false)).pad(5);
					}
				}
				b.image(Icon.right).pad(5);
				if (recipe.outputItems != null) {
					for (ItemStack stack : recipe.outputItems) {
						b.add(new ItemDisplay(stack.item, stack.amount, false)).pad(5);
					}
				}
			}, Styles.clearTogglei, () -> {}).left().growX().get();
			cont.row();

			button.changed(() -> consumer.get(button.isChecked() ? recipe : null));
			button.update(() -> button.setChecked(provider.get() == recipe));
		}

		table.add(cont);
	}
	public static void bulletRecipeSelection(Seq<BulletRecipe> recipes, Table table, Cons<BulletRecipe> consumer, Prov<BulletRecipe> provider) {
		int i = 0;
		Table cont = new Table();
		cont.defaults().size(40);
		for (BulletRecipe recipe : recipes) {
			Button button = cont.button(b -> {
				b.image(recipe.uiIcon).size(32f);
			}, Styles.clearTogglei, () -> {}).get();
			
			if (i++ % 4 == 3) {
				cont.row();
			}
			button.changed(() -> consumer.get(button.isChecked() ? recipe : null));
			button.update(() -> button.setChecked(provider.get() == recipe));
		}

		table.add(cont);
	}
}