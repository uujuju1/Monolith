package monolith.content;

import mindustry.type.*;
import mindustry.content.*;

public class MonolithStatusEffects {
	public static StatusEffect overrun;

	public void load() {
		overrun = new StatusEffect("overrun") {{
			damage = 5;
			init(() -> {
				opposite(StatusEffects.burning, StatusEffects.melting);
			});
		}};
	}
}