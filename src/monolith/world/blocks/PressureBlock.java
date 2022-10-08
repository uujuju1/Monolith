package monolith.world.blocks;

import arc.math.*;
import mindustry.gen.*;
import mindustry.world.*;
import monolith.world.graph.*;
import monolith.world.modules.*;
import monolith.world.interfaces.*;
import monolith.world.graph.PressureVertex.*;

public class PressureBlock extends Block {
	public float
	pressureFlowMultiplier = 0.5f,
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

		public void changeGraph() {
			PressureGraph graph = new PressureGraph();
			graph.setUpdater(this);
			changeGraph(graph);
		}
		public void changeGraph(PressureGraph graph) {
			graph.addVertex(getVertex());
			removeGraph();
			getModule().graph = graph;
		}
		// removes it's vertex from the graph
		public void removeGraph() {
			getGraph().vertexes.remove(getVertex());
		}

		public float pressureMap() {
			Mathf.map(getModule().pressure, minPressure, maxPressure);
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
					if (((PressureBuild) build).getGraph() != getGraph()) {
						changeGraph(((PressureBuild) build).getGraph());
						graphProximity();
						break;
					}
				}
			}
			for (Building build : proximity) {
				if (build instanceof PressureBuild) {
					getVertex().addEdge(((PressureBuild) build).getVertex(), true);
				}
			}
		}

		@Override
		public void onRemoved() {
			removeGraph();
			for (Building build : proximity) {
				if (build instanceof PressureBuild) {
					((PressureBuild) build).changeGraph();
					((PressureBuild) build).graphProximity();
				}
			}
			for (PressureEdge edge : getVertex().edges) edge.removeSelf();
		}

		@Override
		public void updateTile() {
			overflow();
			if (getGraph().updater == this) getGraph().update();
		}
	}
}