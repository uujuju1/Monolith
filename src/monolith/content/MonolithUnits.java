package monolith.content;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.blocks.units.UnitFactory.*;
import monolith.type.*;
import monolith.type.draw.*;
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
			engineSize = 0f;
			fallSpeed = 0.007f;
			range = maxRange = 135f;
			flying = lowAltitude = true;
			constructor = CopterComp::new;
			immunities.add(MonolithStatusEffects.isolated);
			setEnginesMirror(
				new PressureEngine(4f, 0f, 15, 4f, 8f, 45f)
			);
			rotors.add(
				new Rotor("-rotor", 0, 4, 15, false),
				new Rotor("-rotor-small", 0, -7, -15, true)
			);
			weapons.addAll(
				new Weapon("monolith-connect-weapon") {{
					x = 5.75f;
					y = 4.5f;
					reload = 60f;
					recoil = 1f;
					shootCone = 2f;
					ignoreRotation = false;
					shootSound = Sounds.mediumCannon;
					bullet = new BasicBulletType(6f, 20) {{
						lifetime = 22.5f;
						frontColor = Color.valueOf("FFCBDD");
						backColor = trailColor = Color.valueOf("CF85CB");
						width = 10f;
						height = 12.5f;
						trailWidth = 2;
						trailLength = 10;
						status = MonolithStatusEffects.isolated;
						statusDuration = 180f;
					}};
				}}
			);
		}};

		// plans
		((UnitFactory) Blocks.groundFactory).plans.addAll(new UnitPlan(shelter, 120 * 60, ItemStack.with(Items.silicon, 25, MonolithItems.macrosteel, 20)));
		((UnitFactory) Blocks.airFactory).plans.addAll(new UnitPlan(connect, 120 * 60, ItemStack.with(Items.silicon, 25, MonolithItems.lithium, 20, Items.plastanium, 15)));
	}
}