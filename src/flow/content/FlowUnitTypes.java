package flow.content;

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
import flow.type.*;
import flow.type.draw.*;
import flow.type.ModUnitType.*;

public class FlowUnitTypes {
	public static UnitType 
	moth, 
	shelter, connect, vessel, remnant;

	public static void load() {
		moth = new UnitType("moth") {{
			health = 650;
			speed = 2f;
			range = maxRange = 18f * 8f;
			hitSize = 9f;
			engineSize = 4f;
			engineOffset = 6f;
			outlineColor = Pal.darkOutline;
			flying = true;
			constructor = UnitEntity::create;

			weapons.addAll(
				new Weapon("flow-moth-weapon") {{
					x = 6.25f;
					y = -1f;
					reload = 30f;
					shootSound = Sounds.chargedShock;
					bullet = new BasicBulletType(2f, 50) {{
						widht = height = 10f;
						lifetime = 72f;
						frontColor = Color.white;
						backColor = Pal.accent;
					}};
				}}
			);
		}};

		shelter = new ModUnitType("shelter") {{
			health = 1650;
			speed = 1f;
			range = maxRange = 25f * 8f;
			hitSize = 12f;
			outlineColor = Pal.darkOutline;
			canDrown = false;
			constructor = MechUnit::create;
			immunities.add(FlowStatusEffects.overrun);

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

			immunities.add(FlowStatusEffects.isolated);

			setEnginesMirror(new GasEngine(4f, 0f, 15, 4f, 8f, 160f, Color.gray));
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
						status = FlowStatusEffects.isolated;
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

			immunities.add(FlowStatusEffects.isolated);

			rotors.add(
				new Rotor("-rotor", 0f, 6f, 15f) {{
					layerOffset = 0.001f;
				}},
				new Rotor("-rotor-back", 0f, -8f, -15f) {{
					layerOffset = -0.001f;
				}}
			);

			parts.addAll(
				new RegionPart("-blade") {{
					x = 2.25f;
					y = 15.15f;
					moveX = 2f;
					mirror = true;
					layerOffset = -0.001f;
					outlineLayerOffset = -0.002f;
					moves.add(new PartMove(PartProgress.reload, 0f, -1f, 0f));
				}}
			);

			weapons.add(
				new Weapon() {{
					x = 0f;
					y = 15.15f;
					recoil = 0f;
					reload = 120f;
					mirror = false;
					bullet = Bullets.placeholder;
				}},
				new Weapon("monolith-vessel-cannon") {{
					x = 10.75f;
					y = 8f;
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
						}};
					}}
				);
			}
		}};
	}
}