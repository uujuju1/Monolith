package monolith.entities.comp;

import arc.math.*;
import arc.util.io.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import monolith.type.*;
import monolith.type.ModUnitType.*;

public class CopterComp extends UnitEntity {
	public float alpha;

	@Override
	public void update() {
		if (dead) {
			alpha = Mathf.approachDelta(alpha, 1f, type.fallSpeed + 0.003f);	
		} else {
			alpha = Mathf.approachDelta(alpha, 0f, type.fallSpeed + 0.003f);	
		}
		super.update();
	}

	@Override
	public void draw(){
		super.draw();
		((ModUnitType) type).rotors.each(rotor -> rotor.draw(this));
	}
}