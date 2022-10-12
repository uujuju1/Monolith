package monolith.world.blocks.sandbox;

import monolith.world.blocks.*;

public class HeatSource extends HeatBlock {
	public HeatSource(String name) {
		super(name);
		health = 20000000;
	}

	public class HeatSourceBuild extends HeatBuild {}
}