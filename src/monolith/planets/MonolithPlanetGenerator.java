package monolith.planets;

import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.maps.generators.*;

public class MonolithPlanetGenerator extends PlanetGenerator {
	public float minHeight = 0.1f;
	public double scale = 2.3, persistence =  0.5, octaves = 7;
	public Seq<Biome> biomes = new Seq<>();
	public Cons<MonoloithPlanetGenerator> gen = p -> p.pass((x, y) -> {p.floor = getBlock(p.sector.tile.v);});
	public Block defaultBlock = Blocks.stone;

	public class Biome {
		// array tileset, i reccomend 10 - 13 blocks here
		public Block[] heightMap;

		// equator to pole interpolation
		public Interp polarInterp = Interp.one;

		// seed for noise
		public int noiseSeed = 0;

		public float 
		// height that the biome starts
		minValue = 0f,
		// height that the biome ends
		maxValue = 1f,

		// noise magnitude
		magnitude = 1f;

		/**
			@param clampHeight will override any biome before it when set to true
			clamps noise onto min and max values
			this will apply the entire noise height map onto the planet
		*/
		public boolean clampHeight = false;

		public double
		// x, y and z noise offsets
		xOffset = 0,
		yOffset = 0,
		zOffset = 0,

		// clamps npoise onto min and max values
		public boolean clampHeight = false;

		// noise octaves
		octaves = 3, 
		// noise persistence
		persistence = 0.5,
		// noise scale
		scale = 1;

		public float noise(Vec3 pos) {
			float noise = Simplex.noise3d(seed, octaves, persistence, scale, pos.x + xOffset, pos.y + yOffset, pos.z + zOffset) * magnitude * polarInterp.apply(Math.abs(pos.y))
			return clampHeight ? Mathf.clamp(Math.max(0, noise), minValue, maxValue) : Math.max(0, noise);
		}
		public @Nullable Block getBlock(Vec3 pos) {
			Block res = heightMap[Mathf.clamp((int) (noise(pos) * (heightMap.length - 1f)), 0, heightMap.length - 1)];
			return (noise(pos) < minValue || noise(pos) > maxValue) ? null : res;
		}
	}
	
	float rawHeight(Vec3 pos) {return Simplex.noise3d(seed, octaves, persistence, scale, pos.x, pos.y, pos.z);}
	Block getBlock(Vec3 pos) {
		@Nullable Block res = null;
		for (Biome biome : biomes) if(biome.getBlock(pos) != null) res = biome.getBlock(pos);
		return res == null ? defaultBlock : res;
	}

	@Override
	public float getHeight(Vec3 pos) {return Math.max(rawHeight(pos), minHeight);}

	@Override
	public Color getColor(Vec3 pos) {return getBlock(pos).mapColor;}

	@Override
	protected void generate() {gen.get(this);}
}