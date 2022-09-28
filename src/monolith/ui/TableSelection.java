package monolith.ui;

import arc.func.*;
import arc.struct.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import monolith.world.blocks.production.MultiCrafter.ItemRecipe;

public class TableSelection {
	public static void itemRecipeSelection(Seq<ItemRecipe> recipes, Table table, Cons<ItemRecipe> consumer, Prov<ItemRecipe> provider) {
		for (ItemRecipe recipe : recipes) {
			ImageButton button = (ImageButton) table.button(b -> {
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
			}, Styles.clearTogglei, () -> {}).left().get();
			table.row();

			button.changed(() -> consumer.get(button.isChecked() ? recipe : null));
			button.update(() -> button.setChecked(provider.get() == recipe));
		}
	}
}