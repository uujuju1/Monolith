package monolith.world;

import mindustry.gen.*;
import mindustry.world.*;
import monolith.world.graph.*;
import monolith.world.modules.*;
import monolith.world.interfaces.*;

public class PressureBlock extends Block {
	public float
	minPressure = -100f,
	maxPressure = 100f;

	public PressureBlock(String name) {
		super(name);
		update = sync = destructible = true;
	}

	public class PressureBuild extends Building implements PressureInterface {
		public PressureModule pModule = new PressureModule(this);

		public void graphProximity() {
			for (Building build : proximity) {
				if (build instanceof PressureBuild) {
					if (((PressureBuild) build).pModule.graph != pModule.graph) {
						((PressureBuild) build).changeGraph(pModule.graph);
						((PressureBuild) build).graphProximity();	
					}
				}
			}
		}

		public void changeGraph(PressureGraph graph) {
			graph.addVertex(getVertex());
			removeGraph();
			pModule.graph = graph;
		}
		// removes it's vertex from the graph
		public void removeGraph() {
			graph.vertexes.remove(getVertex());
		}

		@Override
		public PressureModule getModule() {return pModule;}

		@Override
		public void overflow() {
			if (getModule().pressure < minPressure || getModule().pressure > maxPressure) kill();
		}

		@Override
		public void onProximityAdded() {
			for (Building build : proximity) {
				if (build instanceof PressureBuild) {
					if (build.getGraph() != getGraph()) {
						changeGraph(((PressureBuild) build).getGraph());
						graphProximity();
						break;
					}
					PressureEdge next = new PressureEdge(this, build);
					if (!getVertex().hasEqual(next)) vertex.addEdge(next);
				}
			}
		}

		@Override
		public void onRemoved() {
			for (Building build : proximity) {
				if (build instanceof PressureBuild) {
					build.changeGraph(new PressureGraph());
					build.graphProximity();
				}
			}
			getVertex().edges.each(e -> e.removeSelf());
		}

		@Override
		public void updateTile() {
			overflow();
		}
	}
}