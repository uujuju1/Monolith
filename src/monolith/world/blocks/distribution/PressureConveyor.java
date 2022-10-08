package monolith.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import monolith.world.blocks.*;
import monolith.world.blocks.PressureBlock.*;

public class PressureConveyor extends PressureBlock {
	public TextureRegion[] regions;
	public TextureRegion[] pressureRegions;

	public PressureConveyor(String name) {
		super(name);
		solid = true;
	}

	@Override
	public void load() {
		super.load();
		for (int i = 0; i < size * 4; i++) {
			regions[i] = Core.atlas.find(name + "-" + i);
			pressureRegions[i] = Core.atlas.find(name + "-" + i + "-pressure");
		}
	}

	public class PressureConveyorBuild extends PressureBuild {
		public int findRegion() {
			int index = 0;
			for (int i = 0; i < 4; i++) if (nearby(i) instanceof PressureBuild) if (((PressureBuild) nearby(i)).acceptPressure(this, 0f)) index += 1 << i;
		}

		@Override
		public void draw() {
			Draw.rect(regions[findRegion()], x, y, 0);
			Draw.alpha(pressureMap());
			Draw.rect(pressureRegions[findRegion()], x, y, 0);
		}
	}
}