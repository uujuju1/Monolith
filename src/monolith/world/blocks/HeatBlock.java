package monolith.world.blocks;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.util.io.*;
import arc.graphics.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.graphics.*;
import monolith.world.graph.*;
import monolith.world.modules.*;
import monolith.world.consumers.*;
import monolith.world.interfaces.*;
import monolith.world.graph.HeatVertex.*;

public class HeatBlock extends Block {
	public float
	heatFlowMultiplier = 0.05f,
	heatLossPerSecond = 1f,
	minHeat = -273f,
	maxHeat = 1000f;

	public HeatBlock(String name) {
		super(name);
		update = sync = destructible = true;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("Heat", entity -> new Bar(
			Core.bundle.get("bar.Heat"),
			Color.white,
			() -> ((HeatBuild) entity).heatFraction()
		));
	}

	public void consumeHeat(float amount, boolean inverse) {
		consume(new ConsumeHeat(amount, inverse));
	}

	public class HeatBuild extends Building implements HeatInterface {
		public HeatModule pModule = new HeatModule(this);

		public void graphProximity() {
			for (Building build : proximity) {
				if (build instanceof HeatBuild) {
					if (((HeatBuild) build).pModule.graph != pModule.graph) {
						((HeatBuild) build).changeGraph(pModule.graph);
						((HeatBuild) build).graphProximity();	
					}
				}
			}
		}

		public void changeGraph() {
			HeatGraph graph = new HeatGraph();
			graph.setUpdater(this);
			changeGraph(graph);
		}
		public void changeGraph(HeatGraph graph) {
			graph.addVertex(getVertex());
			removeGraph();
			getModule().graph = graph;
		}

		public void removeGraph() {
			getGraph().vertexes.remove(getVertex());
		}

		public float heatAlpha() {
			return Math.abs(getModule().heat)/Math.max(Math.abs(minHeat), maxHeat);
		}
		public float heatFraction() {
			return (getModule().heat + Math.abs(minHeat))/(maxHeat + Math.abs(minHeat));
		}

		@Override
		public HeatModule getModule() {return pModule;}

		@Override
		public void overheat() {
			if (getModule().heat > maxHeat) kill();
		}

		@Override
		public void onProximityAdded() {
			for (Building build : proximity) {
				if (build instanceof HeatBuild) {
					if (((HeatBuild) build).getGraph() != getGraph()) {
						changeGraph(((HeatBuild) build).getGraph());
						graphProximity();
						break;
					}
				}
			}
			for (Building build : proximity) {
				if (build instanceof HeatBuild) {
					getVertex().addEdge(((HeatBuild) build).getVertex(), true);
				}
			}
		}

		@Override
		public void onRemoved() {
			getGraph().destroyVertex(getVertex());
			if (getGraph().vertexes.size > 0) if (getGraph().updater == this) getGraph().setUpdater(getGraph().vertexes.get(0).pModule.build);
		}

		@Override
		public void updateTile() {
			overheat();
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