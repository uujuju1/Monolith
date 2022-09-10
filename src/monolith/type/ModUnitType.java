package monolith.type;

import arc.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;

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
		// if (!(unit instanceof CopterComp)) {
			super.draw(unit);
			drawRotors(unit);
		// }
	}

	public void drawRotors(Unit unit) {
		rotors.each(rotor -> {
			Draw.z(back ? Layer.flyingUnitLow + 0.01 : Layer.flyingUnitLow - 0.01);
			Draw.alpha(1);
			Draw.rect(region, unit.x + Angles.trnsx(unit.rotation - 90, x, y), unit.y + Angles.trnsx(unit.rotation - 90, x, y), Time.time + unit.id);
			Draw.alpha(0);
			Draw.rect(blurRegion, unit.x + Angles.trnsx(unit.rotation - 90, x, y), unit.y + Angles.trnsy(unit.rotation - 90, x, y), Time.time + unit.id);
		});
	}

	public class Rotor {
		public String name;
		public TextureRegion region, blurRegion;
		public float 
		x = 0, y = 0,
		speed = 15f,
		blurTime = 10f;
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
		}
	}
}