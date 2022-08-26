package monolith.planets;

import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.maps.generators.*;

public class ChromaPlanetGenerator extends PlanetGenerator {
	public double octaves = 1f, persistence = 1f, scale = 1f;
	public float minHeight = 0.3f;

	public Block[] arr = {Blocks.regolith, Blocks.regolith, Blocks.regolith, Blocks.regolith, Blocks.yellowStone, Blocks.rhyolite, Blocks.rhyolite, Blocks.carbonStone};

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
		if (rawTemp() < 0.5f) {
			return Color.yellow;
		}
		return Color.red.cpy().lerp(Color.blue, rawHeight(pos));
	}

	Block getBlock(Vec3 pos) {
		return Blocks.grass;
	}

	@Override
	protected void generate() {
		pass((x, y) -> {
			floor = getBlock(sector.tile.v);
		});
	}
}