package flow.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.liquid.*;

public class ItemLiquidJunction extends LiquidBlock {
	public ItemLiquidJunction(String name) {
		super(name);
		hasItems = acceptsItems = true;
		destructible = true;
		update = sync = true;
	}

	public class ItemLiquidJunctionBuild extends LiquidBuild {
		@Override
		public void updateTile() {
			items.each((item, amount) -> dump(item));
			dumpLiquid(liquids.current());
		}

		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return liquids.currentAmount() < block.liquidCapacity;
		}
		@Override
		public boolean acceptItem(Building source, Item item) {
			return acceptsItems && items.get(item) < getMaximumAccepted(item);
		}
	}
}