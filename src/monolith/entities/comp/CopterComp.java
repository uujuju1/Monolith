package monolith.entities.comp;

import arc.math.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import monolith.type.*;
import monolith.type.ModUnitType.*;

public class CopterComp extends UnitEntity {
	// you want more? That's the neat part, you dont.
	public float[] alphas = new float[16];

	@Override
	public void update() {
		// if (!(type instanceof ModUnitType)) return;
		// if (((ModUnitType) type).rotors.size >= 16) return;
		for (int i = 0; i < ((ModUnitType) type).rotors.size; i++) {
			Rotor r = ((ModUnitType) type).rotors.get(i);
			if (dead) {
				alphas[i] = Mathf.approachDelta(alphas[i], r.blurTime, 0.008f);
			} else {
				alphas[i] = Mathf.approachDelta(alphas[i], 0, 0.008f);
			}
		}
		super.update();
	}

	@Override
	public void draw(){
		super.draw();
		if (type instanceof ModUnitType) {
			((ModUnitType) type).rotors.each(rotor -> {
				int id = rotors.indexOf(rotor);
				if (rotor.back) {
					Draw.z(Layer.flyingUnitLow - 0.01f);
				} else {
					Draw.z(Layer.flyingUnitLow + 0.01f);
				}
				Draw.alpha(1 - (alphas[id]/rotor.blurTime));
				for (int i = 0; i < rotor.sides; i++) {
					Draw.rect(rotor.outlineRegion, x + Angles.trnsx(rotation - 90, rotor.x, rotor.y), y + Angles.trnsy(rotation - 90, rotor.x, rotor.y), Time.time + id * speed + (360/rotor.sides * i));
				}
				for (int i = 0; i < rotor.sides; i++) {
					Draw.rect(rotor.region, x + Angles.trnsx(rotation - 90, rotor.x, rotor.y), y + Angles.trnsy(rotation - 90, rotor.x, rotor.y), Time.time + id * speed + (360/rotor.sides * i));
				}			
	
				Draw.alpha(alphas[id]/rotor.blurTime);
				Draw.rect(rotor.blurRegion, x + Angles.trnsx(rotation - 90, rotor.x, rotor.y), y + Angles.trnsy(rotation - 90, rotor.x, rotor.y), Time.time + id);
			});
		}
	}
}