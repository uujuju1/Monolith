package flow.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.graphics.*;
import flow.world.blocks.*;
import flow.world.blocks.HeatBlock.*;

public class HeatPipe extends HeatBlock {
	public TextureRegion[] regions;
	public TextureRegion[] heatRegions;

	public HeatPipe(String name) {
		super(name);
		solid = true;
	}

	@Override
	public void load() {
		super.load();
		regions = new TextureRegion[16];
		heatRegions = new TextureRegion[16];
		for (int i = 0; i < 16; i++) {
			regions[i] = Core.atlas.find(name + "-" + i);
			heatRegions[i] = Core.atlas.find(name + "-" + i + "-heat", Core.atlas.find("monolith-pipe-" + i + "-heat"));
		}
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{regions[0]};
	}

	public class HeatPipeBuild extends HeatBuild {
		public int findRegion() {
			int index = 0;
			for (int i = 0; i < 4; i++) if (nearby(i) instanceof HeatBuild) if (((HeatBuild) nearby(i)).acceptHeat(this, 0f)) index += 1 << i;
			return index;
		}

		@Override
		public void draw() {
			Draw.rect(regions[findRegion()], x, y, 0);
			Draw.color(getModule().heat > 0 ? Pal.accent : Pal.lancerLaser);
			Draw.alpha(heatAlpha());
			Draw.rect(heatRegions[findRegion()], x, y, 0);
		}
	}
}