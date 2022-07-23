package monolith.content;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.world.meta.*;
import mindustry.type.weather.*;

public class MonolithWeathers {
	public static Weather storm;

	public void load() {
		storm = new ParticleWeather("storm"){{
			duration = 60f * 60f;
			noiseLayers = 3;
			noiseLayerSclM = 0.8f;
			noiseLayerAlphaM = 0.7f;
			noiseLayerSpeedM = 2f;
			noiseLayerSclM = 0.6f;
			baseSpeed = 0.05f;
			color = noiseColor = Color.valueOf("302E3B");
			noiseScale = 1100f;
			noisePath = "noiseAlpha";
			drawNoise = true;
			useWindVector = false;
			xspeed = 2f;
			yspeed = 3f;
			attrs.set(Attribute.light, -0.3f);
			attrs.set(Attribute.water, 2f);
			status = MonolithStatusEffects.overrun;
			sound = Sounds.rain;
			opacityMultiplier = 0.47f;
		}};
	}
}