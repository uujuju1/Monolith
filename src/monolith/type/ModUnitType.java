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
		super.draw();
		if (unit instanceof CopterComp) {
			rotors.each(rotor -> rotor.draw((CopterComp) unit));
		}
	}

	public static class PressureEngine extends UnitEngine {
		public static Rand rand;
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
				Tmp.v1.set(unit.x + x, unit.y + y).rotate(unit.rotation - 90);
				float
				fin = Time.time/length % 1,
				trnsx = Tmp.v1.x + Angles.trnsx(rotation, fin, rand.random(-width/2f, width/2f)),
				trnsy = Tmp.v1.y + Angles.trnsy(rotation, fin, rand.random(-width/2f, width/2f));
				Fill.circle(trnsx, trnsy, 1 - fin * width/8);
			}
		}
	}
}