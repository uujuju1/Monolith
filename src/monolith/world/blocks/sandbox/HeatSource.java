package monolith.world.blocks.sandbox;

import arc.scene.ui.layout.*;
import monolith.world.blocks.*;

public class HeatSource extends HeatBlock {
	public HeatSource(String name) {
		super(name);
		health = 999999999;
		confiugurable = true;
	}

	public class HeatSourceBuild extends HeatBuild {
		public float current;

		@Override public void overheat() {setHeat(current);}
		@Override public void buildConfiguration(Table table) {table.slider(hBlock().minHeat, hBlock().maxHeat, 0.5f, getModule().heat, value -> current = value);}
	}
}