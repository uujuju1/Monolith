package monolith.type.draw;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;
import monolith.entities.comp.*;

public class Rotor {
	public String suffix;
	public TextureRegion region, topRegion, blurRegion, outlineRegion;
	public float 
	x = 0, y = 0,
	speed = 15f,
	blurTime = 10f;
	public int sides = 4;
	public boolean back = false;

	public Rotor(String suffix, float x, float y, float speed, boolean back) {
		this.suffix = suffix;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.back = back;
	}

	public void draw(Unit unit) {
		CopterComp type = ((CopterComp) unit);
		Draw.reset();

		if (back) {
			Draw.z(Layer.flyingUnitLow - 0.01f);
		} else {
			Draw.z(Layer.flyingUnitLow + 0.01f);
		}

		float
		x = Angles.trnsx(unit.rotation - 90, this.x, this.y),
		y = Angles.trnsy(unit.rotation - 90, this.x, this.y);

		// Draw.alpha(1);
		// for (int i = 0; i < sides; i++) {
		// 	Draw.rect(
		// 		outlineRegion,
		// 		x, y,
		// 		Time.time * speed + unit.id + (360/sides * i)
		// 	);
		// }
		for (int i = 0; i < sides; i++) {
			Draw.rect(
				region,
				x, y,
				Time.time * speed + unit.id + (360/sides * i)
			);
		}

		// if (!back) {
		// 	Draw.rect(topRegion, x, y, unit.rotation - 90);
		// }
		Draw.reset();
	}

	public void load(UnitType type) {
		region = Core.atlas.find(type.name + suffix);
		topRegion = Core.atlas.find(type.name + suffix + "-top");
		blurRegion = Core.atlas.find(type.name + suffix + "-blur");
		outlineRegion = Core.atlas.find(type.name + suffix + "-outline");
	}
}