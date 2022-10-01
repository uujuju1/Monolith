package monolith.type.weathers;

import arc.*;
import arc.util.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.graphics.g2d.*;
import arc.graphics.Texture.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;

public class StormWeather extends Weather {
	public String noisePath;

	public Vec2 
	speed = new Vec2(50f, 5f),
	noiseSpeed = new Vec2(2.5f, 0.25f);

	public Liquid liquid = Liquids.water;

	public Color color = liquid.color, noiseColor = liquid.color;

	public TextureRegion[] splashes = new TextureRegion[12];
	public Texture noiseRegion;

	public float
	stroke = 1f,
	minSize = 8f,
	maxSize = 200f,
	rainDensity = 150f,
	splashScl = 22f,

	noiseLayerSpeedM = 1.1f, 
	noiseLayerAlphaM = 0.8f, 
	noiseLayerSclM = 0.99f, 
	noiseLayerColorM = 1f,
	noiseBaseSpeed = 6.1f,
	noiseScale = 2000f;

	public int noiseLayers = 1;

	public JupiterStormWeather(String name, String noisePath) {
		super(name);
		this.noisePath = noisePath;
	}
	public JupiterStormWeather(String name) {
		this(name, "noiseAlpha");
	}

	@Override
	public void load() {
		super.load();
		if(Core.assets != null){
			Core.assets.load("sprites/" + noisePath + ".png", Texture.class);
		}
		for (int i = 0; i < splashes.length; i++) {
			splashes[i] = Core.atlas.find("splash-" + i);
		}
	}

	@Override
	public void drawOver(WeatherState state) {
		drawRain(minSize, maxSize, speed.x, speed.x, rainDensity, state.intensity, stroke, color);
	}

	@Override
	public void drawUnder(WeatherState state) {
		drawNoise(state);
		drawSplashes(splashes, maxSize, rainDensity, state.intensity, state.opacity, splashScl, stroke, color, liquid);
	}

	public void drawNoise(WeatherState state) {
		float windSpeed = noiseBaseSpeed * state.intensity;

		float 
		windx = noiseSpeed.x * windSpeed,
		windy = noiseSpeed.y * windSpeed;

		if(noiseRegion == null){
			noiseRegion = Core.assets.get("sprites/" + noisePath + ".png", Texture.class);
			noiseRegion.setWrap(TextureWrap.repeat);
			noiseRegion.setFilter(TextureFilter.linear);
		}

		float sspeed = 1f, sscl = 1f, salpha = 1f, offset = 0f;

		Color col = Tmp.c1.set(noiseColor);

		for(int i = 0; i < noiseLayers; i++){
			drawNoise(noiseRegion, noiseColor, noiseScale * sscl, state.opacity * salpha * opacityMultiplier, sspeed * noiseBaseSpeed, state.intensity, windx, windy, offset);
			sspeed *= noiseLayerSpeedM;
			salpha *= noiseLayerAlphaM;
			sscl *= noiseLayerSclM;
			offset += 0.29f;
			col.mul(noiseLayerColorM);
		}
	}
}