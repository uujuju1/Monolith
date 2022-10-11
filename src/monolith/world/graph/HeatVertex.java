package monolith.world.graph;

import arc.func.*;
import arc.util.*;
import arc.struct.*;
import monolith.world.modules.*;

public class HeatVertex {
	public HeatModule module;
	public HeatGraph graph;
	public Seq<HeatEdge> edges = new Seq<>(false, 28, HeatEdge.class);

	public HeatVertex(HeatModule module, HeatGraph graph) {
		this.module = module;
		this.graph = graph;
		graph.addVertex(this);
	}

	public HeatModule getModule() {return module;}
	public HeatGraph getGraph() {return graph;}

	public void addEdge(HeatVertex with, boolean checkDuplicate) {
		// HeatEdge edge = new HeatEdge(this, with);
		if (!checkDuplicate || !hasEqual(edge)) new HeatEdge(this, with).addSelf();
	}
	public void removeEdge(HeatEdge edge) {edge.removeSelf();}
	public void clearEdges() {for (HeatEdge edge : edges) edge.removeSelf()}

	public boolean hasEqual(HeatEdge other) {
		for (HeatEdge edge : edges) if (edge.equals(other)) return true;
		return false;
	}

	public class HeatEdge {
		public HeatVertex v1, v2;

		public HeatEdge(HeatVertex v1, HeatVertex v2) {
			this.v1 = v1;
			this.v2 = v2;
		}

		public void addSelf() {
			v1.edges.add(this);
			v2.edges.add(this);
		}
		public void removeSelf() {
			if (!v1.edges.remove(this)) Log.warn("removeEdgeWarning", "Edge wasnt removed or doesnt exist in vertex:" + v1);
			if (!v2.edges.remove(this)) Log.warn("removeEdgeWarning", "Edge wasnt removed or doesnt exist in vertex:" + v2);
		}

		public void transfer(float value) {
			HeatModule bigger = bigger(), shorter = shorter();
			bigger.sub(value);
			shorter.add(value);
		}

		public boolean equals(HeatEdge other) {return (v1 == other.v1 || v1 == other.v2) && (v2 == other.v2 || v2 == other.v1);}

		public HeatModule bigger() {return v1.module.heat >= v2.module.heat ? v1.module : v2.module;}
		public HeatModule shorter() {return v1.module.heat < v2.module.heat ? v1.module : v2.module;}
	}
}