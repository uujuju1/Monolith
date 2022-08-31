package monolith.blocks.distribution;

import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import monolith.blocks.modules.*;

public class Tunnel extends Block {
	public int maxTravelLength = 10;
	public float travelTime = 10;

	public Tunnel(String name) {
		super(name);
		update = sync = rotate = true;
		solid = destructible = true;
		hasItems = true;

	} 

	public class TunnelBuild extends Building {
		public TunnelModule module = new TunnelModule(this);

		@Override
		public boolean acceptItem(Building source, Item item) {
			if (module.start == this) {
				return true;
			}
			if (source == module.start) {
				return true;
			}
			return false;
		}

		@Override
		public void updateTile() {
			super.updateTile();
			if (module.start == this) {
				module.update();
			}
		}

		@Override
		public void draw() {
			super.draw();
			if (module.start == this) {
				module.draw();
			}
		}
	}
}