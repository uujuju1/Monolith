package monolith.blocks.distribution;

import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.world.*;

public class ItemLiquidJunction extends Block {
	public TextureRegion liquidRegion;

	public ItemLiquidJunction(String name) {
		super(name);
		hasItems = hasLiquids = true;
		destructible = true;
		update = sync = true;
	}

	@Override
	public void load() {
		super.load();
		liquidRegion = Core.atlas.find(name + "-liquid");
	}

	public class ItemLiquidJunctionBuild extends Building {
		@Override
		public void updateTile() {
			items.each(item -> dump(item));
			liquids.each(liquid -> dumpLiquid(liquid));
		}

		@Override
		public void draw() {
			super.draw();
			Draw.rect(liquidRegion, x, y, 0);
		}
	}
}