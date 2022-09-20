package monolith.planets;

import arc.math.*;
import arc.struct.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.maps.generators.*;

public class ChromaPlanetGenerator extends PlanetGenerator {
	public double octaves = 3f, persistence = 0.8f, scale = 0.01f;
	public float minHeight = 0.4f, noiseTresh = 0.5f, mag = 1f;
	public boolean forceOres = true;

	public Block[] arr = {Blocks.dirt, Blocks.dirt, Blocks.basalt};

	float rawHeight(Vec3 pos) {
		return Simplex.noise3d(seed, 7, 0.5f, 2.3f, pos.x, pos.y, pos.z);
	}
	float rawTemp(Vec3 pos) {
		float poles = Math.abs(pos.y);
		return Simplex.noise3d(seed + 2, 3f, 0.8f, 1, pos.x, pos.y, pos.z) * poles;
	}
	@Override
	public float getHeight(Vec3 pos) {
		return Math.max(minHeight, rawHeight(pos));
	}

	@Override
	public Color getColor(Vec3 pos) {
		return getBlock(pos).mapColor.cpy();
	}

	Block getBlock(Vec3 pos) {
		if (rawHeight(pos) < minHeight) {
			if (rawTemp(pos) < 0.5f) {
				return Blocks.water;
			} else {
				return Blocks.ice;
			}
		}
		if (rawTemp(pos) > 0.5f - 0.1f) {
			if (rawTemp(pos) > 0.5f) {
				return Blocks.snow;
			}
			return Blocks.iceSnow;
		}
		return arr[Mathf.clamp((int)(rawHeight(pos) * arr.length), 0, arr.length - 1)];
	}

	public float noise2d(int seed, float x, float y, double octaves, double persistence, double scale, float mag) {
		return Simplex.noise2d(seed, 3f, 0.8f, 0.01f, x, y) * mag;
	}
	public float noise2d(float x, float y, double octaves, double persistence, double scale, float mag) {
		return Simplex.noise2d(seed, 3f, 0.8f, 0.01f, x, y) * mag;
	}

	public void getOres() {
		Seq<Block> ores = Seq.with(Blocks.oreCopper, Blocks.oreLead);

		if(Simplex.noise3d(seed, 2, 0.5, 1, sector.tile.v.x, sector.tile.v.y + 2, sector.tile.v.z + 2) > 0.25f || forceOres){
			ores.add(Blocks.oreCoal);
		}

		if(Simplex.noise3d(seed, 2, 0.5, 1, sector.tile.v.x + 1, sector.tile.v.y + 1, sector.tile.v.z) > 0.5f || forceOres){
			ores.add(Blocks.oreTitanium);
		}

		if(Simplex.noise3d(seed, 2, 0.5, 1, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z + 1) > 0.7f || forceOres){
			ores.add(Blocks.oreThorium);
		}
		
		pass((x, y) -> {
			for (int i = 0; i < ores.size; i++) {
				if (noise(x + i*999, y + 1*-999, 2f, 0.2f, 30f, 1f) > 0.8f + (i * 2)) {
					ore = ores.get(i);
				}
			}
		});
	}

	@Override
	protected void generate() {
		pass((x, y) -> {
			floor = getBlock(sector.tile.v);

			// water region
			if (floor == Blocks.water) {
				float noise = noise2d(sector.tile.v.x + x, sector.tile.v.y + y, 3f, 0.8f, 0.01f, 1);

				if (noise < 0.5/1.5f) {
					floor = Blocks.deepwater;
				}
				if (noise > 0.5f) {
					floor = Blocks.dirt;
				}
				if (noise > 0.625f) {
					block = Blocks.dirtWall;
				}
				if (noise > 0.75f) {
					block = Blocks.duneWall;
				}
				if (x < 25 || x > width - 25 || y < 25 || y > height - 25) {
					floor = Blocks.dirt;
				}
				if (x < 15 || x > width - 15 || y < 15 || y > height - 15) {
					block = Blocks.dirtWall;
				}
				if (noise2d(x + sector.tile.v.x, y + sector.tile.v.y, 3d, 0.5d, 0.01d, 1f) > 0.8f && floor == Blocks.dirt) {
					floor = Blocks.carbonStone;
					if (block == Blocks.dirtWall) block = Blocks.carbonWall;
					if (block == Blocks.duneWall) block = Blocks.ferricStoneWall;
				}
			}

			// dirt region
			if (floor == Blocks.dirt) {
				float noise = noise2d(sector.tile.v.x + x, sector.tile.v.y + y, 3f, 0.8f, 0.1f, 1);

				if (noise > 0.625f) {
					block = Blocks.dirtWall;
				}
				if (noise > 0.75f) {
					block = Blocks.duneWall;
				}

				if (x < 47 || x > width - 47 || y < 47 || y > height - 47) {
					block = Blocks.dirtWall;
				}
				if (noise2d(x + sector.tile.v.x, y + sector.tile.v.y, 3d, 0.5d, 0.01d, 1f) > 0.5f && floor == Blocks.dirt) {
					floor = Blocks.carbonStone;
					if (block == Blocks.dirtWall) block = Blocks.carbonWall;
					if (block == Blocks.duneWall) block = Blocks.ferricStoneWall;
				}
			}

			// ice region
			if (floor == Blocks.ice || floor == Blocks.iceSnow) {
				float[] noises = new float[]{
					noise(x + 999, y + 999, octaves, persistence, scale, 1),
					noise(x + 1500, y + 999, octaves, persistence, scale, 1),
					noise(x + 999, y + 1500, octaves, persistence, scale, 1),
				};

				if (noises[0] > noiseTresh) {
					block = Blocks.iceWall;
				}
			}
		});
		distort(184f, 42f);
	}
}