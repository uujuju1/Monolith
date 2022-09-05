package monolith.graphics;

import arc.util.*;
import arc.math.*;
import arc.graphics.g2d.*;

public class MonolithDrawf {
	public static Rand rand = new Rand();

	public static void voidfSmoke(int seed, float x, float y, int particles, float particleSize, float baseLife, float baseSize) {
		rand.setSeed(seed);
		float angle = rand.random(360f);
		float fin = (rand.random(1f) * (Time.time/baseLife)) % 1;
		float len = rand.random(baseSize/2f, baseSize) * fin;
		float
		trnsx = x + Angles.trnsx(angle, len, rand.random(-baseSize/2f, baseSize/2f)),
		trsny = y + Angles.trnsy(angle, len, rand.random(-baseSize/2f, baseSize/2f));
		Fill.circle(trnsx, trnsy, Interp.sine.apply(fin * 2f) * particleSize);
	}
}