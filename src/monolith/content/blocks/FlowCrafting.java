package flow.content.blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.world.draw.*;
import mindustry.world.consumers.*;
import flow.content.*;
import flow.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class FlowCrafting {
	public static Block
	chromiumSmelter, boiler,
	combustionHeater, heatFan;

	public static void load() {
		chromiumSmelter = new HeatGenericCrafter("chromium-smelter") {{
			requirements(Category.crafting, with(
				Items.titanium, 150,
				Items.silicon, 200,
				Items.graphite, 160,
				Items.metaglass, 140
			));
			size = 3;
			health = 200;
			craftTime = 90f;
			craftEffect = FlowFx.chromiumCraft;
			updateEffect = Fx.smoke;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawArcSmelt(),
				new DrawDefault()
			);
			consumeItems(with(
				Items.lead, 2,
				Items.copper, 1
			));
			consumeLiquid(FlowLiquids.vapour, 0.1f);
			consumeHeat(140f, true);
			consumePower(1f);
			outputHeat = 25f;
			outputItems = with(FlowItems.chromium, 1);
		}};
		boiler = new HeatGenericCrafter("boiler") {{
			requirements(Category.crafting, with(
				Items.metaglass, 120,
				Items.graphite, 150,
				Items.silicon, 100
			));
			size = 3;
			health = 200;
			craftTime = 10;
			updateEffect = Fx.smoke;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidRegion(Liquids.water) {{suffix = "-liquid-water"}},
				new DrawLiquidRegion(Liquids.vapour) {{suffix = "-liquid-vapour"}},
				new DrawDefault()
			);
			consumeLiquid(Liquids.water, 0.1f);
			consummeHeat(90f, true);
			outputHeat = -1f;
		}};

		combustionHeater = new HeatGenericCrafter("combustion-heater") {{
			requirements(Category.crafting, with(
				Items.graphite, 25,
				Items.titanium, 45
			));
			size = 2;
			health = 160;
			craftTime = 180f;
			updateEffect = Fx.smoke;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawArcSmelt(),
				new DrawDefault()
			);
			consume(new ConsumeItemFlammable());
			consumeHeat(400f, true);
			outputHeat = 390;
		}};
		heatFan = new HeatGenericCrafter("heat-fan") {{
			requirements(Category.crafting, with(
				Items.graphite, 150,
				Items.titanium, 100
			));
			size = 3;
			health = 200;
			craftTime = 30f;
			updateEffect = Fx.smoke;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawBlurSpin("-rotator", 15f)
			);
			consumePower(1f);
			consumeHeat(0f, false);
			outputHeat = -10f;
		}};
	}
}