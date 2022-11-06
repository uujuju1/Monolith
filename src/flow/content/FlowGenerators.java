package flow.content;

import arc.func.*;
import arc.util.*;
import arc.math.*;
import arc.struct.*;
import mindustry.*;
import mindustry.ai.*;
import mindustry.game.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.ai.Astar.*;
import flow.planets.*;
import flow.content.blocks.*;

public class FlowGenerators {
	public class Room {
		public int x, y;
		public Room other;

		public Room(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Seq<Tile> path(TileHueristic th, Boolf<Tile> passable) {return Astar.pathfind(x, y, other.x, other.y, th, passable);}

		public String toString() {return "x = " + x + ", y = " + y;}
	}

	public ObjectMap<Block, Block> craters = ObjectMap.of(
		FlowEnvironment.lodeStone, FlowEnvironment.lodeCrater,
		FlowEnvironment.oxaicStone, FlowEnvironment.oxaicCrater,
		FlowEnvironment.cupricStone, FlowEnvironment.cupricCrater
	);

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
			new Room(startX, startY),
			new Room(endX, endY)
		);

		for (int i = 0; i < 10; i++) {
			Tmp.v1.trns(gen.rand().random(360f), gen.width()/2.6f * gen.rand().random(1f));
			rooms.add(new Room((int) ((gen.width()/2f) - Tmp.v1.x), (int) ((gen.height()/2f) - Tmp.v1.y)));
		}
		for (Room room : rooms) {
			gen.erase(room.x, room.y, 20);
			while (room.other == room || room.other == null) room.other = rooms.random(gen.rand());
			gen.brush(room.path(tile -> 999999999, tile -> true), 20);
		}

		gen.pass((x, y) -> {if (gen.noise(x + 999, y, 3, 0.5, 40) > 0.7f) gen.setFloor(craters.get(gen.floor()));});

		gen.distort(165f, 60f);
		gen.distort(73f, 27f);
		gen.distort(64f, 21f);
		gen.median(5, 0.5f);
		gen.distort(61f, 8f);
		gen.distort(26f, 6f);
		gen.distort(18f, 5f);
		gen.distort(12f, 4f);

		Schematics.placeLaunchLoadout(rooms.get(0).x, rooms.get(0).y);
		gen.tiles().getn(rooms.get(1).x, rooms.get(1).y).setOverlay(Blocks.spawn);
	};
}
