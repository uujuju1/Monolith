package monolith.entities.comp;

import arc.math.*;
import arc.util.io.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import monolith.type.*;
import monolith.type.ModUnitType.*;

public class CopterComp extends UnitEntity {
	public float alpha = 0f;

	@Override
	public void update() {
		if (dead) {
			alpha = Mathf.lerpDelta(alpha, 1f, type.fallSpeed + 0.003f);	
		} else {
			alpha = Mathf.lerpDelta(alpha, 0f, type.fallSpeed + 0.003f);	
		}
		super.update();
	}
}