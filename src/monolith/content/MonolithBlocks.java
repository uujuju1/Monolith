package monolith.content;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.defense.turrets.*;

import monolith.blocks.defense.*;
import monolith.blocks.distribution.*;

import static mindustry.type.ItemStack.*;

public class MonolithBlocks {
	public static Block 
	itemLiquidJunction,

	furnace,

	move, accelerate,
	caesar, vigenere;

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
					statuses = new StatusEffect[]{StatusEffects.blasted};
					statusDurations = new float[]{120f}; 
				}},
				new BulletRecipe("cryo", with(Items.titanium, 28, Items.silicon, 14)) {{
					damage = 150f;
					range = 220f;
					reloadTime = 400f;
					statuses = new StatusEffect[]{StatusEffects.freezing};
					statusDurations = new float[]{120f}; 
				}}
			);
		}};
	}
}