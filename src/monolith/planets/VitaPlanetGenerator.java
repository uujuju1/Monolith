package monolith.planets;

import arc.util.*;
import arc.graphics.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.maps.generators.*;

public class VitaPlanetGenerator extends PlanetGenerator {
	public double octaves = 12, persistence = 0.75f, scale = 0.85f;
	public float minHeight = 0.3f;

	public Block[] arr = {Blocks.stone, Blocks.sand, Blocks.grass};

	public VitaPlanetGenerator() {
		seed = 5;
	}

	@Override
	public Color getColor(Vec3 pos) {
		return Color.white;
	}

	float rawHeight(Vec3 pos) {
		return Simplex.noise3d(seed, octaves, persistence, scale, pos.x, pos.y, pos.z);
	}
	@Override
	public float getHeight(Vec3 pos) {
		return Math.min(minHeight, rawHeight(pos));
	}

	Block getBlock(float x, float y, float z) {
		float 
		poles = Math.abs(y),
		height = rawHeight(Tmp.v31.set(x, y, z));
	}

	@Override
	public void generate() {
		pass((x, y) -> {
			floor = Blocks.stone;
		});
		Schematics.placeLauchLoadout(50, 50);
	}
}