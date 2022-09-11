package monolith.entities.comp;

import arc.math.*;
import mindustry.gen.*;
import monolith.type.*;
import monolith.type.ModUnitType.*;

public class CopterComp extends UnitEntity {
	// you want more? That's the neat part, you dont.
	public float[] alphas = new float[16];

	@Override
	public void update() {
		if (!(type instanceof ModUnitType)) return;
		if (((ModUnitType) type).rotors.size >= 16) return;
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
}