package monolith.content;

import arc.math.*;
import arc.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.part.*;
import mindustry.entities.bullet.*;
import mindustry.entities.abilities.*;
import mindustry.annotations.Annotations.*;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.blocks.units.UnitFactory.*;
import monolith.type.*;
import monolith.type.draw.*;
import monolith.type.ModUnitType.*;

public class MonolithUnitTypes {
	public static @EntityDef({Unitc.class, Mechc.class}) UnitType shelter;

	public static @EntityDef({Unitc.class}) UnitType connect, vessel, remnant;

	public static void load() {
		shelter = new ModUnitType("shelter") {{
			health = 1650;
			speed = 1f;
			range = maxRange = 25f * 8f;
			hitSize = 12f;
			outlineColor = Pal.darkOutline;
			canDrown = false;
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
			speed = 2f;
			accel = 0.1f;
			drag = 0.06f;
			hitSize = 9f;
			engineSize = 0f;
			fallSpeed = 0.007f;
			range = maxRange = 135f;
			outlineColor = Pal.darkOutline;
			flying = lowAltitude = true;
			constructor = UnitEntity::create;

			immunities.add(MonolithStatusEffects.isolated);

			setEnginesMirror(new PressureEngine(4f, 0f, 15, 4f, 8f, 160f, Color.gray));
			engines.get(1).rotation = -160f;

			rotors.add(
				new Rotor("-rotor", 0, 4, 15) {{
					layerOffset = 0.001f;
				}},
				new Rotor("-rotor-small", 0, -7, -15) {{
					layerOffset = -0.001f;
				}}
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
		vessel = new ModUnitType("vessel") {{
			health = 4350f;
			speed = 3f;
			accel = 0.1f;
			drag = 0.06f;
			hitSize = 21.5f;
			engineSize = 0f;
			fallSpeed = 0.003f;
			range = maxRange = 160f;
			outlineColor = Pal.darkOutline;
			flying = true;
			constructor = UnitEntity::create;

			immunities.add(MonolithStatusEffects.isolated);

			rotors.add(
				new Rotor("-rotor", 0f, 0f, 15f) {{
					layerOffset = 0.001f;
				}},
				new Rotor("-rotor-back", 0f, 0f, -15f) {{
					layerOffset = -0.001f;
				}}
			);

			parts.addAll(
				new RegionPart("-blade") {{
					x = 0f;
					y = 0f;
					moveRot = 90f;
					layerOffset = -0.001f;
					outlineLayerOffset = -0.002f;
				}}
			);

			weapons.add(
				new Weapon() {{
					x = 0f;
					y = 20f;
					recoil = 0f;
					reload = 120f;
					mirror = false;
					bullet = Bullets.placeholder;
				}},
				new Weapon("monolith-vessel-cannon") {{
					x = 20f;
					y = 0f;
					recoil = 2f;
					reload = 60f;
					bullet = Bullets.placeholder;
				}}
			);
		}};

		remnant = new ModUnitType("remnant") {{
			health = 1450;
			speed = 5f;
			accel = 0.01f;
			drag = 0.01f;
			hitSize = 12f;
			engineSize = 0f;
			fallSpeed = 0.005f;
			range = maxRange = 0f;
			flying = true;
			constructor = UnitEntity::create;

			abilities.add(new MoveLightningAbility(25, 10, 0.1f, hitSize, 1, 5, Pal.accent, "monolith-remnant-shield"));

			rotors.add(
				new Rotor("-rotor", 0, 0, 18) {{
					layerOffset = 0.001f;
					doubleRot = true;
				}}
			);

			for (int i : Mathf.signs) {
				weapons.addAll(
					new Weapon("monolith-remnant-weapon") {{
						x = 8f * i;
						y = 4f;
						reload = 30f;
						baseRotation = -45 * i;
						shootCone = 180f;
						layerOffset = -0.001f;
						shootSound = Sounds.mediumCannon;
						mirror = false;
	
						parts.addAll(
							new RegionPart("-blade") {{
								moveRot = -15f;
								mirror = true;
							}}
						);
	
						bullet = new BasicBulletType(4f, 50) {{
							lifetime = 45f;
							homingPower = 0.25f;
							homingDelay = 4f;
							frontColor = Color.white;
							backColor = trailColor = Pal.accent;
							width = 10f;
							height = 12.5f;
							trailWidth = 2;
							trailLength = 10;
							status = MonolithStatusEffects.isolated;
							statusDuration = 180f;
						}};
					}}
				);
			}
		}};

		// plans
		((UnitFactory) Blocks.groundFactory).plans.addAll(new UnitPlan(shelter, 120 * 60, ItemStack.with(Items.silicon, 25, MonolithItems.macrosteel, 20)));
		((UnitFactory) Blocks.airFactory).plans.addAll(new UnitPlan(connect, 120 * 60, ItemStack.with(Items.silicon, 25, MonolithItems.lithium, 20, Items.plastanium, 15)));
	}
}