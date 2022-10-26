package flow.world.blocks.defense.turrets;

import arc.math.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.world.blocks.defense.turrets.*;

// from esch from esch from esch from esch from esch from esch

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
			if (minRange > 0) Drawf.dashCircle(x, y, minRange, UAWPal.darkPyraBloom);
		}

		@Override
		protected void bullet(BulletType type, float angle) {
			float lifeScl = type.scaleVelocity ? Mathf.clamp(Mathf.dst(x, y, targetPos.x, targetPos.y) / type.range(), minRange / type.range(), range / type.range()) : 1f;
			type.create(this, team, x, y, angle, 1f + Mathf.range(velocityInaccuracy), lifeScl);
		}

		@Override
		protected void effects() {
			Effect shoot = shootEffect == Fx.none ? peekAmmo().shootEffect : shootEffect;
			Effect smoke = smokeEffect == Fx.none ? peekAmmo().smokeEffect : smokeEffect;

			shoot.at(x, y);
			smoke.at(x, y);
			shootSound.at(x, y, Mathf.random(0.9f, 1.1f));

			if (shootShake > 0) Effect.shake(shootShake, shootShake, this);
		}
	}
}