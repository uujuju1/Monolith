package monolith.content;

import mindustry.type.*;
import mindustry.content.*;

public class MonolithStatusEffects {
	public static StatusEffect overrun;

	public void load() {
		overrun = new StatusEffect("overrun") {{
			damage = 5;
			speedMultiplier = reloadMultiplier = reloadMultiplier = damageMultiplier = 0.3f;
			healthMultiplier = 0.5f;
			init(() -> {
				opposite(StatusEffects.burning, StatusEffects.melting);
			});
		}};
	}
}