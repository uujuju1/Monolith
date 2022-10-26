package flow.world.blocks.defense.turrets;

import arc.math.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.world.blocks.defense.turrets.*;

public class Mortar extends ItemTurret {
	public Mortar(String name) {super(name);}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x * Vars.tilesize + offset, y * Vars.tilesize + offset, rotation, valid);
		if (minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.placing.cpy().mul(0.8f));
	}

	public class MortarBuild extends ItemTurretBuild {
		@Override public void draw() {Draw.rect(region, x, y, 0);}

		@Override
		public void drawSelect() {
			super.drawSelect();
			if (minRange > 0) Drawf.dashCircle(x, y, minRange, Pal.placing.cpy().mul(0.8f));
		}

		@Override
		protected void bullet(BulletType type, float xOffset, float yOffset, float angleOffset, Mover mover) {
			Mortar self = (Mortar) block;
			float 
			lifeScl = type.scaleLife ? Mathf.clamp(Mathf.dst(x, y, targetPos.x, targetPos.y) / type.range, self.minRange / type.range, range() / type.range) : 1f,
			shootAngle = rotation + angleOffset + Mathf.range(self.inaccuracy);

			type.create(this, team, x, y, shootAngle, -1f, (1f - self.velocityRnd) + Mathf.random(self.velocityRnd), lifeScl, null, mover, targetPos.x, targetPos.y);

			(self.shootEffect == null ? type.shootEffect : self.shootEffect).at(x, y, rotation + angleOffset, type.hitColor);
			(self.smokeEffect == null ? type.smokeEffect : self.smokeEffect).at(x, y, rotation + angleOffset, type.hitColor);
			shootSound.at(x, y, Mathf.random(self.soundPitchMin, self.soundPitchMax));
			if(self.shake > 0) Effect.shake(self.shake, self.shake, this);

			curRecoil = 1f;
			heat = 1f;

			if(!self.consumeAmmoOnce) useAmmo();
		}
	}
}