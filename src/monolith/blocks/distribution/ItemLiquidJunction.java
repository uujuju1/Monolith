package monolith.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.world.*;

public class ItemLiquidJunction extends LiquidBlock {
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

	public class ItemLiquidJunctionBuild extends LiquidBuild {
		@Override
		public void updateTile() {
			items.each((item, amount) -> dump(item));
			dumpLiquid(liquids.current());
		}
	}
}