package monolith.blocks.voidf.sandbox;

import arc.math.*;
import monolith.blocks.voidf.VoidfBlock;

public class VoidfSource extends VoidfBlock {

	public VoidfSource(String name) {
		super(name);
		outputVoidf = true;
	}

	public class VoidfSourceBuild extends VoidfBuild {
		@Override
		public void updateTile() {
			super.updateTile();
			for (int i = 0; i < proximity.size; i++) {
				if (proximity.get(i) instanceof VoidfBuild) {
					VoidfBuild next = (VoidfBuild) proximity.get(i);
					if (next.acceptsVoidf(0, this)) {
						next.setVoidf(((VoidfBlock) next.block).maxVoidf, this);
					}
				}
			}
		}
	}
}