package monolith.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import monolith.world.blocks.*;
import monolith.world.blocks.PressureBlock.*;

public class PressurePipe extends PressureBlock {
	public TextureRegion[] regions;
	public TextureRegion[] pressureRegions;

	public PressurePipe(String name) {
		super(name);
		solid = true;
	}

	@Override
	public void load() {
		super.load();
		regions = new TextureRegion[16];
		pressureRegions = new TextureRegion[16];
		for (int i = 0; i < 16; i++) {
			regions[i] = Core.atlas.find(name + "-" + i);
			pressureRegions[i] = Core.atlas.find(name + "-" + i + "-pressure");
		}
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{regions[0]};
	}

	public class PressurePipeBuild extends PressureBuild {
		public int findRegion() {
			int index = 0;
			for (int i = 0; i < 4; i++) if (nearby(i) instanceof PressureBuild) if (((PressureBuild) nearby(i)).acceptPressure(this, 0f)) index += 1 << i;
			return index;
		}

		@Override
		public void draw() {
			Draw.rect(regions[findRegion()], x, y, 0);
			Draw.alpha(pressureAlpha());
			Draw.rect(pressureRegions[findRegion()], x, y, 0);
		}
	}
}