package flow.content;

import arc.func.*;
import arc.util.*;
import arc.struct.*;
import flow.planets.*;

public class FlowGenerators {
	public class Room {
		public int x, y, r;
		public Room other;

		public Room(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}

		public Seq<Tile> path(TileHueristic th, Boolf<Tile> passable) {
			return Astar.pathfind(
				Vars.world.tiles.getn(x, y),
				Vars.world.tiles.getn(other.x, other.y),
				th,
				passable
			);
		}
	}

	public Cons<ModularPlanetGenerator> chroma = gen -> {
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

		Tmp.v1.trns(gen.rand().random(360f), gen.width()/2.6f);
		int 
		startX = (int) ((gen.width()/2f) + Tmp.v1.x), startY = (int) ((gen.height()/2f) + Tmp.v1.y),
		endX = (int) ((gen.width()/2f) - Tmp.v1.x), endY = (int) ((gen.height()/2f) - Tmp.v1.y);

		Seq<Room> rooms = Seq.with(
			new Room(startX, startY, 20),
			new Room(endX, endY, 20)
		);

		for (int i = 0; i < 10; i++) {
			Tmp.v1.trns(gen.rand().random(360f), gen.width()/gen.rand().random(2.6f));
			rooms.add(new Room((int) ((gen.width()/2f) - Tmp.v1.x), (int) ((gen.height()/2f) - Tmp.v1.y), 20));
		}
		for (Room room : rooms) {
			gen.erase(room.x, room.y, room.r);
			while (room.other = room && room.other == null) room.other == rooms.random(rand);
			gen.brush(room.path(tile -> Mathf.dst(gen.width()/2f, gen.height()/2f), tile -> true), 20);
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
