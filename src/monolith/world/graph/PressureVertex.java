package monolith.world.graph;

import arc.func.*;
import arc.struct.*;
import monolith.world.modules.*;

public class PressureVertex {
	public PressureModule pModule;
	public PressureGraph pGraph;
	public Seq<PressureEdge> edges = new Seq<>(false, 28, PressureEdge.class);

	public PressureVertex(PressureModule pModule, PressureGraph graph) {
		this.pModule = pModule;
		this.pGraph = pGraph;
		graph.addVertex(this);
	}

	public void addEdge(PressureVertex with) {
	 PressureEdge edge = new PressureEdge(this, with);
		edges.add(edge);
		with.edges.add(edge);
	}

	public void transferAll(Floatf<PressureEdge> amount) {
		for (PressureEdge edge : edges) edge.transfer(amount.get(edge));
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
			bigger().sub(value);
			shorter().add(value);
		}

		public PressureModule bigger() {
			if (v1.pressure > v2.pressure) return v1;
			return v2;
		}
		public PressureModule shorter() {
			if (v1.pressure < v2.pressure) return v1;
			return v2;
		}
	}
}