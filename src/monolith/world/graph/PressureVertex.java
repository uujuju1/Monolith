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
	public void addEdge(PressureEdge edge) {
		edges.add(edge);
		with.edges.add(edge);
	}

	public void transferAll(Floatf<PressureEdge> amount) {
		for (PressureEdge edge : edges) edge.transfer(amount.get(edge));
	}

	public boolean hasEqual(PressureEdge other) {
		for (PressureEdge edge : edges) if (edge.compare(other)) return true;
		return false;
	}

	public class PressureEdge {
		public PressureVertex v1, v2;

		public PressureEdge(PressureVertex v1, PressureVertex v2) {
			this.v1 = v1;
			this.v2 = v2;
		}

		public void removeSelf() {
			v1.edges.remove(this);
			v2.edges.remove(this);
		}

		public void transfer(float value) {
			PressureModule bigger = bigger(), shorter = shorter();
			bigger.sub(value);
			shorter.add(value);
		}

		public boolean compare(PressureEdge other) {
			return (v1 == other.v1 || v1 == other.v2) && (v2 == other.v2 || v2 == other.v1);
		}

		public PressureModule bigger() {
			if (v1.pModule.pressure >= v2.pModule.pressure) return v1.pModule;
			return v2.pModule;
		}
		public PressureModule shorter() {
			if (v1.pModule.pressure < v2.pModule.pressure) return v1.pModule;
			return v2.pModule;
		}
	}
}