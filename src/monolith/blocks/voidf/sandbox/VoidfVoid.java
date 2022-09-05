package monolith.blocks.voidf.sandbox;

import monolith.blocks.voidf.VoidfBlock;

public class VoidfVoid extends VoidfBlock {
	public VoidfSource(String name) {
		super(name);
		acceptVoidf = true;
	}

	public class VoidfVoidBuild extends VoidfBuild {
		@Override
		public void updateTile() {
			super.updateTile();
			setVoidf(((VoidfBlock) block).minVoidf, this);
		}
	}
}