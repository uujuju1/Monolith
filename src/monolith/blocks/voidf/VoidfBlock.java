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

	public class VoidfBuild extends Building {
		public VoidfModule module = new VoidfModule();

		public VoidfModule voidfModule() {
			return module;
		}

		public boolean acceptsVoidf(float voidf, Building src) {
			return acceptVoidf;
		}
		public boolean outputsVoidf(float voidf, Building src) {
			return outputVoidf;
		}

		public void addVoidf(float voidf, Building src) {
			module.voidf += voidf;
		}
		public void subVoidf(float voidf, Building src) {
			module.voidf -= voidf;
		}
		public void setVoidf(float voidf, Building src) {
			module.voidf = voidf;
		}
	}
}