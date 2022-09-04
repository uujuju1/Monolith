package monolith.blocks.voidf;

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
		solid = destructible = true;
		update = sync = true;
	}

	@Override
	public void load() {
		super.load();
		voidfRegion = Core.atlas.find(name + "-voidf");
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("voidf", build -> new Bar(Core.bundle.get("bar.voidf"), voidfColor, () -> ((VoidfBuild) build).voidfF()));
	}

	public class VoidfBuild extends Building {
		public VoidfModule module = new VoidfModule();

		// get module
		public VoidfModule voidfModule() {
			return module;
		}

		// get percentage from 0 - 1
		public float voidfF() {
			return (voidfModule().voidf + Math.abs(minVoidf)) / (maxVoidf + Math.abs(minVoidf));
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
			Draw.alpha(voidfF());
			Draw.rect(voidfRegion, x, y, block.rotate ? rotdeg() : 0);
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