package monolith.content;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.blocks.units.UnitFactory.*;
import monolith.type.*;
import monolith.entities.comp.*;
import monolith.type.ModUnitType.*;

public class MonolithUnits {
	public static UnitType shelter, connect;

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

		connect = new ModUnitType("connect") {{
			health = 450;
			speed = 1f;
			hitSize = 9f;
			lowAltitude = true;
			constructor = CopterComp::new;
			immunities.add(MonolithStatusEffects.isolated);
			rotors.add(
				new Rotor("monolith-connect-rotor", 0, 4, 15, true),
				new Rotor("monolith-connect-rotor-small", 0, -7, 15, false)
			);
		}};

		// plans
		((UnitFactory) Blocks.groundFactory).plans.addAll(new UnitPlan(shelter, 120 * 60, ItemStack.with(Items.silicon, 25, MonolithItems.macrosteel, 20)));
		((UnitFactory) Blocks.airFactory).plans.addAll(new UnitPlan(connect, 120 * 60, ItemStack.with(Items.silicon, 25, MonolithItems.lithium, 20, Items.plastanium, 15)));
	}
}