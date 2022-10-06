package monolith.world;

import mindustry.gen.*;
import mindustry.world.*;
import monolith.world.graph.*;
import monolith.world.modules.*;

public class PressureBlock extends Block {
	public PressureBlock(String name) {
		update = sync = destructible = true;
	}

	public class PressureBuild extends Building {
		public PressureModule pModule = new PressureModule();

		public void graphProximity() {
			for (Building build : proximity) {
				if (build instanceof PressureBuild) {
					if (((PressureBuild) build).pModule.graph != pModule.graph) {
						(PressureBuild build).changeGraph(pModule.graph);
						(PressureBuild build).graphProximity();	
					}
				}
			}
		}

		public void changeGraph(PressureGraph graph) {
			graph.addVertex(pModule.vertex);
			pModule.graph = graph;
		}
	}
}