package monolith.block.voidf;

import arc.graphics.*;
import mindustry.gen.*;
import mindustry.world.*;
import monolith.blocks.modules.*;

public class VoidfBlock extends Block {
	public boolean acceptVoidf = false, outputVoidf = false;
	public float minVoidf = -100, maxVoidf = 100;
	public Color VoidfColor = Color.white;

	public VoidfBlock(String name) {
		super(name);
	}

	public class VoidfBuild extends Building implements VoidfBuilding {
		public VoidfModule module = new VoidfModule();

		@Override
		public VoidfModule voidfModule() {return module;}

		@Override
		public boolean acceptsVoidf(float heat, Building src) {return acceptVoidf;}
		@Override
		public boolean outputsVoidf(float heat, Building src) {return outputVoidf;}
	}
}