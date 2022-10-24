package flow.content.blocks;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.*;

public class FlowTurrets {
	public static Block holder, pusher;

	public static void load() {
		holder = new ItemTurret("holder") {{
			requirements(Category.turret, with(
				Items.copper, 75,
				Items.lead, 100,
				Items.graphite, 50
			));
			size = 2;
			health = 560;
			reload = 120;
			range = 200;
			recoil = 2;
			shake = 2;
			ammoPerShot = 2;
			targetAir = false;
			shootSound = Sounds.mediumCannon;

			ammo(
				Items.copper, new BasicBulletType(2f, 10, "mine-bullet") {{
					lifetime = 100f;
					width = height = 14f;
					knockback = 24f;
				}},
				Items.graphite, new BasicBulletType(2f, 20, "mine-bullet") {{
					lifetime = 100f;
					width = height = 16f;
					knockback = 48f;
					ammoMultiplier = 2;
				}}
			);
		}};
		pusher = new ItemTurret("pusher") {{
			requirements(Category.turret, with(
				Items.copper, 125,
				Items.graphite, 130,
				Items.titanium, 120
			));
			size = 3;
			health = 1200;
			range = 120f;
			recoil = 3;
			shake = 3;
			ammoPerShot = 3;
			targetAir = false;
			shootSound = Sounds.mediumCannon;

			ammo(
				Items.graphite, new BasicBulletType(1.5f, 40) {{
					lifetime = 80f;
					width = height = 20f;
					knockback = 32f;
					ammoMultiplier = 3;
				}},
				Items.silicon, new BasicBulletType(1.2f, 30) {{
					lifetime = 100f;
					width = height = 18f;
					knockback = 28f;
					homingPower = 0.08f;
				}},
				Items.thorium, new BasicBulletType(1f, 60) {{
					lifetime = 120f;
					width = height = 24f;
					knockback = 48f;
					ammoMultiplier = 2;
				}}
			);
		}};
	}
}