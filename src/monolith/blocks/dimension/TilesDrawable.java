package monolith.blocks.dimension;

import arc.math.*;
import arc.graphics.g2d.*;
import arc.scene.style.*;
import mindustry.world.*;
import mindustry.content.*;

public class TilesDrawable extends BaseDrawable implements TransformDrawable {
	public Tiles tiles;

	public TilesDrawable(Tiles tiles) {
		this.tiles = tiles;
	}

	@Override
	public void draw(float initx, float inity, float width, float height){
		tiles.eachTile(tile -> {
			boolean drawbuild = true;
			float 
			drawx = initx - (tiles.width/2f),
			drawy = inity - (tiles.height/2f);

			Mathf.rand.setSeed(tile.pos());
			if (tile.floor() != null && tile.floor() != Blocks.air) {
				Draw.rect(tile.floor().variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, tile.floor().variantRegions.length - 1))], drawx + tile.worldx(), drawy + tile.worldy());
			}
			if (tile.overlay() != null && tile.overlay() != Blocks.air) {
				Draw.rect(tile.overlay().variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, tile.overlay().variantRegions.length - 1))], drawx + tile.worldx(), drawy + tile.worldy());
			}
			if (tile.block() != null && tile.block() != Blocks.air) {
				Draw.rect(tile.block().uiIcon, drawx + tile.worldx(), drawy + tile.worldy());
			}
		});
	}
}