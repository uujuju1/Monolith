package monolith.type;

import arc.math.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.entities.*;
import mindustry.world.blocks.environment.*;

public class SubmersibleUnitType extends UnitType {
	public Effect submersedEffect = new Effect(30f, e -> {
		Draw.color(e.color);
		Lines.stroke(e.foutpow());
		Lines.circle(e.x, e.y, 2f * e.finpow());
	});
	public float submersedEffectChance = 0.04f;

	public SubmersibleUnitType(String name) {
		super(name);
		canDrown = false;
	}

	public Floor getFloor(Unit unit) {
		return Vars.world.floorWorld(unit.x, unit.y);
	}

	@Override
	public boolean targetable(Unit unit, Team targeter) {
		if (getFloor(unit).isLiquid) return false;
		super.targetable(unit, targeter);
	}

	@Override
	public boolean hittable(Unit unit) {
		if (getFloor(unit).isLiquid) return false;
		super.hittable(unit);
	}

	@Override
	public void update(Unit unit) {
		super.update(unit);
		if(Mathf.chanceDelta(submersedEffectChance)){
			submersedEffect.at(unit.x + Mathf.range(unit.type.hitSize * 8f), unit.y + Mathf.range(unit.type.hitSize * 8f));
		}
	}
}