package monolith.blocks.voidf.defense;

import arc.util.*;
import arc.func.*;
import arc.math.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.graphics.*;
import monolith.graphics.*;
import monolith.blocks.voidf.*;

public class VoidfSprayer extends VoidfBlock {
	public float 
	range = 80f;
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
		public float alpha;
		@Override
		public void drawSelect() {
			Drawf.dashCircle(x, y, range, MonolithPal.voidf);
		}

		@Override
		public void updateTile() {
			super.updateTile();
			if (voidfModule().voidf > ((VoidfBlock) block).consumeVoidf * Time.delta) {
				subVoidf(((VoidfBlock) block).consumeVoidf/60f * Time.delta, this);
				action.get(this);
				alpha = Mathf.lerp(alpha, 1f, 0.01f);
			} else {
				alpha = Mathf.lerp(alpha, 0f, 0.01f);
			}
		}

		@Override
		public void draw() {
			Draw.rect(bottomRegion, x, y, 0);
			super.draw();
			Draw.rect(region, x, y, 0);

			Draw.color(MonolithPal.voidf);
			Lines.stroke(3f * alpha);
			Lines.circle(x, y, range * alpha);
			Draw.alpha(0.3f);
			Fill.circle(x, y, range * alpha);
		}
	}
}