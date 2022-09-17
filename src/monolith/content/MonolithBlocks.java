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
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.defense.turrets.*;

import monolith.type.*;
import monolith.blocks.voidf.*;
import monolith.blocks.defense.*;
import monolith.blocks.payload.*;
import monolith.blocks.distribution.*;
import monolith.blocks.voidf.sandbox.*;
import monolith.blocks.voidf.defense.*;
import monolith.blocks.voidf.production.*;
import monolith.blocks.voidf.distribution.*;

import static mindustry.type.ItemStack.*;

public class MonolithBlocks {
	public static Block 
	itemLiquidJunction,

	furnace, lithiumWeaver, alloyInfuser,

	move, accelerate,
	revenant,
	caesar, vigenere,

	sparkWall,
	meaniumWall, meaniumWallLarge,

	payloadCrucible,

	artifact,

	voidfConveyor, 
	voidfRouter,

	voidfTank,

	voidfCrafter, voidfFactory,
	macroSmelter,

	coreollis, magnetar,

	voidfVoid, voidfSource;

	public void load() {
		itemLiquidJunction = new ItemLiquidJunction("item-liquid-junction") {{
			requirements(Category.liquid, with(
				Items.metaglass, 2,
				Items.copper, 3,
				MonolithItems.macrosteel, 1
			));
			size = 1;
			health = 60;
		}};

		furnace = new GenericCrafter("furnace") {{
			requirements(Category.crafting, with(
				Items.graphite, 120,
				Items.silicon, 180
			));
			size = 3;
			health = 200;
			craftTime = 120;
			craftEffect = MonolithFx.furnaceSmelt;
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
				new BulletRecipe("standard", with(Items.copper, 12, Items.silicon, 6)) {{
					damage = 45f;
					range = 120f;
					reloadTime = 180f;
					shootEffect = MonolithFx.aoeShoot;
				}},
				new BulletRecipe("heavy-graphite", with(Items.graphite, 16, Items.silicon, 8)) {{
					damage = 90f;
					range = 180f;
					reloadTime = 240f;
					shootEffect = MonolithFx.aoeShoot;
				}},
				new BulletRecipe("pyra", with(Items.pyratite, 14, Items.silicon, 7)) {{
					damage = 30f;
					range = 120f;
					reloadTime = 60f;
					shootEffect = MonolithFx.aoeShoot;
					statuses = new StatusEffect[]{StatusEffects.burning};
					statusDurations = new float[]{30f};
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
				new BulletRecipe("heavy-graphite", with(Items.graphite, 26, Items.silicon, 13)) {{
					damage = 140f;
					range = 200f;
					reloadTime = 240f;
					shootEffect = MonolithFx.aoeShoot;
				}},
				new BulletRecipe("heavy-thorium", with(Items.thorium, 30, Items.silicon, 15)) {{
					damage = 200f;
					range = 240f;
					reloadTime = 320f;
					shootEffect = MonolithFx.aoeShoot;
				}},
				new BulletRecipe("explosive", with(Items.blastCompound, 28, Items.silicon, 14)) {{
					damage = 150f;
					range = 220f;
					reloadTime = 400f;
					shootEffect = MonolithFx.aoeShoot;
					statuses = new StatusEffect[]{StatusEffects.blasted};
					statusDurations = new float[]{120f}; 
				}},
				new BulletRecipe("cryo", with(Items.titanium, 28, Items.silicon, 14)) {{
					damage = 150f;
					range = 220f;
					reloadTime = 400f;
					shootEffect = MonolithFx.aoeShoot;
					statuses = new StatusEffect[]{StatusEffects.freezing};
					statusDurations = new float[]{120f}; 
				}}
			);
		}};

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

		payloadCrucible = new PayloadCrafter("payload-crucible") {{
			requirements(Category.units, with(
				Items.copper, 1
			));
			size = 3;
			health = 260;
			plans.addAll(
				new Recipe(meaniumWallLarge, with(Items.titanium, 16, Items.coal, 40), 120f),
				new Recipe(Blocks.container, with(Items.titanium, 50, Items.silicon, 25), 180f)
			);
			itemCapacity = 100;
		}};

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

		voidfConveyor = new VoidfConveyor("void-conveyor") {{
			requirements(Category.distribution, with(
				MonolithItems.lithium, 1,
				Items.graphite, 1,
				Items.plastanium, 1
			));
			size = 1;
			health = 40;
		}};
		voidfRouter = new VoidfRouter("void-router") {{
			requirements(Category.distribution, with(
				MonolithItems.lithium, 3,
				Items.graphite, 3,
				Items.plastanium, 1
			));
			size = 1;
			health = 60;
		}};

		voidfTank = new VoidfRouter("void-tank") {{
			requirements(Category.distribution, with(
				MonolithItems.lithium, 75,
				Items.graphite, 12,
				Items.metaglass, 13,
				Items.plastanium, 25
			));
			size = 2;
			health = 350;
			maxVoidf = 1000;
			transferRate = 0.01f;
		}};

		voidfVoid = new VoidfVoid("void-void") {{
			buildVisibility = BuildVisibility.sandboxOnly;
			size = 1;
			health = 1000000000;
		}};
		voidfSource = new VoidfSource("void-source") {{
			buildVisibility = BuildVisibility.sandboxOnly;
			size = 1;
			health = 1000000000;
		}};

		voidfCrafter = new VoidfCrafter("void-crafter") {{
			requirements(Category.crafting, with(
				MonolithItems.lithium, 150,
				MonolithItems.macrosteel, 200,
				Items.silicon, 125,
				Items.graphite, 125
			));
			size = 2;
			health = 160;
			craftTime = 10;
			drawer = new DrawMulti(new DrawDefault());
			consumeItems(with(
				Items.plastanium, 1,
				Items.coal, 1
			));
			consumeLiquid(Liquids.water, 0.01f);
			consumePower(2f);
			voidfOutput = 10f;
		}};
		voidfFactory = new VoidfCrafter("void-factory") {{
			requirements(Category.crafting, with(
				MonolithItems.lithium, 150,
				MonolithItems.macrosteel, 200,
				Items.silicon, 125,
				Items.graphite, 125,
				Items.titanium, 175,
				Items.plastanium, 200
			));
			size = 3;
			health = 250;
			craftTime = 10;
			drawer = new DrawMulti(new DrawDefault());
			consumeItems(with(
				Items.plastanium, 1,
				Items.coal, 2
			));
			consumeLiquid(Liquids.water, 0.1f);
			consumePower(2f);
			voidfOutput = 25f;
		}};

		macroSmelter = new VoidfCrafter("macro-smelter") {{
			requirements(Category.crafting, with(
				MonolithItems.lithium, 150,
				Items.silicon, 200,
				Items.graphite, 250,
				Items.lead, 150,
				Items.copper, 200
			));
			size = 3;
			health = 300;
			craftTime = 120;
			itemCapacity = 30;
			drawer = new DrawMulti(new DrawDefault());
			consumeItems(with(
				MonolithItems.lithium, 3,
				Items.graphite, 5,
				Items.sand, 7
			));
			consumePower(3f);
			consumeVoidf = 25;
			outputItems = with(MonolithItems.macrosteel, 5, Items.silicon, 6);
		}};

		coreollis = new VoidfSprayer("coreollis") {{
			requirements(Category.turret, with(
				MonolithItems.lithium, 125,
				Items.plastanium, 75,
				Items.titanium, 100,
				Items.graphite, 80
			));
			size = 2;
			health = 350;
			consumeVoidf = 10f;
			action = build -> {
				Damage.status(build.team, build.x, build.y, range, MonolithStatusEffects.isolated, 60f, true, true);
			};
		}};
		magnetar = new VoidfSprayer("magnetar") {{
			requirements(Category.turret, with(
				MonolithItems.lithium, 225,
				Items.plastanium, 175,
				Items.titanium, 200,
				Items.thorium, 150,
				Items.graphite, 180
			));
			size = 3;
			health = 750;
			consumeVoidf = 10f;
			range = 160f;
			action = build -> {
				Damage.status(build.team, build.x, build.y, range, MonolithStatusEffects.isolated, 120f, true, true);
			};
		}};
	}
}