package monolith.world.blocks.voidf.distribution;

import arc.graphics.g2d.*;
import monolith.world.blocks.voidf.VoidfBlock;

public class VoidfRouter extends VoidfBlock {

	public VoidfRouter(String name) {
		super(name);
		acceptVoidf = outputVoidf = true;
	}

	public class VoidfSourceBuild extends VoidfBuild {
		@Override
		public void draw() {
			Draw.rect(bottomRegion, x, y, 0);
			super.draw();
			Draw.rect(region, x, y, 0);
		}
	}
}