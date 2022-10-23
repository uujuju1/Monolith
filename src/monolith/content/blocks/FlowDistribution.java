package flow.content.blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import flow.content.*;
import flow.world.blocks.distribution.*;

import static mindustry.type.ItemStack.*;

public class FlowDistribution {
	public static Block
	itemLiquidJunction,
	heatPipe, advHeatPipe;

	public static void load() {
		itemLiquidJunction = new ItemLiquidJunction("item-liquid-junction") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.copper, 3,
				FlowItems.chromium, 1
			));
			size = 1;
			health = 60;
		}};
		heatPipe = new HeatPipe("heat-pipe") {{
			requirements(Category.distribution, with(Items.silicon, 1));
			size = 1;
			health = 160;
		}};
		advHeatPipe = new HeatPipe("adv-heat-pipe") {{
			requirements(Category.distribution, with(FlowItems.chromium, 1, Items.silicon, 2));
			size = 1;
			health = 160;
			heatFlowMultiplier = 0.2f;
		}};
	}
}