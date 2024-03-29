package flow.content.blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.consumers.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.production.*;
import flow.content.*;
import flow.world.blocks.payload .*;
import flow.world.blocks.production.*;

import static mindustry.type.ItemStack.*;

public class FlowCrafting {
	public static Block
	wallCrafter, payloadDeconstructor,
	chromiumSmelter, boiler,
	mechanicalWell,
	compressor, pyratitePress, advancedCrafter;

	public static void load() {
		wallCrafter = new PayloadCrafter("wall-crafter") {{
			requirements(Category.units, with(
				Items.titanium, 130,
				Items.silicon, 150,
				Items.plastanium, 80,
				Items.graphite, 145,
				FlowItems.chromium, 120
			));
			size = 3;
			health = 200;
			recipes.addAll(
				new PayloadRecipe(Blocks.copperWallLarge, with(Items.copper, 18), 180f),
				new PayloadRecipe(Blocks.titaniumWallLarge, with(Items.titanium, 18), 200f),
				new PayloadRecipe(Blocks.plastaniumWallLarge, with(Items.plastanium, 12, Items.metaglass, 6), 220f),
				new PayloadRecipe(Blocks.thoriumWallLarge, with(Items.thorium, 18), 240f),
				new PayloadRecipe(Blocks.surgeWallLarge, with(Items.surgeAlloy, 18), 260f)
			);
			updateEffect = Fx.smoke;
			consumePower(3f);
		}};
		payloadDeconstructor = new PayloadDeconstructor("payload-deconstructor") {{
			requirements(Category.units, with(
				Items.graphite, 120,
				Items.silicon, 150,
				Items.titanium, 140
			));
			size = 3;
			health = 200;
			itemCapacity = 100;
			consumePower(2f);
		}};

		chromiumSmelter = new GenericCrafter("chromium-smelter") {{
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
			consumePower(1f);
			outputItems = with(FlowItems.chromium, 1);
		}};
		boiler = new GenericCrafter("boiler") {{
			requirements(Category.crafting, with(
				Items.metaglass, 120,
				Items.graphite, 150,
				Items.silicon, 100
			));
			size = 3;
			health = 200;
			craftTime = 60;
			updateEffect = Fx.smoke;
			hasLiquids = true;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidRegion(Liquids.water) {{suffix = "-liquid-water";}},
				new DrawLiquidRegion(FlowLiquids.vapour) {{suffix = "-liquid-vapour";}},
				new DrawDefault()
			);
			consumeItems(with(Items.coal, 2));
			consumeLiquid(Liquids.water, 0.1f);
			outputLiquids = LiquidStack.with(FlowLiquids.vapour, 0.1f);
		}};

		mechanicalWell = new AttributeCrafter("mechanical-well") {{
			requirements(Category.production, with(
				Items.copper, 45,
				Items.lead, 25
			));
			size = 2;
			health = 160;
			hasLiquids = true;
			attribute = Attribute.water;
			boostScale = 0.25f;
			minEfficiency = -1f;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidRegion(Liquids.water),
				new DrawDefault()
			);
			outputLiquids = LiquidStack.with(Liquids.water, 0.09f);
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
		pyratitePress = new GenericCrafter("pyratite-press") {{
			requirements(Category.crafting, with(
				Items.copper, 50,
				Items.graphite, 25
			));
			size = 2;
			health = 160;
			craftTime = 120f;
			consumeItems(with(
				Items.lead, 1,
				Items.graphite, 1
			));
			outputItems = with(Items.pyratite, 2);
		}};
		advancedCrafter = new MultiCrafter("advanced-crafter") {{
			requirements(Category.crafting, with(
				Items.graphite, 95,
				Items.copper, 120,
				Items.lead, 100
			));
			size = 3;
			health = 200;
			recipes.add(
				new ItemRecipe() {{
					consumeItems = with(
						Items.pyratite, 1,
						Items.sand, 5,
						Items.coal, 3
					);
					consumePower = 1f;
					drawer = new DrawMulti(
						new DrawDefault(),
						new DrawFlame() {{
							flameRadius = 4f;
						}}
					);
					outputItems = with(Items.silicon, 5);
					updateEffect = Fx.smoke;
					craftTime = 120f;
				}},
				new ItemRecipe() {{
					consumeItems = with(Items.coal, 5);
					consumeLiquids = LiquidStack.with(Liquids.water, 0.1f);
					consumePower = 0.5f;
					drawer = new DrawMulti(
						new DrawDefault(),
						new DrawRegion("-cap")
					);
					outputItems = with(Items.graphite, 4);
					updateEffect = Fx.smoke;
					craftTime = 180f;
				}}
			);
		}};
	}
}