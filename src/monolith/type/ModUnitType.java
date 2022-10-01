package monolith.type;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.entities.*;
import mindustry.world.blocks.environment.*;
import monolith.type.draw.*;

public class ModUnitType extends UnitType {
	public Seq<Rotor> rotors = new Seq<>();
	public Effect submersedEffect = new Effect(30f, e -> {
		Draw.color(e.color);
		Lines.stroke(e.foutpow());
		Lines.circle(e.x, e.y, 5f * e.finpow());
	});
	public float submersedEffectChance = 0.04f;

	public ModUnitType(String name) {
		super(name);
	}

	@Override
	public void load() {
		super.load();
		rotors.each(r -> r.load(this));
	}

	@Override
	public void update(Unit unit) {
		super.update(unit);
		if(Mathf.chanceDelta(submersedEffectChance) && getFloor(unit).isDeep()){
			submersedEffect.at(unit.x + Mathf.range(unit.type.hitSize), unit.y + Mathf.range(unit.type.hitSize), unit.rotation, getFloor(unit).mapColor);
		}
	}

	@Override
	public void draw(Unit unit){
		super.draw(unit);
		rotors.each(rotor -> rotor.draw(unit));
	}

	@Override
	public boolean targetable(Unit unit, Team targeter) {
		if (getFloor(unit).isDeep()) return false;
		return super.targetable(unit, targeter);
	}

	@Override
	public boolean hittable(Unit unit) {
		if (getFloor(unit).isDeep()) return false;
		return super.hittable(unit);
	}

	public Floor getFloor(Unit unit) {
		return Vars.world.floorWorld(unit.x, unit.y);
	}

	public static class PressureEngine extends UnitEngine {
		public static Rand rand = new Rand();
		public Color color;
		public float length;
		public int amount;

		public PressureEngine(float x, float y, int amount, float width, float length, float rotation, Color color) {
			super(x, y, width, rotation);
			this.amount = amount;
			this.length = length;
			this.color = color;
		}

		@Override
		public void draw(Unit unit) {
			rand.setSeed(unit.id);
			for (int i = 0; i < amount; i++) {
				float
				fin = rand.random(0.7f, 1) * (Time.time/length) % 1,
				trnsx = unit.x + Angles.trnsx(unit.rotation - 90, x, y) + Angles.trnsx(rotation + unit.rotation, fin * length, rand.random(-radius/2f, radius/2f)),
				trnsy = unit.y + Angles.trnsy(unit.rotation - 90, x, y) + Angles.trnsy(rotation + unit.rotation, fin * length, rand.random(-radius/2f, radius/2f));
				Draw.color(color);
				Fill.circle(trnsx, trnsy, 1 - fin * radius/8);
			}
		}
	}
}