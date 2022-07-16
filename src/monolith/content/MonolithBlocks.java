package monolith.content;

import arc.grpahics.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class MonolithBlocks {
	public static Block furnace;

	public void load() {
		furnace = new GenericCrafter("furnace") {{
			requirements(Category.crafting, with(
				Items.graphite, 120,
				Items.silicon, 180
			));
			size = 3;
			health = 200;
			craftTime = 120;
			warmupSpeed = 0.007f;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawArcSmelt() {{
					flameColor = Color.white;
					midColor = Color.valueOf("DEDEDE");
				}},
				new DrawDefault(),
				new DrawWarmupRegion(),
				new DrawGlowRegion("-light")
			);
			consumeItems(with(
				Items.sand, 1,
				Items.coal, 1,
				Items.metaglass, 1
			));
			consumePower(0.5f);
			outputItems = with(
				MonolithItems.macrosteel, 1,
				Items.silicon, 1
			);
		}};
	}
}