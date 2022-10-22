package flow.type.draw;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;

public class Rotor {
	public String suffix;
	public TextureRegion region, topRegion, blurRegion;
	public float 
	x = 0, y = 0,
	speed = 15f,
	layerOffset = 0f;
	public int sides = 4;
	public boolean doubleRot = false;

	public Rotor(String suffix, float x, float y, float speed) {
		this.suffix = suffix;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void draw(Unit unit) {
		Draw.reset();

		float
		x = unit.x + Angles.trnsx(unit.rotation - 90, this.x, this.y),
		y = unit.y + Angles.trnsy(unit.rotation - 90, this.x, this.y);

		Draw.z((unit.type.lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) + layerOffset);
		drawRot(unit, speed);
		if (doubleRot) drawRot(unit, -speed);
		Draw.alpha(1);
		if (layerOffset >= 0f) {
			Draw.rect(topRegion, x, y, unit.rotation - 90);
		}
		Draw.reset();
	}

	public void drawRot(Unit unit, float speed) {
		float
		x = unit.x + Angles.trnsx(unit.rotation - 90, this.x, this.y),
		y = unit.y + Angles.trnsy(unit.rotation - 90, this.x, this.y);
		
		Draw.alpha(1 - unit.elevation);
		for (int i = 0; i < sides; i++) {
			Draw.rect(region, x, y, Time.time * speed + unit.id + (360/sides * i));
		}
		Draw.alpha(unit.elevation);
		Draw.rect(blurRegion, x, y, Time.time * (speed * 2) + unit.id);
	}

	public void load(UnitType type) {
		region = Core.atlas.find(type.name + suffix);
		topRegion = Core.atlas.find(type.name + suffix + "-top");
		blurRegion = Core.atlas.find(type.name + suffix + "-blur");
	}
}