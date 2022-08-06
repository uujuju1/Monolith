package monolith.content;

import mindustry.type.*;
import mindustry.content.*;
import monolith.planets.*;

public class MonolithPlanets {
	public static Planet vita;

	public void load() {
		vita = new Planet("vita", Planets.sun, 1f, 2) {{
			generator = new VitaPlanetGenerator();
			acessible = true;
		}};
	}
}