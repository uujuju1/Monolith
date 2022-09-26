package monolith.world.blocks.voidf.sandbox;

import arc.math.*;
import arc.graphics.g2d.*;
import monolith.world.blocks.voidf.VoidfBlock;

public class VoidfSource extends VoidfBlock {

	public VoidfSource(String name) {
		super(name);
		outputVoidf = true;
	}

	public class VoidfSourceBuild extends VoidfBuild {
		@Override
		public void updateTile() {
			for (int i = 0; i < proximity.size; i++) {
				if (proximity.get(i) instanceof VoidfBuild) {
					VoidfBuild next = (VoidfBuild) proximity.get(i);
					next.setVoidf(((VoidfBlock) next.block).maxVoidf, this);
				}
			}
		}
		@Override
		public void draw() {
			Draw.rect(region, x, y, 0);
		}
	}
}