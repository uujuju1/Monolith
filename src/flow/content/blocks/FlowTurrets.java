
package flow.content.blocks;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.world.blocks.defense.turrets.*;
import flow.content.*;
import flow.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.*;

public class FlowTurrets {
	public static Block 
	trebuchet,

	holder, pusher,
	shrapnel;

	public static void load() {
		trebuchet = new Mortar("trebuchet") {{
			requirements(Category.turret, with(
				Items.graphite, 60,
				Items.copper, 85,
				Items.lead, 100,
				Items.silicon, 75
			));
			size = 3;
			health = 1050;
			reload = 60f;
			range = 200f;
			minRange = 80f;
			shake = 3f;
			targetAir = false;
			shootSound = Sounds.mediumCannon;

			ammo(
				Items.lead, new ArtilleryBulletType(2f, 80) {{
					lifetime = 100f;
					width = height = 32f;
					splashDamage = 20f;
					splashDamageRadius = 80f;
					despawnEffect = hitEffect = new MultiEffect(FlowFx.trebuchetShoot, FlowFx.trebuchetSmoke);
					hitColor = frontColor;
				}},
				Items.graphite, new ArtilleryBulletType(2f, 120) {{
					lifetime = 100f;
					width = height = 32f;
					splashDamage = 40f;
					splashDamageRadius = 40f;
					despawnEffect = hitEffect = new MultiEffect(FlowFx.trebuchetShoot, FlowFx.trebuchetSmoke);
					hitColor = frontColor;
				}}
			);
		}};

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
			reload = 60f;
			recoil = 3;
			shake = 3;
			ammoPerShot = 3;
			targetAir = false;
			shootSound = Sounds.mediumCannon;

			ammo(
				Items.graphite, new BasicBulletType(1.5f, 40, "mine-bullet") {{
					lifetime = 80f;
					width = height = 20f;
					knockback = 32f;
					ammoMultiplier = 3;
				}},
				Items.silicon, new BasicBulletType(1.2f, 30, "mine-bullet") {{
					lifetime = 100f;
					width = height = 18f;
					knockback = 28f;
					homingPower = 0.08f;
				}},
				Items.thorium, new BasicBulletType(1f, 60, "mine-bullet") {{
					lifetime = 120f;
					width = height = 24f;
					knockback = 48f;
					ammoMultiplier = 2;
				}}
			);
		}};

		shrapnel = new ItemTurret("shrapnel") {{
			requirements(Category.turret, with(
				Items.lead, 150,
				Items.graphite, 135,
				Items.silicon, 140
			));
			size = 2;
			health = 520;
			range = 80f;
			recoil = 2f;
			shake = 2f;
			inaccuracy = 15f;
			targetGround = false;
			shootSound = Sounds.shotgun;
			shoot = new ShootPattern() {{shots = 6;}};

			ammo(
				Items.graphite, new BasicBulletType(3.2f, 10) {{
					lifetime = 25f;
					width = height = 8f;
				}}
			);
		}};
	}
}