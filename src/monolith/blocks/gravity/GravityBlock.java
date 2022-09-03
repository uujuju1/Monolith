package monolith.blocks.gravity;

import mindustry.gen.*;
import mindustry.world.*;
import monolith.blocks.graph.*;

public class GravityBlock extends Block {
	public boolean usesGravity = false, producesGravity = false;

	public GravityBlock(String name) {
		super(name);
		solid = destructible = true;
		update = sync = true;
	}

	public class GravityBuild extends Building {
		public GravityGraph graph = new GravityGraph(this);

		public void updateTile() {
			for (int i = 0; i < proximity.size; i++) {
				if (proximity.get(i) instanceof GravityBuild) {
					graph.mergeGraph(((GravityBuild) proximity.get(i)).graph);
				}
			}
		}

		@Override
		public void onRemoved() {
			graph.removeBuild(this);
		}
	}
}