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
}