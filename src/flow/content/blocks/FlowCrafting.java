package flow.content.blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.world.draw.*;
import mindustry.world.consumers.*;
import mindustry.world.blocks.production.*;
import flow.content.*;
import flow.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class FlowCrafting {
	public static Block
	chromiumSmelter, boiler, compressor, advancedCrafter,
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
			hasLiquids = true;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidRegion(Liquids.water) {{suffix = "-liquid-water";}},
				new DrawLiquidRegion(FlowLiquids.vapour) {{suffix = "-liquid-vapour";}},
				new DrawDefault()
			);
			consumeLiquid(Liquids.water, 0.1f);
			consumeHeat(90f, false);
			outputLiquids = LiquidStack.with(FlowLiquids.vapour, 0.1f);
			outputHeat = -1f;
		}};
		compressor = new GenericCrafter("compressor") {{
			requirements(Category.crafting, with(
				Items.copper, 50,
				Items.lead, 65
			));
			size = 2;
			health = 160;
			craftTime = 30f;
			updateEffect = Fx.smoke;
			consumeItems(with(Items.copper, 3, Items.lead, 2));
			outputItems = with(Items.scrap, 1);
		}};
		advancedCrafter = new MultiCrafter("advanced-crafter") {{
			requirements(Category.crafting, with(
				Items.silicon, 120,
				Items.titanium, 150,
				Items.metaglass 125,
				Items.lead, 135,
				FlowItems.chromium, 140
			));
			size = 3;
			health = 200;
			recipes.add(
				new ItemRecipe() {{
					consumeItems = with(
						Items.pyratite, 1
						Items.sand, 5,
						Items.coal, 3
					);
					outputItems = with(Items.silicon, 5);
					updateEffect = Fx.smoke;
					craftTime = 120f
				}},
				new ItemRecipe() {{
					consumeItems = with(Items.coal, 10);
					consumeLiquids = LiquidStack.with(Liquids.water, 0.1f);
					outputItems = with(Items.silicon, 5);
					updateEffect = Fx.smoke;
					craftTime = 180f;
				}}
			);
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