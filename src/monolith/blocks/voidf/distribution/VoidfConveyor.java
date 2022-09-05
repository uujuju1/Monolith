package monolith.blocks.voidf.distribution;

import arc.Core;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import monolith.blocks.voidf.*;

public class VoidfConveyor extends VoidfBlock {
	public TextureRegion bottomRegion, dirRegion;

	public VoidfConveyor(String name) {
		super(name);
	}

	@Override
	public void load() {
		super.load();
		bottomRegion = Core.atlas.find(name + "-bottom");
		dirRegion = Core.atlas.find(name + "-dir");
	}

	public class VoidfConveyorBuild extends VoidfBuild {
		
		@Override
		public boolean acceptsVoidf(float voidf, Building src) {
			if (back() == src) {
				super.acceptsVoidf(voidf, src);
			}
			return false;
		}

		@Override
		public void updateTile() {
			overflowVoidf();
			float t = voidfTransfer();
			if (front() instanceof VoidfBuild) {
				if (((VoidfBuild) front()).acceptsVoidf(t, this) && outputsVoidf(t, this)) {
					((VoidfBuild) front()).addVoidf(t, this);
					subVoidf(t, this);
				}
			}
		}

		@Override
		public void draw() {
			Draw.rect(bottomRegion, x, y, 0);
			super.draw();
			float rot = (rotdeg() + 90f) % 180f - 90f;
			Draw.rect(region, x, y, rot);
			Draw.rect(dirRegion, x, y, rot);
		}
	}
}