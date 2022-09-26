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
	float rawHeight(Vec3 pos) {
		return Simplex.noise3d(seed, 7, 0.5f, 2.3f, pos.x, pos.y, pos.z);
	}

	@Override
	public float getHeight(Vec3 pos) {
		return 1f;
	}

	@Override
	public Color getColor(Vec3 pos) {
		return Color.black;
	}

	@Override
	protected void generate() {
		pass((x, y) -> floor = Blocks.space);
	}
}