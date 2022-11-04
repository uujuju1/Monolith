package flow.content;

import arc.func.*;
import arc.util.*;
import arc.struct.*;
import flow.planets.*;

public class FlowGenerators {
	public class Room {
		int x, y, r;

		public Room(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}
	}
	public Seq<Room> rooms = new Seq<>();
	public static Cons<ModularPlanetGenerator> chroma = gen -> {
		gen.pass((x, y) -> {
			float 
			offsetX = (x/gen.width()-0.5f) / 1.3f,
			offsetY = (y/gen.height()-0.5f) / 1.3f,
			offsetZ = offsetX;

			gen.setBlock(gen.setFloor(gen.getBlock(Tmp.v31.set(
				gen.sector().tile.v.x + offsetX,
				gen.sector().tile.v.y + offsetY,
				gen.sector().tile.v.z + offsetZ
			))).asFloor().wall);
		});

		rooms.clear();
		Tmp.v1.trns(gen.rand().random(360f), gen.width()/2.6f);
		int 
		startX = (gen.width()/2) + Tmp.v1.x, startY = (gen.height()/2) + Tmp.v1.y,
		endX = (gen.width()/2) - Tmp.v1.x, endY = (gen.height()/2) - Tmp.v1.x,
		rooms.add(
			new Room(startX, startY, 20),
			new Room(endX, endY, 20)
		);

		for (int i = 0; i < 10; i++) {
			Tmp.v1.trns(gen.rand().random(360f), gen.width()/gen.rand().random(2.6f));
			rooms.add(new Room((gen.width()/2) - Tmp.v1.x, (gen.height()/2) - Tmp.v1.y, gen.rand().random(10, 20)));
			gen.erase(room.x, room.y, room.r);
		}

		gen.distort(165f, 60f);
		gen.distort(73f, 27f);
		gen.distort(64f, 21f);
		gen.median(5, 0.5f);
		gen.distort(61f, 6f);
		gen.distort(26f, 4f);
		gen.distort(18f, 3f);
	};
}
