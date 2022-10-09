package monolith.world.consumers;

import mindustry.gen.*;
import mindustry.world.consumers.*;
import monolith.world.PressureBlock.*;

public class ConsumePressure extends Consume {
	public float tresh;
	public boolean inverse;

	public ConsumePressure(float tresh, boolean inverse) {
		this.tresh = tresh;
		this.inverse = inverse;
	}

	@Override
	public float efficiency(Building build) {
		if (build instanceof PressureBuild) {
			PressureBuild b = (PressureBuild) build;
			float
			posValue = b.pressureAlpha() > tresh ? b.pressureAlpha() : 0f,
			negValue = b.pressureAlpha() < tresh ? b.pressureAlpha() : 0f;
			return inverse ? negValue : posValue;
		}
		return 0f;
	}
}