package monolith.block.voidf;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.world.*;
import monolith.blocks.modules.*;

public class VoidfBlock extends Block {
	public boolean acceptVoidf = false, outputVoidf = false;
	public float minVoidf = -100, maxVoidf = 100;
	public Color voidfColor = Color.white;
	public TextureRegion voidfRegion;

	public VoidfBlock(String name) {
		super(name);
	}

	@Override
	public void load() {
		voidfRegion = Core.atlas.find(name + "-voidf");
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("voidf", build -> new Bar(Core.bundle.get("bar.voidf"), voidfColor, () -> (build.voidfModule().voidf + minVoidf)/(maxVoidf + minVoidf)));
	}

	public class VoidfBuild extends Building {
		public VoidfModule module = new VoidfModule();

		// get module
		public VoidfModule voidfModule() {
			return module;
		}

		// input voidf check
		public boolean acceptsVoidf(float voidf, Building src) {
			return acceptVoidf;
		}
		public boolean outputsVoidf(float voidf, Building src) {
			return outputVoidf;
		}

		// modify voidf value
		public void addVoidf(float voidf, Building src) {
			voidfModule().voidf += voidf;
		}
		public void subVoidf(float voidf, Building src) {
			voidfModule().voidf -= voidf;
		}
		public void setVoidf(float voidf, Building src) {
			voidfModule().voidf = voidf;
		}

		// what to do when voidf is out of bounds
		public void overflowVoidf() {
			if (voidfModule().voidf < minVoidf || voidfModule().voidf > maxVoidf) {
				kill();
			}
		}

		// drawing voidf stuff
		public void drawVoidf(Color color) {
			Draw.color(color);
			Draw.alpha((voidfModule().voidf + minVoidf)/(maxVoidf + minVoidf));
			Draw.rect(voidfRegion, x, y, block.rotate ? build.rotdeg() : 0);
		}

		@Override
		public void updateTile() {
			overflowVoidf();
		}

		@Override
		public void draw() {
			super.draw();
			drawVoidf(voidfColor);
		}
	}
}