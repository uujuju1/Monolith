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
	public Seq<Biome> biomes = new Seq<>();
	public Block defaultBlock = Blocks.stone;

	public class Biome {
		public Block[] heightMap;
		public int noiseSeed = 0;
		public float minValue = 0f, maxValue = 1f, magnitude = 1f;
		public double xOffset = 0, yOffset = 0, zOffset = 0, octaves = 3, persistence = 0.5, scale = 1;

		public float noise(Vec3 pos) {
			return Simplex.noise3d(seed, octaves, persistence, scale, pos.x + xOffset, pos.y + yOffset, pos.z + zOffset) * magnitude;
		}
		public @Nullable Block getBlock(Vec3 pos) {
			Block res = heightMap[Mathf.clamp((int) (noise(pos) * (heightMap.length - 1f)), 0, heightMap.length - 1)];
			return (noise(pos) < minValue || noise(pos) > maxValue) ? null : res;
		}
	}
	
	float rawHeight(Vec3 pos) {return Simplex.noise3d(seed, 7, 0.5f, 2.3f, pos.x, pos.y, pos.z);}
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
	protected void generate() {pass((x, y) -> floor = getBlock(sector.tile.v));}
}