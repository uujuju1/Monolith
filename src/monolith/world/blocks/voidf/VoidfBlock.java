package monolith.world.blocks.voidf;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.world.*; 
import mindustry.world.meta.*;
import monolith.graphics.*;
import monolith.world.blocks.meta.*;
import monolith.world.blocks.modules.*;

public class VoidfBlock extends Block {
	public boolean acceptVoidf = false, outputVoidf = false;
	public float 
	minVoidf = 0, maxVoidf = 100,
	transferRate = 0.1f,
	consumeVoidf = 0f;
	public TextureRegion voidfRegion, bottomRegion;

	public VoidfBlock(String name) {
		super(name);
		solid = destructible = true;
		update = sync = true;
	}

	@Override
	public void load() {
		super.load();
		bottomRegion = Core.atlas.find(name + "-bottom", region);
		voidfRegion = Core.atlas.find(name + "-voidf");
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{bottomRegion, region};
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("voidf", build -> new Bar(Core.bundle.get("bar.voidf"), MonolithPal.voidf, () -> ((VoidfBuild) build).voidfF()));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.abilities, MonolithStatValues.voidfUnit(this));
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

		// get transfer amount 
		public float voidfTransfer() {
			return voidfModule().voidf * transferRate * Time.delta;
		}

		// input voidf check
		public boolean acceptsVoidf(float voidf, Building src) {
			return voidf + voidfModule().voidf < maxVoidf && acceptVoidf;
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
			Draw.color(color.cpy().mul(1.2f));
			Draw.alpha(voidfF());
			MonolithDrawf.voidfSmoke(id, x, y, block.size * 10, block.size/2f, 60f, block.size * 4f);
			Draw.reset();
		}

		@Override
		public void updateTile() {
			overflowVoidf();
			float t = voidfTransfer();
			for (int i = 0; i < proximity.size; i++) {
				if (proximity.get(i) instanceof VoidfBuild) {
					VoidfBuild next = (VoidfBuild) proximity.get(i);
					if (next.acceptsVoidf(t, this) && outputsVoidf(t, this)) {
						next.addVoidf(t, this);
						subVoidf(t, this);
					}
				}
			}
		}

		@Override
		public void draw() {
			drawVoidf(MonolithPal.voidf);
		}
	}
}