package monolith.type;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;
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
		rotors.each(r -> r.load());
	}

	@Override
	public void draw(Unit unit) {
		if (!(unit instanceof CopterComp)) {
			super.draw(unit);
			drawRotors((CopterComp) unit);
		}
		super.draw(unit);
	}

	public void drawRotors(CopterComp unit) {
		rotors.each(rotor -> {
			int id;
			if (rotor.back) {
				Draw.z(Layer.flyingUnitLow - 0.01f);
			} else {
				Draw.z(Layer.flyingUnitLow + 0.01f);
			}
			Draw.alpha(1 - (unit.alphas[id]/rotor.blurTime));
			for (int i = 0; i < rotor.sides; i++) {
				Draw.rect(rotor.outlineRegion, unit.x + Angles.trnsx(unit.rotation - 90, rotor.x, rotor.y), unit.y + Angles.trnsy(unit.rotation - 90, rotor.x, rotor.y), Time.time + unit.id * speed + (360/rotor.sides * i));
			}
			for (int i = 0; i < rotor.sides; i++) {
				Draw.rect(rotor.region, unit.x + Angles.trnsx(unit.rotation - 90, rotor.x, rotor.y), unit.y + Angles.trnsy(unit.rotation - 90, rotor.x, rotor.y), Time.time + unit.id * speed + (360/rotor.sides * i));
			}			

			Draw.alpha(unit.alphas[id]/rotor.blurTime);
			Draw.rect(rotor.blurRegion, unit.x + Angles.trnsx(unit.rotation - 90, rotor.x, rotor.y), unit.y + Angles.trnsy(unit.rotation - 90, rotor.x, rotor.y), Time.time + unit.id);
			id++;
		});
	}

	public class Rotor {
		public String name;
		public TextureRegion region, blurRegion, outlineRegion;
		public float 
		x = 0, y = 0,
		speed = 15f,
		blurTime = 10f;
		public int sides = 4;
		public boolean back = false;

		public Rotor(String name, float x, float y, float speed, boolean back) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.speed = speed;
			this.back = back;
		}

		public void load() {
			region = Core.atlas.find(name);
			blurRegion = Core.atlas.find(name + "-blur");
			outlineRegion = Core.atlas.find(name + "-outline");
		}
	}
}