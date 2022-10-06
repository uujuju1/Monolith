package monolith.world.graph;

import arc.struct.*;
import monolith.world.modules.*;

public class PressureGraph {
	public Seq<PressureVertex> vertexes = new Seq<>();

	public void addVertex(PressureVertex vertex) {
		vertexes.add(vertex);
	}
}