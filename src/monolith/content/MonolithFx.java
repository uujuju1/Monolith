package monolith.content;

import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.graphics.*;
import mindustry.entities.*;

public class MonolithFx {
	public static Effect 

	macrosteelCraft = new Effect(60f, e -> {
		for(int i = 0; i < 4; i++) {
			float
			dx = e.x + Angles.trnsx((i * 90f) + 45f, 10f, 0f),
			dy = e.y + Angles.trnsy((i * 90f) + 45f, 10f, 0f);
		
			Drawf.tri(dx, dy, 4f * e.foutpow(), 8f * e.foutpow(), Angles.angle(dx, dy, e.x, e.y));
			Drawf.tri(dx, dy, 4f * e.foutpow(), 8f * e.foutpow(), Angles.angle(e.x, e.y, dx, dy));
		}
		
		Lines.stroke(3f * e.fout());
		Angles.randLenVectors(e.id, 10, 40f * e.finpow(), (x, y) -> {
			Lines.lineAngle(e.x + x, e.y + y, Angles.angle(e.x + x, e.y + y, e.x, e.y), 5f * e.foutpow());
		});
		
		Lines.stroke(2f * e.foutpow());
		Lines.circle(e.x, e.y, 40f * e.finpow());
	}),
	lithiumCraft = new Effect(60f, e -> {
		Draw.color(Pal.accent);
		Lines.stroke(3 * e.fout());
		Angles.randLenVectors(e.id, 20, 30 * e.finpow(), (x, y) -> {
		  Lines.lineAngle(e.x + x, e.y + y, Angles.angle(e.x, e.y, e.x + x, e.y + y), 3 * e.fout());
		});
		
		Draw.color(Color.gray);
		Angles.randLenVectors(e.id + 1, 20, 40 * e.finpow(), (x, y) -> {
		  Fill.circle(e.x + x, e.y + y, 10 * (1 - e.finpow()));
		});
	}),
	lathaniumCraft = new Effect(60f, e -> {
		Lines.stroke(e.foutpow());
		Lines.circle(e.x, e.y, 40 * e.finpow());
		
		Angles.randLenVectors(e.id, 20, 40 * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 2 * e.foutpow());
		});
		
		Angles.randLenVectors(e.id + 2, 10, 40 * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 3 * Interp.pow3InInverse.apply(e.fout()));
		});
		
		Draw.alpha(e.finpow());
		Angles.randLenVectors(e.id + 1, 10, 40 * e.foutpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 3 * e.fout());
		});
	}),
	veneraCraft = new Effect(60f, e -> {
		Draw.color(Pal.redSpark);
		Lines.stroke(e.foutpow());
		Lines.circle(e.x, e.y, 40 * e.finpow());
		
		Angles.randLenVectors(e.id, 20, 40 * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 2 * e.foutpow());
		});
		
		Angles.randLenVectors(e.id + 2, 10, 40 * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 3 * Interp.pow3InInverse.apply(e.fout()));
		});
	}),
	sohriteCraft = new Effect(30f, e -> {
		Draw.color();
		Lines.stroke(e.fout());
		Angles.randLenVectors(e.id, 20, 30 * e.finpow(), (x, y) -> {
		  Lines.lineAngle(e.x + x, e.y + y, Angles.angle(e.x, e.y, e.x + x, e.y + y), 3 * e.fout());
		});
		
		Draw.color(Color.gray);
		Angles.randLenVectors(e.id + 1, 20, 40 * e.finpow(), (x, y) -> {
		  Fill.circle(e.x + x, e.y + y, 5 * (1 - e.finpow()));
		});
	}),

	crucibleCraft = new Effect(30f, e -> {
		Angles.randLenVectors(e.id, 20, e.finpow() * 40f, (x, y) -> {
			Fill.circle(e.x + x, e.y + y, e.foutpow() * 4f);
		});
		Lines.stroke(e.foutpow() * 3f);
		Lines.circle(e.x, e.y, e.finpow() * 40f);
	}),

	shootDiamondColor = new Effect(30f, e -> {
		Draw.color(e.color, Color.gray, e.finpow());
		Angles.randLenVectors(e.id, 10, 40f * e.finpow(), 0, 15, (x, y) -> {
			Drawf.tri(e.x + x, e.y + y, 3f * e.foutpow(), 6f * e.foutpow(), Angles.angle(e.x + x, e.y + y, e.x, e.y));
			Drawf.tri(e.x + x, e.y + y, 3f * e.foutpow(), 6f * e.foutpow(), Angles.angle(e.x, e.y, e.x + x, e.y + y));
		});
	}),

	aoeShoot = new Effect(30f, e -> {
		Lines.stroke(3f * e.foutpow());
		Lines.circle(e.x, e.y, 40f * e.finpow());

		for (int i = 0; i < 4; i++) {
			float x = e.x + Angles.trnsx(e.rotation + (i * 90f) + (e.finpow() * 180f), 40f * e.finpow(), 0f);
			float y = e.y + Angles.trnsy(e.rotation + (i * 90f) + (e.finpow() * 180f), 40f * e.finpow(), 0f);
			Drawf.tri(x, y, 8f * e.finpow(), 16f * e.foutpow(), e.rotation + (i * 90f) + (e.finpow() * 180f));
		}

		Angles.randLenVectors(e.id, 15, 40f * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 3f * e.foutpow());
		}); 
	});
}