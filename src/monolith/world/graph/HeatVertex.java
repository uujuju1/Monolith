package monolith.world.graph;

import arc.func.*;
import arc.struct.*;
import monolith.world.modules.*;

public class HeatVertex {
	public HeatModule pModule;
	public HeatGraph pGraph;
	public Seq<HeatEdge> edges = new Seq<>(false, 28, HeatEdge.class);

	public HeatVertex(HeatModule pModule, HeatGraph graph) {
		this.pModule = pModule;
		this.pGraph = pGraph;
		graph.addVertex(this);
	}

	public void addEdge(HeatVertex with) {
		addEdge(with, false);
	}
	public void addEdge(HeatVertex with, boolean checkDuplicate) {
		HeatEdge edge = new HeatEdge(this, with);
		if (!checkDuplicate || !hasEqual(edge)) addEdge(edge);
	}
	public void addEdge(HeatEdge edge) {
		edge.v1.edges.add(edge);
		edge.v2.edges.add(edge);
		pGraph.edges.add(edge);
	}

	public boolean hasEqual(HeatEdge other) {
		for (HeatEdge edge : edges) if (edge.compare(other)) return true;
		return false;
	}

	public class HeatEdge {
		public HeatVertex v1, v2;

		public HeatEdge(HeatVertex v1, HeatVertex v2) {
			this.v1 = v1;
			this.v2 = v2;
		}

		public void removeSelf() {
			v1.edges.remove(this);
			v2.edges.remove(this);
		}

		public void transfer(float value) {
			HeatModule bigger = bigger(), shorter = shorter();
			bigger.sub(value);
			shorter.add(value);
		}

		public boolean compare(HeatEdge other) {
			return (v1 == other.v1 || v1 == other.v2) && (v2 == other.v2 || v2 == other.v1);
		}

		public HeatModule bigger() {
			if (v1.pModule.heat >= v2.pModule.heat) return v1.pModule;
			return v2.pModule;
		}
		public HeatModule shorter() {
			if (v1.pModule.heat < v2.pModule.heat) return v1.pModule;
			return v2.pModule;
		}
	}
}