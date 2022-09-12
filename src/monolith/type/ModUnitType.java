package monolith.type;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;
import monolith.type.draw.*;
import monolith.entities.comp.*;

public class ModUnitType extends UnitType {
	public Seq<Rotor> rotors = new Seq<>();

	public ModUnitType(String name) {
		super(name);
		outlineColor = Pal.darkOutline;
	}

	@Override
	public void load() {
		super.load();
		rotors.each(r -> r.load(this));
	}

	@Override
	public void draw(Unit unit){
		super.draw(unit);
		if (unit instanceof CopterComp) {
			rotors.each(rotor -> rotor.draw((CopterComp) unit));
		}
	}

	public static class PressureEngine extends UnitEngine {
		public static Rand rand = new Rand();
		public float length;
		public int amount;

		public PressureEngine(float x, float y, int amount, float width, float length, float rotation) {
			super(x, y, width, rotation);
			this.amount = amount;
			this.length = length;
		}

		@Override
		public void draw(Unit unit) {
			rand.setSeed(unit.id);
			for (int i = 0; i < amount; i++) {
				float
				dx = unit.x + Angles.trnsx(unit.rotation - 90, x, y),
				dy = unit.y + Angles.trnsy(unit.rotation - 90, x, y),
				fin = Time.time/length % 1,
				trnsx = dx + Angles.trnsx(rotation, fin, rand.random(-radius/2f, radius/2f)),
				trnsy = dx + Angles.trnsy(rotation, fin, rand.random(-radius/2f, radius/2f));
				Fill.circle(trnsx, trnsy, 1 - fin * radius/8);
			}
		}
	}
}