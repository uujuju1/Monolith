package monolith.planets;

import arc.math.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.maps.generators.*;

public class ChromaPlanetGenerator extends PlanetGenerator {
	public double octaves = 1f, persistence = 1f, scale = 1f;
	public float minHeight = 0.4f, tempTresh = 0.7f;

	public Block[] arr = {Blocks.dirt, Blocks.basalt};

	float rawHeight(Vec3 pos) {
		return Simplex.noise3d(seed, 7, 0.5f, 2.3f, pos.x, pos.y, pos.z);
	}
	float rawTemp(Vec3 pos) {
		float poles = Math.abs(pos.y);
		return Simplex.noise3d(seed + 2, octaves, persistence, scale, pos.x, pos.y, pos.z) * poles;
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
			if (rawTemp(pos) < tempTresh) {
				return Blocks.water;
			} else {
				return Blocks.ice;
			}
		}
		if (rawTemp(pos) < tempTresh - 0.1f) {
			if (rawTemp(pos) < tempTresh) {
				return Blocks.snow;
			}
			return Blocks.iceSnow;
		}
		return arr[Mathf.clamp((int)(rawHeight(pos) * arr.length), 0, arr.length - 1)];
	}

	@Override
	protected void generate() {
		pass((x, y) -> {
			floor = getBlock(sector.tile.v);
		});
	}
}