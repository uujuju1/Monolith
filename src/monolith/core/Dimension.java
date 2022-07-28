package monolith.core;

import mindustry.*;
import mindustry.game.*;
import mindustry.core.*;
import mindustry.world.*;

public class Dimension extends World {
	public int width, height,
	id;

	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tiles(width, height);
		this.tiles.fill();
		setId(Vars.world);
	}

	public void setId(World from) {
		from.tiles.eachTile(tile -> {
			id += tile.floor().id;
			id -= tile.x + tile.y;
		});
		id = Math.abs(id);
	}
	// set block but multiblock compatible
	public void setBlock(Tile tile, Block type, Team team, int rotation) {
		World prev = Vars.world;
		Vars.world = this;
		tile.setBlock(type, team, rotation);
		Vars.world = prev;
	}
}