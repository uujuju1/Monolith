package monolith.content;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.blocks.units.UnitFactory.*;
import monolith.type.*;

public class MonolithUnits {
	public static UnitType shelter;

	public void load() {
		// units
		shelter = new SubmersibleUnitType("shelter") {{
			health = 1650;
			speed = 1f;
			range = maxRange = 25f * 8f;
			hitSize = 12f;
			outlineColor = Pal.darkOutline;
			constructor = MechUnit::create;
			immunities.add(MonolithStatusEffects.overrun);

			weapons.add(
				new Weapon("monolith-shelter-cannon") {{
					x = 9.5f;
					y = 0f;
					reload = 60f;
					recoil = 2f;
					shootY = 4f;
					shootSound = Sounds.artillery;
					bullet = new BasicBulletType(2.5f, 60) {{
						width = height = 16f;
						lifetime = 80f;
					}};
				}},
				new Weapon("monolith-shelter-mount") {{
					x = 3f;
					y = -6f;
					reload = 30f;
					recoil = 2f;
					bullet = new BasicBulletType(2.5f, 15) {{
						lifetime = 80f;
					}};
				}}
			);
		}};

		// plans
		((UnitFactory) Blocks.groundFactory).plans.addAll(new UnitPlan(shelter, 120 * 60, ItemStack.with(Items.silicon, 25, MonolithItems.macrosteel, 20)));
	}
}