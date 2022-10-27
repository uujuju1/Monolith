package flow.content.blocks;

import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class FlowEnvironment {
	public static Block 
	crystalIce, crystalSnow,
	crystalStone, flakeStone, cupricStone,
	lodeStone, acrylicStone, mosaicStone, oxaicStone, 

	crystalIceWall, crystalSnowWall,
	crystalWall, flakeWall, cupricWall,
	lodeWall, acrylicWall, mosaicWall, oxaicWall;

	public static void load() {
		crystalIce = new Floor("crystal-ice") {{attributes.set(Attributes.water, 0.4f);}};
		crystalSnow = new Floor("crystal-snow") {{attributes.set(Attributes.water, 0.3f);}};
		crystalStone = new Floor("crystal-stone");
		flakeStone = new Floor("flake-stone");
		cupricStone = new Floor("cupric-stone");
		lodeStone = new Floor("lode-stone") {{attributes.set(Attributes.water, -0.25f);}};
		acrylicStone = new Floor("acrylic-stone");
		mosaicStone = new Floor("mosaic-stone") {{attributes.set(Attributes.water, -0.5f);}};
		oxaicStone = new Floor("oxaic-stone");

		crystalIceWall = new StaticWall("crystal-ice-wall") {{crystalIce.asFloor().wall = this;}};
		crystalSnowWall = new StaticWall("crystal-snow-wall") {{crystalSnow.asFloor().wall = this;}};
		crystalWall = new StaticWall("crystal-wall") {{crystalStone.asFloor().wall = this;}};
		flakeWall = new StaticWall("flake-wall") {{flakeStone.asFloor().wall = this;}};
		cupricWall = new StaticWall("cupric-wall") {{cupricStone.asFloor().wall = this;}};
		lodeWall = new StaticWall("lode-wall") {{lodeStone.asFloor().wall = this;}};
		acrylicWall = new StaticWall("acrylic-wall") {{acrylicStone.asFloor().wall = this;}};
		mosaicWall = new StaticWall("mosaic-wall") {{mosaicStone.asFloor().wall = this;}};
		oxaicWall = new StaticWall("oxaic-wall") {{oxaicStone.asFloor().wall = this;}};
	}
}