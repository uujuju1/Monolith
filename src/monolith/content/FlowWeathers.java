package flow.content;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.world.meta.*;
import mindustry.type.weather.*;
import flow.type.weathers.*;

public class FlowWeathers {
	public static Weather storm;

	public static void load() {
		storm = new StormWeather("storm"){{
			duration = 1.5f * (60f * 60f);
			rainDensity = 150f;
			noiseSpeed.set(0.125f, 0.0125f);
			attrs.set(Attribute.light, -0.3f);
			attrs.set(Attribute.water, 2f);
			status = FlowStatusEffects.overrun;
			sound = Sounds.rain;
		}};
	}
}