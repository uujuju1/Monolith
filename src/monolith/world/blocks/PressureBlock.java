package monolith.world.blocks;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.graphics.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.graphics.*;
import monolith.world.graph.*;
import monolith.world.modules.*;
import monolith.world.consumers.*;
import monolith.world.interfaces.*;
import monolith.world.graph.PressureVertex.*;

public class PressureBlock extends Block {
	public float
	pressureFlowMultiplier = 0.1f,
	minPressure = -100f,
	maxPressure = 100f;

	public PressureBlock(String name) {
		super(name);
		update = sync = destructible = true;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("pressure", entity -> new Bar(
			Core.bundle.get("bar.pressure"),
			Color.white,
			() -> ((PressureBuild) entity).pressureFraction()
		));
	}

	public void consumePressure(float amount) {
		consume(new ConsumePressure(amount));
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

		public void removeGraph() {
			getGraph().vertexes.remove(getVertex());
		}

		public float pressureAlpha() {
			return Math.abs(getModule().pressure)/Math.max(Math.abs(minPressure), maxPressure);
		}
		public float pressureFraction() {
			return (getModule().pressure + minPressure)/(maxPressure + minPressure);
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

		@Override
		public void write(Writes write) {
			super.write(write);
			getModule().write(write);
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			getModule().read(read);
		}
	}
}