package monolith.planets;

import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.maps.generators.*;

public class ChromaPlanetGenerator extends PlanetGenerator {

	float rawHeight(Vec3 pos) {
		return Simplex.noise3d(seed, 1, 1f, 0.9f, pos.x, pos.y, pos.z);
	}
	@Override
	public float getHeight(Vec3 pos) {
		return Math.max(0.1f, rawHeight(pos));
	}

	@Override
	public Color getColor(Vec3 pos) {
		return Color.red;
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