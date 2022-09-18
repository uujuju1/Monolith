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

	public float noise2d(float x, float y, double octaves, double persistence, double scale, float mag) {
		return Simplex.noise2d(seed, octaves, persistence, scale, x, y) * mag;
	}

	@Override
	protected void generate() {
		pass((x, y) -> {
			float noise = noise2d(sector.tile.v.x + x, sector.tile.v.y + y, octaves, persistence, scale, mag);
			floor = getBlock(sector.tile.v);
			if (floor == Blocks.water) {
				if (noise < 0.5/1.5f) {
					floor = Blocks.deepwater;
				}
				if (noise > 0.5) {
					floor = Blocks.dirt;
				}
				if (noise > 0.5 + (0.5/4)) {
					block = Blocks.dirtWall;
				}
				if (noise > 0.5 + (0.5/2)) {
					block = Blocks.duneWall;
				}
				if (x < 10 || x > width - 10 || y < 10 || y > height - 10) {
					floor = Blocks.dirt;
				}
				if (x < 7 || x > width - 7 || y < 7 || y > height - 7) {
					block = Blocks.dirtWall;
				}
				if (noise2d(x + sector.tile.v.x, y + sector.tile.v.y, 3d, 0.5d, 0.01d, 1f) > 0.5f && floor == Blocks.dirt) {
					floor = Blocks.carbonStone;
					if (block == Blocks.dirtWall) block = Blocks.carbonWall;
					if (block == Blocks.duneWall) block = Blocks.ferricStoneWall;
				}
			}
		});
		distort(14f, 4f);

		Seq<Block> ores = Seq.with(Blocks.oreCopper, Blocks.oreLead);

		if(Simplex.noise3d(seed, 2, 0.5, 1, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z) > 0.25f){
			ores.add(Blocks.oreCoal);
		}

		if(Simplex.noise3d(seed, 2, 0.5, 1, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z) > 0.5f){
			ores.add(Blocks.oreTitanium);
		}

		if(Simplex.noise3d(seed, 2, 0.5, 1, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z) > 0.7f){
			ores.add(Blocks.oreThorium);
		}

		pass((x, y) -> {
			for (Block ore : ores) {
				if (noise2d(x + sector.tile.v.x, y + sector.tile.v.y, 3d, 0.5d, 0.1d, 1f) > 0.8f) {
					ore = ore;
				}
			}
		});
	}
}