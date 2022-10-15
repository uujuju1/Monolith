package monolith.content;

import arc.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import monolith.planets.*;

public class MonolithPlanets {
	public static Planet chroma;

	public static void load() {
		chroma = new Planet("chroma", Planets.sun, 1f, 3) {{
			generator = new MonolithPlanetGenerator() {{
				biomes.add(
					new Biome() {{
						heightMap = new Block[]{Blocks.ferricStone, Blocks.carbonStone, Blocks.crystalFloor, Blocks.crystallineStone};
						maxValue = 0.6f;
					}},
					new Biome() {{
						heightMap = new Block[]{Blocks.carbonStone, Blocks.rhyolite, Blocks.regolith, Blocks.redStone};
						minValue = 0.4f;
					}}
				);
				defaultBlock = Blocks.carbonStone;
			}};
			meshLoader = () -> new HexMesh(this, 6);
			startSector = 15;
			alwaysUnlocked = accessible = true;
			hasAtmosphere = false;
		}};
	}
}