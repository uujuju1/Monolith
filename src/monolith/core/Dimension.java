package monolith.core;

import mindustry.core.*;
import mindustry.world.*;

public class Dimension extends World {
	public int width, height,
	id;

	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tiles(width, height);
	}

	public void setId(World from) {
		from.tiles.eachTile(tile -> {
			id += tile.floor().id;
			id -= tile.x + tile.y;
		});
	}
}