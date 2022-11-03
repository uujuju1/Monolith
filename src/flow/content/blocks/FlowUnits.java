package flow.content.blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.world.blocks.units.*;
import flow.content.*;
import flow.world.blocks.units.*;

import static mindustry.type.ItemStack.*;

public class FlowUnits {
	public static Block mothFactory, insectAssembler;

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
			itemCapacity = 120;
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

		insectAssembler = new UnitAssembler("insect-assembler") {{
			requirements(Category.units, with(
				Items.thorium, 550,
				Items.silicon, 620,
				Items.graphite, 600,
				Items.plastanium, 470,
				Items.titanium, 520
			));
			size = 5;
			health = 560;
			plans.addAll(
				new AssemblerUnitPlan(FlowUnitTypes.butterfly, 3600f, PayloadStack.list(Blocks.thoriumWallLarge, 5, Blocks.plastaniumWallLarge, 7))
			);
			consumeItems(with(FlowItems.chromium, 150, Items.silicon, 250, Items.graphite, 220));
			consumeLiquid(FlowLiquids.vapour, 0.7f);
			consumePower(4f);
		}};
	}
}