package monolith.content;

import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.graphics.*;
import mindustry.entities.*;

public class MonolithFx {
	public static Effect furnaceSmelt = new Effect(60f, e -> {
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

	shootDiamondColor = new Effect(30f, e -> {
		Draw.color(e.color, Color.gray, e.finpow());
		Angles.randLenVectors(e.id, 10, 40f * e.finpow(), 0, 15, (x, y) -> {
			Drawf.tri(e.x + x, e.y + y, 3f * e.foutpow(), 6f * e.foutpow(), Angles.angle(e.x + x, e.y + y, e.x, e.y));
			Drawf.tri(e.x + x, e.y + y, 3f * e.foutpow(), 6f * e.foutpow(), Angles.angle(e.x, e.y, e.x + x, e.y + y));
		});
	}),

	aoeShoot = new Effect(30f, e -> {
		Lines.stroke(3f * e.foutpow());
		Lines.circle(e.x, e.y, 40ff * e.finpow());

		for (int i = 0; i < 4; i++) {
			float x = e.x + Angles.trnsx(e.rotation + (i * 90f) + (e.finpow() * 180f), 40f * e.finpow(), 0f);
			float y = e.y + Angles.trnsy(e.rotation + (i * 90f) + (e.finpow() * 180f), 40f * e.finpow(), 0f);
			Drawf.tri(x, y, 8f * e.finpow(), 16f * e.foutpow(), e.rotation + (i * 90f) + (e.finpow() * 180f));
		}

		Angles.randLenVectors(e.id, 15f, 40f * e.finpow(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, 3f * e.foutpow());
		}); 
	});
}