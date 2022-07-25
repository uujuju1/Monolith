package monolith.content;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.entities.bullet.*;
import monolith.type.*;

public class MonolithUnits {
	public static UnitType shelter;

	public void load() {
		shelter = new SubmersibleUnitType("shelter") {{
			health = 1650;
			speed = 1f;
			range = maxRange = 25f * 8f;
			hitSize = 12f;
			constructor = MechUnit::create;
			immunities.add(MonolithStatusEffects.overrun);

			weapons.add(
				new Weapon("monolith-shelter-cannon") {{
					x = 9.5f;
					y = 0f;
					reload = 60f;
					recoil = 2f;
					bullet = new BasicBulletType(2.5f, 60) {{
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
	}
}