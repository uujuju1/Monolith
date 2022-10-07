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
			graph.addVertex(pModule.vertex);
			pModule.graph = graph;
		}

		@Override
		public PressureModule getModule() {return pModule;}

		@Override
		public void overflow() {
			if (getModule().pressure < minPressure || getModule().pressure > maxPressure) kill();
		}

		@Override
		public void onProximityAdded() {
			PressureBuild next;
			for (Building build : proximity) {
				if (build instanceof PressureBuild) {
					next = ((PressureBuild) build);
					changeGraph(next.pModule.graph);
					graphProximity();
					break;
				}
			}
		}
	}
}