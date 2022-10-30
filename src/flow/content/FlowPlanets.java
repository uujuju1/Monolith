"C34345package flow.content;

import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.graphics.*;
import arc.math.geom.*;
import mindustry.*;
import mindustry.ai.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import flow.planets.*;
import flow.content.blocks.*;

public class FlowPlanets {
	public static Planet chroma;

	public class Room {
		public int x, y, r;
		public Room connect;

		public Room(int x, int y, int r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}
	}
	public static void loadc() {
		new FlowPlanets().load();
	}

	public void load() {
		chroma = new Planet("chroma", Planets.sun, 1f, 2) {{
			generator = new ModularPlanetGenerator() {{
				biomes.add(
					new Biome() {{
						heightMap = new Block[]{
							FlowEnvironment.mosaicStone,
							FlowEnvironment.mosaicStone,
							FlowEnvironment.mosaicStone,
							FlowEnvironment.mosaicStone,
							FlowEnvironment.oxaicStone,
							FlowEnvironment.oxaicStone,
							FlowEnvironment.oxaicStone,
							FlowEnvironment.oxaicStone,
							FlowEnvironment.lodeStone,
							FlowEnvironment.lodeStone,
							FlowEnvironment.lodeStone,
							FlowEnvironment.lodeStone
						};
						ores.add(Blocks.oreCoal);
						scale = 1;
						magnitude = 1.5f;
						clampHeight = true;
					}},
					new Biome() {{
						heightMap = new Block[]{
							FlowEnvironment.crystalStone,
							FlowEnvironment.crystalStone,
							FlowEnvironment.crystalStone,
							FlowEnvironment.acrylicStone,
							FlowEnvironment.acrylicStone,
							FlowEnvironment.acrylicStone,
							FlowEnvironment.flakeStone,
							FlowEnvironment.flakeStone,
							FlowEnvironment.flakeStone,
							FlowEnvironment.cupricStone,
							FlowEnvironment.cupricStone,
							FlowEnvironment.cupricStone
						};
						ores.add(Blocks.oreTitanium);
						minValue = 0.4f;
						scale = 0.1;
						magnitude = 2;
						noiseSeed = 1;
					}},
					new Biome() {{
						heightMap = new Block[]{
							FlowEnvironment.crystalIce,
							FlowEnvironment.crystalSnow
						};
						ores.add(Blocks.oreThorium);
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

					Vec2 baseTrns = Tmp.v1.trns(rand().random(360f), width()/2.6f);
					Seq<Room> rooms = Seq.with(
						new Room((int) (baseTrns.x + width()/2f), (int) (baseTrns.y + height()/2f), 20),
						new Room((int) (-baseTrns.x + width()/2f), (int) (-baseTrns.y + height()/2f), 20)
					);

					for (int i = 0; i < 10; i++) {
						Vec2 roomTrns = Tmp.v1.trns(rand().random(360f), width()/2.6f * rand().random(1f));
						rooms.add(new Room((int) (roomTrns.x + width()/2f), (int) (roomTrns.y + height()/2f), rand().random(10, 20)));
					}

					for (Room room : rooms) {
						while (room.connect == null || room.connect == room) room.connect = rooms.random(rand());
						p.erase(room.x, room.y, room.r);
						p.brush(Astar.pathfind(Vars.world.tiles.getn(room.x, room.y), Vars.world.tiles.getn(room.connect.x, room.connect.y), tile -> Mathf.dst(width()/2f, height()/2f), tile -> true), rand().random(10, 20));
					}
					p.distort(118f, 37f);
					p.distort(67f, 17f);
					p.distort(55f, 20f);
					p.median(3, 0.5);
					p.distort(18f, 4f);
					p.distort(120f, 29f);
					p.distort(120f, 29f);
					p.distort(17f, 5f);

					p.erase(rooms.get(0).x, rooms.get(0).y, rooms.get(0).r);
					p.erase(rooms.get(1).x, rooms.get(1).y, rooms.get(1).r);
					p.brush(Astar.pathfind(Vars.world.tiles.getn(rooms.get(0).x, rooms.get(0).y), Vars.world.tiles.getn(rooms.get(1).x, rooms.get(1).y), tile -> Mathf.dst(width()/2f, height()/2f), tile -> Vars.world.getDarkness(tile.x, tile.y) <= 1), 10);
					// TODO non 0 chance that sector blocks ground unit's path
					p.distort(120f, 29f);
					p.distort(17f, 5f);

					p.ores(p.getBiome(p.sector().tile.v).ores);

					Schematics.placeLaunchLoadout(rooms.get(0).x, rooms.get(0).y);
					tiles().getn(rooms.get(1).x, rooms.get(1).y).setOverlay(Blocks.spawn);

					Vars.state.rules.waveSpacing = Mathf.lerp(60 * 65 * 2, 60f * 60f * 1f, Math.max(sector().threat - 0.4f, 0f));
					Vars.state.rules.winWave = sector().info.winWave = 10 + 5 * (int)Math.max(sector().threat * 10, 1);
					Vars.state.rules.waves = sector().info.waves = true;
					Vars.state.rules.env = sector().planet.defaultEnv;
				};
			}};
			atmosphereColor = Color.valueOf("C94A4A");
			meshLoader = () -> new HexMesh(this, 5);
			cloudMeshLoader = () -> new MultiMesh(
				new HexSkyMesh(this, 10, 0.13f, 0.1f, 5, Color.valueOf("C94A4A").mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
				new HexSkyMesh(this, 2, 0.15f, 0.17f, 5, Color.valueOf("C94A4A").mul(0.7f).a(0.75f), 2, 0.45f, 0.9f, 0.41f)
			);
			startSector = 15;
			alwaysUnlocked = accessible = true;
		}};
	}
}