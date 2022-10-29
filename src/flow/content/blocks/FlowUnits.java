package flow.content.blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import flow.content.*;
import flow.world.blocks.units.*;

import static mindustry.type.ItemStack.*;

public class FlowUnits {
	public static Block mothFactory;

	public static void load() {
		mothFactory = new SingleUnitFactory("moth-factory") {{
			requirements(Category.units, with(
				Items.silicon, 150,
				Items.graphite, 145,
				Items.titanium, 120,
				Items.lead, 160
			));
			size = 3;
			health = 200;
			craftTime = 2100f;
			consumeItems(with(
				Items.silicon, 60,
				Items.graphite, 40,
				Items.titanium, 45,
				FlowItems.chromium, 30
			));
			consumeLiquid(FlowLiquids.vapour, 0.2f);
			consumePower(3f);
			unit = FlowUnitTypes.moth;
		}};
	}
}