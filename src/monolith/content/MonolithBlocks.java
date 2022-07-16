package monolith.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.*;

public class MonolithBlocks {
	public static Block 
	furnace,

	move;

	public void load() {
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
					backColor = trailColor = Color.valueOf("95ABD9");
					trailWidth = 4f;
					trailLength = 16f;
					shootEffect = shootEff;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					rangeChange = 16f;
				}},
				Items.silicon, new BasicBulletType(2.5f, 30) {{
					width = height = 10f;
					lifetime = 54.4f;
					frontColor = Color.white;
					backColor = trailColor = Color.valueOf("B0BAC0");
					trailWidth = 4f;
					trailLength = 16f;
					shootEffect = shootEff;
					hitEffect = despawnEffect = Fx.hitBulletColor;
					homingRange = 40f;
					homingPower = 0.07f;
				}},
				MonolithItems.macrosteel, new BasicBulletType(3f, 40) {{
					width = height = 12f;
					lifetime = 45.33f;
					frontColor = Color.white;
					backColor = trailColor = Color.valueOf("B2B8FF");
					trailWidth = 4f;
					trailLength = 16f;
					shootEffect = shootEff;
					hitEffect = despawnEffect = Fx.hitBulletColor;
				}}
			);
		}};
	}
}