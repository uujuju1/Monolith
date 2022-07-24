package monolith.content;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.world.meta.*;
import mindustry.type.weather.*;
import monolith.type.weathers.*;

public class MonolithWeathers {
	public static Weather storm;

	public void load() {
		storm = new JupiterStormWeather("storm"){{
			duration = 1.5f * (60f * 60f);
			rainDensity = 150f;
			attrs.set(Attribute.light, -0.3f);
			attrs.set(Attribute.water, 2f);
			status = MonolithStatusEffects.overrun;
			sound = Sounds.rain;
		}};
	}
}