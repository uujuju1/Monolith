package monolith.world.blocks.voidf.sandbox;

import arc.graphics.g2d.*;
import monolith.world.blocks.voidf.VoidfBlock;

public class VoidfVoid extends VoidfBlock {
	public VoidfVoid(String name) {
		super(name);
		acceptVoidf = true;
	}

	public class VoidfVoidBuild extends VoidfBuild {
		@Override
		public void updateTile() {
			setVoidf(((VoidfBlock) block).minVoidf, this);
		}
		@Override
		public void draw() {
			Draw.rect(region, x, y, 0);
		}
	}
}