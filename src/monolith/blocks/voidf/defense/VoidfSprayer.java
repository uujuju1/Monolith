package monolith.blocks.voidf.defense;

import arc.util.*;
import arc.func.*;
import mindustry.*;
import mindustry.graphics.*;
import monolith.blocks.voidf.*;

public class VoidfSprayer extends VoidfBlock {
	public float 
	range = 80f,
	consumeVoidf = 1f;
	public Cons<VoidfBuild> action = build -> {};

	public VoidfSprayer(String name) {
		super(name);
		acceptVoidf = true;
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);
		Drawf.dashCircle(x + Vars.tilesize * offset, y + Vars.tilesize * offset, range, MonolithPal.voidf);
	}

	public class VoidfSprayerBuild extends VoidfBuild {
		float alpha = 0;
		@Override
		public void drawSelect() {
			Drawf.dashCircle(x, y, range, MonolithPal.voidf);
		}

		@Override
		public void updateTile() {
			super.updateTile();
			if (voidfModule().voidf > consumeVoidf * Time.delta) {
				subVoidf(consumeVoidf/60f * Time.delta);
				action.get(this);
				Mathf.approachDelta(alpha, 1f, 0.01f);
			} else {
				Mathf.approachDelta(alpha, 0f, 0.01f);
			}
		}

		@Override
		public void draw() {
			Draw.rect(bottomRegion, x, y, 0);
			super.draw();
			Draw.rect(region, x, y, 0);

			if (voidfModule().voidf > consumeVoidf * Time.delta) {
				Draw.color(MonolithPal.voidf);
				Lines.stroke(3f * alpha);
				Lines.circle(x, y, range * alpha);
				Draw.alpha(0.3f);
				Fill.circle(x, y, range * alpha);
			}
		}
	}
}