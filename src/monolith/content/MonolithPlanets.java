package monolith.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import monolith.planets.*;

public class MonolithPlanets {
	public static Planet chroma;

	public void load() {
		chroma = new Planet("chroma", Planets.sun, 1f, 3) {{
			generator = new ChromaPlanetGenerator();
			meshLoader = () -> new HexMesh(this, 6);
			startSector = 15;
			alwaysUnlocked = accessible = true;
			atmosphereRadIn = 0.1f;
			atmosphereRadOut = 0.3f;
			atmosphereColor = new Color(0.3f, 0.7f, 1f, 0.7f);
		}};
	}
}