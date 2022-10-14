package monolith.content;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.consumers.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.defense.turrets.*;

import monolith.world.*;
import monolith.world.blocks.units.*;
import monolith.world.blocks.defense.*;
import monolith.world.blocks.payload.*;
import monolith.world.blocks.sandbox.*;
import monolith.world.blocks.production.*;
import monolith.world.blocks.distribution.*;

import static mindustry.type.ItemStack.*;

public class MonolithBlocks {
	public static Block 
	// distribution
	itemLiquidJunction,
	heatPipe,

	// production
	macrosteelFurnace, lithiumWeaver, alloyInfuser,
	industrialPress, sohritePress, karanitePress,
	vakyiteCompressor,
	combustionHeater, heatFan,

	payloadCrucible,

	// turrets
	move, accelerate,
	revenant,
	caesar, vigenere,

	// walls
	sparkWall,
	meaniumWall, meaniumWallLarge,

	// misc
	artifact,

	// sandbox
	heatSource,

	// units
	remnantFactory;

	public static void load() {
		// distribution
		itemLiquidJunction = new ItemLiquidJunction("item-liquid-junction") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.copper, 3,
				MonolithItems.macrosteel, 1
			));
			size = 1;
			health = 60;
		}};
		heatPipe = new HeatPipe("heat-pipe") {{
			requirements(Category.distribution, with(MonolithItems.meanium, 2, Items.silicon, 1));
			size = 1;
			health = 160;
		}};

		// production
		macrosteelFurnace = new GenericCrafter("macrosteel-furnace") {{
			requirements(Category.crafting, with(
				Items.graphite, 120,
				Items.silicon, 180
			));
			size = 3;
			health = 200;
			craftTime = 120;
			craftEffect = MonolithFx.macrosteelCraft;
			updateEffect = Fx.smoke;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawArcSmelt() {{
					flameColor = Color.white;
					midColor = Color.valueOf("DEDEDE");
				}},
				new DrawDefault(),
				new DrawWarmupRegion() {{
					color = Color.white;
					sinScl = 3f;
					sinMag = 0.3f;
				}}
			);
			consumeItems(with(
				Items.sand, 2,
				Items.coal, 2,
				Items.metaglass, 1
			));
			consumePower(0.5f);
			outputItems = with(
				MonolithItems.macrosteel, 1,
				Items.silicon, 1
			);
		}};
		lithiumWeaver = new GenericCrafter("lithium-weaver") {{
			requirements(Category.crafting, with(
				Items.graphite, 120,
				Items.silicon, 100,
				MonolithItems.macrosteel, 70
			));
			size = 3;
			health = 200;
			craftTime = 60f;
			craftEffect = MonolithFx.lithiumCraft;
			updateEffect = Fx.smoke;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawWeave(),
				new DrawDefault()
			);
			consumeItems(with(
				MonolithItems.macrosteel, 1,
				Items.titanium, 2
			));
			consumePower(1f);
			outputItems = with(MonolithItems.lithium, 2);
		}};
		alloyInfuser = new GenericCrafter("alloy-infuser") {{
			requirements(Category.crafting, with(
				Items.titanium, 30,
				Items.silicon, 45,
				Items.graphite, 40,
				MonolithItems.macrosteel, 50
			));
			size = 2;
			health = 160;
			craftTime = 60f;
			updateEffect = Fx.smoke;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawFlame(Color.valueOf("F7E97E"))
			);
			consumeItems(with(
				Items.titanium, 1,
				Items.graphite, 2
			));
			consumePower(1f);
			outputItems = with(MonolithItems.meanium, 1);
		}};
		industrialPress = new MultiCrafter("industrial-press") {{
			requirements(Category.crafting, with(
				MonolithItems.lithium, 250,
				MonolithItems.macrosteel, 200,
				Items.silicon, 300,
				Items.titanium, 175,
				Items.graphite, 275
			));
			size = 5;
			health = 400;
			recipes.addAll(
				new ItemRecipe() {{
					consumeItems = with(MonolithItems.meanium, 2, Items.lead, 2);
					outputItems = with(MonolithItems.lathanium, 1);
					craftTime = 45f;
					consumePower = 2.5f;
					craftEffect = MonolithFx.lathaniumCraft;
					updateEffect = Fx.smoke;
				}},
				new ItemRecipe() {{
					consumeItems = with(MonolithItems.vakyite, 3, Items.silicon, 2, Items.thorium, 1);
					outputItems = with(MonolithItems.venera, 2);
					craftTime = 30f;
					consumePower = 2f;
					craftEffect = MonolithFx.veneraCraft;
					updateEffect = Fx.smoke;
				}}
			);
		}};
		sohritePress = new GenericCrafter("sohrite-press") {{
			requirements(Category.crafting, with(
				MonolithItems.meanium, 150,
				MonolithItems.macrosteel, 175,
				MonolithItems.lithium, 125,
				Items.graphite, 200,
				Items.titanium, 225,
				Items.silicon, 190
			));
			size = 3;
			health = 200;
			craftTime = 75f;
			craftEffect = MonolithFx.sohriteCraft;
			updateEffect = Fx.smoke;
			consumeItems(with(
				MonolithItems.macrosteel, 3,
				MonolithItems.lithium, 2
			));
			consumePower(1.5f);
			outputItems = with(MonolithItems.sohrite, 1);
		}};
		karanitePress = new GenericCrafter("karanite-press") {{
			requirements(Category.crafting, with(
				MonolithItems.meanium, 180,
				MonolithItems.lathanium, 140,
				Items.plastanium, 100,
				Items.titanium, 150
			));
			size = 4;
			craftTime = 90f;
			craftEffect = MonolithFx.karaniteCraft;
			updateEffect = Fx.smoke;
			consumeItems(with(
				Items.titanium, 2,
				Items.silicon, 3,
				MonolithItems.macrosteel, 1
			));
			consumeLiquids(LiquidStack.with(Liquids.oil, 0.2f, Liquids.water, 0.1f));
			consumePower(2f);
			outputItems = with(MonolithItems.karanite, 2);
		}};
		vakyiteCompressor = new HeatGenericCrafter("vakyite-compressor") {{
			requirements(Category.crafting, with(
				MonolithItems.lathanium, 150,
				MonolithItems.sohrite, 125,
				Items.silicon, 175,
				Items.graphite, 200
			));
			size = 3;
			craftTime = 90f;
			// craftEffect = MonolithFx.vakyiteCraft;
			updateEffect = Fx.smoke;
			consumeItems(with(
				MonolithItems.sohrite, 2,
				MonolithItems.meanium, 1
			));
			consumeLiquid(Liquids.water, 0.1f);
			consumeHeat(100f, true);
			consumePower(1f);
			outputHeat = 25f;
			outputItems = with(MonolithItems.vakyite, 1);
		}};

		combustionHeater = new HeatGenericCrafter("combustion-heater") {{
			requirements(Category.crafting, with(
				MonolithItems.meanium, 80,
				Items.graphite, 25,
				Items.titanium, 45
			));
			size = 2;
			health = 160;
			craftTime = 300f;
			// craftEffect = MonolithFx.combust;
			updateEffect = Fx.smoke;
			consume(new ConsumeItemFlammable());
			outputHeat = 390;
		}};
		heatFan = new HeatGenericCrafter("heat-fan") {{
			requirements(Category.crafting, with(
				MonolithItems.meanium, 120,
				Items.titanium, 100
			));
			size = 3;
			health = 200;
			craftTime = 180f;
			updateEffect = Fx.smoke;
			consumePower(1f);
			outputHeat = 0f;
		}};

		payloadCrucible = new PayloadCrafter("payload-crucible") {{
			requirements(Category.crafting, with(
				Items.silicon, 250,
				Items.graphite, 180,
				Items.titanium, 100,
				MonolithItems.meanium, 200,
				MonolithItems.lithium, 150,
				MonolithItems.macrosteel, 230
			));
			size = 3;
			health = 260;
			craftEffect = MonolithFx.crucibleCraft;
			updateEffect = Fx.smoke;
			plans.addAll(
				new Recipe(meaniumWallLarge, with(Items.titanium, 16, Items.coal, 40), 600f),
				new Recipe(Blocks.container, with(Items.titanium, 50, Items.silicon, 25), 180f)
			);
			itemCapacity = 100;
		}};

		// turrets
		move = new ItemTurret("move") {{
			requirements(Category.turret, with(
				Items.graphite, 150,
				Items.silicon, 200,
				Items.lead, 250,
				MonolithItems.macrosteel, 100
			));
			size = 3;
			health = 1400;
			reload = 60;
			range = 17f * 8f;
			recoil = 2f;
			shootY = -2f;
			rotateSpeed = 1.5f;
			shake = 1f;
			drawer = new DrawTurret("reinforced-");
			Effect shootEff = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);
			ammo(
				Items.graphite, new BasicBulletType(2f, 50) {{
					width = height = 12f;
					lifetime = 76f;
					frontColor = Color.white;
					hitColor = backColor = trailColor = Color.valueOf("95ABD9");
					trailWidth = 2.5f;
					trailLength = 16;
					shootEffect = shootEff;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					knockback = 4f;
					rangeChange = 16f;
				}},
				Items.silicon, new BasicBulletType(2.5f, 30) {{
					width = height = 10f;
					lifetime = 54.4f;
					frontColor = Color.white;
					hitColor = backColor = trailColor = Color.valueOf("B0BAC0");
					trailWidth = 2.5f;
					trailLength = 16;
					shootEffect = shootEff;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					knockback = 4f;
					homingRange = 40f;
					homingPower = 0.07f;
				}},
				MonolithItems.macrosteel, new BasicBulletType(3f, 40) {{
					width = height = 12f;
					lifetime = 45.33f;
					frontColor = Color.white;
					hitColor = backColor = trailColor = Color.valueOf("B2B8FF");
					trailWidth = 2.5f;
					trailLength = 16;
					shootEffect = shootEff;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					knockback = 4f;
				}}
			);
		}};
		accelerate = new ItemTurret("accelerate") {{
			requirements(Category.turret, with(
				Items.plastanium, 200,
				Items.thorium, 150,
				Items.titanium, 250,
				Items.silicon, 300,
				MonolithItems.macrosteel, 300
			));
			size = 4;
			health = 2880;
			reload = 30;
			range = 22.75f * 8f + 16f;
			recoil = 3f;
			rotateSpeed = 1.75f;
			shake = 1.5f;
			shootSound = Sounds.shootBig;
			shoot = new ShootBarrel() {{
				barrels = new float[] {
					0f, -2f, 0f,
					5f, -4f, 0f,
					-5f, -4f, 0,
				};
			}};
			drawer = new DrawTurret("reinforced-");
			ammo(
				Items.thorium, new BasicBulletType(2f, 150) {{
					lifetime = 91f;
					width = height = 16f;
					frontColor = Color.white;
					hitColor = backColor = trailColor = Color.valueOf("BF92F9");
					trailWidth = 3f;
					trailLength = 16;
					shootEffect = MonolithFx.shootDiamondColor;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					knockback = 6f;
				}},
				Items.titanium, new BasicBulletType(3.5f, 130) {{
					lifetime = 61.14f;
					width = height = 16f;
					frontColor = Color.white;
					hitColor = backColor = trailColor = Color.valueOf("88A4FF");
					trailWidth = 3f;
					trailLength = 16;
					shootEffect = MonolithFx.shootDiamondColor;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					knockback = 5f;
					rangeChange = 32f;
				}},
				Items.silicon, new BasicBulletType(3.5f, 110) {{
					lifetime = 52f;
					width = height = 16f;
					frontColor = Color.white;
					hitColor = backColor = trailColor = Color.valueOf("B0BAC0");
					trailWidth = 3f;
					trailLength = 16;
					shootEffect = MonolithFx.shootDiamondColor;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					knockback = 4f;
					homingRange = 40f;
					homingPower = 0.12f;
				}},
				MonolithItems.macrosteel, new BasicBulletType(4f, 90) {{
					lifetime = 45.5f;
					width = height = 16f;
					frontColor = Color.white;
					hitColor = backColor = trailColor = Color.valueOf("B2B8FF");
					trailWidth = 3f;
					trailLength = 16;
					shootEffect = MonolithFx.shootDiamondColor;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					knockback = 7f;
				}}
			);
		}};
		revenant = new ItemTurret("revenant") {{
			requirements(Category.turret, with(
				MonolithItems.lithium, 75,
				MonolithItems.macrosteel, 125,
				Items.silicon, 50
			));
			size = 2;
			health = 650;
			reload = 60;
			range = 15f * 8f;
			recoil = 1.5f;
			rotateSpeed = 1f;
			shootSound = Sounds.shotgun;
			ammo(
				Items.silicon, new ShrapnelBulletType() {{
					length = range;
					damage = 60;
					ammoMultiplier = 2;
					width = 20;
					toColor = Color.white;
				}},
				Items.titanium, new ShrapnelBulletType() {{
					length = range + 16f;
					damage = 100;
					ammoMultiplier = 4;
					width = 20;
					rangeChange = 16f;
				}}
			);
		}};

		caesar = new AOEBlock("caesar") {{
			requirements(Category.units, with(
				Items.silicon, 80,
				Items.plastanium, 50,
				MonolithItems.macrosteel, 120
			));
			size = 2;
			health = 250;
			itemCapacity = 20;
			consumePower(2f);
			plans.add(
				new BulletRecipe("monolith-standard") {{
					requirements = with(Items.copper, 12, Items.silicon, 6);
					damage = 45f;
					range = 120f;
					reload = 180f;
					shootEffect = MonolithFx.aoeShoot;
				}},
				new BulletRecipe("monolith-heavy-graphite") {{
					requirements = with(Items.graphite, 16, Items.silicon, 8);
					damage = 90f;
					range = 180f;
					reload = 240f;
					shootEffect = MonolithFx.aoeShoot;
				}},
				new BulletRecipe("monolith-pyra") {{
					requirements = with(Items.pyratite, 14, Items.silicon, 7);
					damage = 30f;
					range = 120f;
					reload = 60f;
					shootEffect = MonolithFx.aoeShoot;
					status(StatusEffects.burning, 30f);
				}}
			);
		}};
		vigenere = new AOEBlock("vigenere") {{
			requirements(Category.units, with(
				Items.thorium, 350,
				Items.silicon, 460,
				Items.titanium, 330,
				Items.phaseFabric, 200,
				MonolithItems.macrosteel, 660
			));
			size = 3;
			health = 830;
			itemCapacity = 30;
			consumePower(5f);
			plans.add(
				new BulletRecipe("monolith-heavy-graphite") {{
					requirements = with(Items.graphite, 26, Items.silicon, 13);
					damage = 140f;
					range = 200f;
					reload = 240f;
					shootEffect = MonolithFx.aoeShoot;
				}},
				new BulletRecipe("monolith-heavy-thorium") {{
					requirements = with(Items.thorium, 30, Items.silicon, 15);
					damage = 200f;
					range = 240f;
					reload = 320f;
					shootEffect = MonolithFx.aoeShoot;
				}},
				new BulletRecipe("monolith-explosive") {{
					requirements = with(Items.blastCompound, 28, Items.silicon, 14);
					damage = 150f;
					range = 220f;
					reload = 400f;
					shootEffect = MonolithFx.aoeShoot;
					status(StatusEffects.blasted, 120f);
				}},
				new BulletRecipe("monolith-cryo") {{
					requirements = with(Items.titanium, 28, Items.silicon, 14);
					damage = 150f;
					range = 220f;
					reload = 400f;
					shootEffect = MonolithFx.aoeShoot;
					status(StatusEffects.freezing, 120f);
				}}
			);
		}};

		// walls
		sparkWall = new SparkWall("spark-wall") {{
			requirements(Category.defense, with(
				MonolithItems.lithium, 12,
				Items.metaglass, 12
			));
			size = 2;
			health = 440 * 4;
			envDisabled |= Env.scorching;
			shoot = new BasicBulletType(2f, 25) {{
				lifetime = 40f * 2f;
				width = height = 10f;
				frontColor = Color.white;
				backColor = hitColor = Pal.accent;
				shootEffect = Fx.colorSpark;
			}};
		}};
		meaniumWall = new Wall("meanium-wall") {{
			requirements(Category.defense, with(
				MonolithItems.meanium, 6
			));
			size = 1;
			health = 500;
		}};
		meaniumWallLarge = new Wall("meanium-wall-large") {{
			requirements(Category.defense, with(
				MonolithItems.meanium, 24
			));
			size = 2;
			health = 2000;
		}};


		// build towers
		artifact = new BuildTurret("artifact") {{
			requirements(Category.effect, with(
				Items.plastanium, 150,
				Items.silicon, 200,
				Items.metaglass, 170,
				MonolithItems.macrosteel, 180
			));
			size = 2;
			health = 250;
			range = 200;
			buildSpeed = 1.5f;
			consumePower(3f);
		}};

		// sandbox
		heatSource = new HeatSource("heat-source") {{
			buildVisibility = BuildVisibility.sandboxOnly;
			size = 1;
			maxHeat = 900f;
		}};

		// units
		remnantFactory = new SingleUnitFactory("remnant-factory") {{
			requirements(Category.units, with(
				Items.graphite, 150,
				Items.thorium, 200,
				Items.titanium, 350,
				Items.silicon, 250,
				MonolithItems.meanium, 400
			));
			size = 5;
			health = 450;
			craftTime = 60f * 60f * 3f;
			unit = MonolithUnitTypes.remnant;
			itemCapacity = 100;
			consumeItems(with(Items.silicon, 25, MonolithItems.meanium, 50));
			consumeLiquid(Liquids.oil, 0.5f);
			consumePower(3f);
		}};
	}
}