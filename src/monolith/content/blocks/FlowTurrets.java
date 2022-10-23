package flow.content.blocks;

import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.*;

public class FlowTurrets {
	public static Block holder;

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

			ammo(
				Items.copper, new BasicBulletType(2f, 20, "mine-bullet") {{
					lifetime = 100;
					width = height = 10f;
					knockback = 32f;
				}},
				Items.graphite, new BasicBulletType(2f, 50, "mine-bullet") {{
					lifetime = 100;
					width = height = 10f;
					knockback = 64f;
				}}
			);
		}};
	}
}