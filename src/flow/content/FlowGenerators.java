package flow.content;

import arc.func.*;
import arc.util.*;
import flow.planets.*;

public class FlowGenerators {
	public static Cons<ModularPlanetGenerator> chroma = gen -> {
		gen.pass((x, y) -> {
			float 
			offsetX = x/gen.width() - 0.5f,
			offsetY = y/gen.height() - 0.5f,
			offsetZ = offsetX;

			gen.setBlock(gen.setFloor(gen.getBlock(Tmp.v31.set(
				gen.sector().tile.v.x + offsetX,
				gen.sector().tile.v.y + offsetY,
				gen.sector().tile.v.z + offsetZ
			))).asFloor().wall);
		});
	};
}