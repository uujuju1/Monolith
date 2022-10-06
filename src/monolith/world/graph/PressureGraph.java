package monolith.world.graph;

import arc.struct.*;
import monolith.world.modules.*;

public class PressureGraph {
	public Seq<PressureVertex> vertexes = new Seq<>();

	public void addVertex(PressureVertex vertex) {
		vertexes.add(vertex);
	}

	// for testing
	public void addVertex(PressureModule module) {
		vertexes.add(new PressureVertex(module, this));
	}
}