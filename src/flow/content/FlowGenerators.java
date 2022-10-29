package flow.content;

import arc.func.*;
import arc.util.*;
import flow.planets.*;

public class FlowGenerators {
	public static Cons<ModularPlanetGenerator> chroma = gen -> {
		p.pass((x, y) -> {
			float 
			offsetX = x/p.width() - 0.5f,
			offsetY = y/p.height() - 0.5f,
			offsetZ = offsetX;

			p.setBlock(p.setFloor(p.getBlock(Tmp.v31.set(
				p.sector().tile.v.x + offsetX,
				p.sector().tile.v.y + offsetY,
				p.sector().tile.v.z + offsetZ
			))).asFloor().wall);
		});
	};
}