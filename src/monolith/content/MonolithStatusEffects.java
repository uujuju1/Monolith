package monolith.content;

import mindustry.type.*;
import mindustry.content.*;

public class MonolithStatusEffects {
	public static StatusEffect overrun, isolated;

	public static void load() {
		overrun = new StatusEffect("overrun") {{
			damage = 5;
			speedMultiplier = reloadMultiplier = damageMultiplier = 0.3f;
			healthMultiplier = 0.5f;
			init(() -> {
				opposite(StatusEffects.burning, StatusEffects.melting);
			});
		}};
		isolated = new StatusEffect("isolated") {{
			speedMultiplier = reloadMultiplier = 0.5f;
			healthMultiplier = 0.7f;
			init(() -> {
				opposite(StatusEffects.shocked);
			});
		}};
	}
}