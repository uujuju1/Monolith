package monolith.world.graph;

import arc.struct.*;
import monolith.world.modules.*;
import monolith.world.PressureBlock.*;

public class PressureGraph {
	public Seq<PressureVertex> vertexes = new Seq<>(false, 28, PressureVertex.class);
	public PressureBuild updater;

	public void setUpdater(PressureBuild updater) {
		this.updater = updater;
	}

	public void addVertex(PressureVertex vertex) {
		if (!vertexes.contains(vertex)) vertexes.add(vertex);
		vertex.pGraph = this;
	}

	// for testing
	public void addVertex(PressureModule pModule) {
		vertexes.add(new PressureVertex(pModule, this));
	}

	@Override
	public void update() {
		for (PressureVertex vertex : vertexes) {
			vertex.transferAll(edge -> (edge.bigger().pressure, - edge.smaller().pressure)*vertex.pModule.build.((PressureBlock)block).pressureFlowMultiplier);
		}
	}
}