package monolith.world.graph;

import arc.struct.*;
import monolith.world.modules.*;

public class PressureVertex {
	public PressureModule pModule;
	public PressureGraph pGraph;
	public Seq<PressureEdge> edges = new Seq<>();

	public PressureVertex(PressureModule pModule, PressureGraph pGraph) {
		this.pModule = pModule;
		this.pGraph = pGraph;
		pGraph.addVertex(this);
	}

	public void addEdge(PressureVertex with) {
		edges.add(new PressureEdge(this, with));
		with.edges.add(edges.peek());
	}

	public String toString() {
		return "Vertex:[" + this + "]: {module: " + pModule + ", graph: " + pGraph + "}";
	}

	public class PressureEdge {
		public PressureVertex v1, v2;

		public PressureEdge(PressureVertex v1, PressureVertex v2) {
			this.v1 = v1;
			this.v2 = v2;
		}

		public void transfer(float value) {
			if (v1 == null || v2 == null) {
				throw new NullPointerException("edge vertexes cannot be null for transfer()");
			}
			v1.pModule.bigger(v2.pModule).sub(value);
			v1.pModule.shorter(v2.pModule).add(value);
		}

		public String toString() {
			return "Edge:[" + this + "]: {v1: " + v1 +", v2: " + v2 + "}";
		}
	}
}