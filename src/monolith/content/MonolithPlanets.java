package monolith.content;

import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.*;
import arc.math.geom.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import monolith.planets.*;

public class MonolithPlanets {
	public static Planet chroma;

	public static void load() {
		chroma = new Planet("chroma", Planets.sun, 1f, 2) {{
			generator = new MonolithPlanetGenerator() {{
				biomes.add(
					new Biome() {{
						heightMap = new Block[]{
							Blocks.roughRhyolite,
							Blocks.roughRhyolite,
							Blocks.roughRhyolite,
							Blocks.ferricStone,
							Blocks.ferricStone,
							Blocks.ferricStone,
							Blocks.rhyolite,
							Blocks.rhyolite,
							Blocks.rhyolite,
							Blocks.regolith,
							Blocks.regolith,
							Blocks.regolith
						};
						scale = 1;
						magnitude = 1.5f;
						clampHeight = true;
					}},
					new Biome() {{
						heightMap = new Block[]{
							Blocks.ferricStone,
							Blocks.ferricStone,
							Blocks.ferricStone,
							Blocks.ferricStone,
							Blocks.ferricStone,
							Blocks.carbonStone,
							Blocks.carbonStone,
							Blocks.basalt,
							Blocks.basalt,
							Blocks.basalt
						};
						minValue = 0.4f;
						scale = 0.1;
						magnitude = 2;
						noiseSeed = 1;
					}},
					new Biome() {{
						heightMap = new Block[]{Blocks.redIce};
						polarInterp = Interp.pow2In;
						noiseSeed = 2;
						scale = 0.1;
						minValue = 0.25f;
					}}
				);
				defaultBlock = Blocks.carbonStone;
				gen = p -> {
					p.pass((x, y) -> {
						float 
						offsetX = (x/p.width() - 0.5f),
						offsetY = (y/p.height() - 0.5f),
						offsetZ = offsetX;

						p.setBlock(
							p.setFloor(p.getBlock(Tmp.v31.set(
								p.sector().tile.v.x + offsetX,
								p.sector().tile.v.y + offsetY,
								p.sector().tile.v.z + offsetZ
							))).asFloor().wall
						);
					});
					p.distort(123f, 58f);
					p.distort(129f, 39f);
					p.distort(105f, 81f);
					p.distort(157f, 43f);
					p.distort(177f, 68f);
					p.distort(39f, 29f);

					int[] rooms = new int[10];
					for (int i = 0; i < rooms.length; i += 2) {
						Vec2 trns = Tmp.v1.trns(rand().random(360f), width()/2.6f);
						rooms[i] = (int)(trns.x + width()/2f);
						rooms[i + 1] = (int)(trns.y + height()/2f);
					}

					for (int i = 0; i < rooms.length; i += 2) p.erase(rooms[i], rooms[i + 1], 10);
				};
			}};
			atmosphereColor = Color.valueOf("809A5E");
			atmosphereRadIn = 0.07f;
			atmosphereRadOut = 0.25f;
			meshLoader = () -> new HexMesh(this, 5);
			startSector = 15;
			alwaysUnlocked = accessible = true;
		}};
	}
}