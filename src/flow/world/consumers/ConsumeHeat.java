package flow.world.consumers;

import mindustry.gen.*;
import mindustry.world.consumers.*;
import flow.world.blocks.HeatBlock.*;

public class ConsumeHeat extends Consume {
	public float tresh;
	public boolean inverse;

	public ConsumeHeat(float tresh, boolean inverse) {
		this.tresh = tresh;
		this.inverse = inverse;
	}

	@Override
	public float efficiency(Building build) {
		if (build instanceof HeatBuild) {
			HeatBuild b = (HeatBuild) build;
			float
			posValue = b.getModule().heat > tresh ? b.heatAlpha().get() : 0f,
			negValue = b.getModule().heat < tresh ? 1f - b.heatAlpha().get() : 0f;
			return inverse ? negValue : posValue;
		}
		return 0f;
	}
}