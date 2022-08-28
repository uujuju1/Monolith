package monolith.planets;

import arc.math.*;
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
			floor = getBlock(sector.tile.v);
			if (floor == Blocks.water) {
				if (noise2d(sector.tile.v.x + x, sector.tile.v.y + y, octaves, persistence, scale, mag) > noiseTresh) {
					floor = Blocks.dirt;
				}
			}
			if (x < 10 || x > width - 10 || y < 10 || y > height - 10) {
				floor = Blocks.dirtWall;
			}
			if (x < 7 || x > width - 7 || y < 7 || y > height - 7) {
				floor = Blocks.dirtWall;
			}
		});
	}
}