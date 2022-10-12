package monolith.world.graph;

import arc.func.*;
import arc.util.*;
import arc.struct.*;
import mindustry.gen.*;
import monolith.world.blocks.*;
import mindustry.world.blocks.*;
import monolith.world.modules.*;

public class HeatVertex {
	public HeatModule module;
	public HeatGraph graph;
	public Seq<HeatEdge> edges = new Seq<>(false, 28, HeatEdge.class);
	// removed tag
	public boolean removed = false;

	public HeatVertex(HeatModule module, HeatGraph graph) {
		this.module = module;
		this.graph = graph;
		graph.addVertex(this);
	}

	public HeatModule getModule() {return module;}
	public HeatGraph getGraph() {return graph;}

	public void addEdge(HeatVertex with) {if (!with.removed) new HeatEdge(this, with).addSelf();}
	public void removeEdge(HeatEdge edge) {edge.removeSelf();}
	public void clearEdges() {for (HeatEdge edge : edges) edge.removeSelf();}

	public boolean hasEqual(HeatEdge other) {
		for (HeatEdge edge : edges) if (edge.equals(other)) return true;
		return false;
	}

	public void onRemoved() {
		getGraph().removeVertex(this);
		clearEdges();
		removed = true;
		for (Building b : getModule().build.proximity) if (b instanceof HeatBuild) ((HeatBuild) b).getVertex().updateEdges();
	}
	public void updateEdges() {
		clearEdges();
		for (Building b : getModule().build.proximity) if (b instanceof HeatBuild) addEdge(((HeatBuild) b).getVertex(), true);
	}

	public class HeatEdge {
		public HeatVertex v1, v2;

		public HeatEdge(HeatVertex v1, HeatVertex v2) {
			this.v1 = v1;
			this.v2 = v2;
			v1.graph = v2.graph;
		}

		public void addSelf() {
			if (!v1.hasEqual(this)) {
				v1.edges.add(this);
				v2.edges.add(this);
			}
		}
		public void removeSelf() {
			if (!v1.edges.remove(this)) Log.warn("removeEdgeWarning", "Edge wasnt removed or doesnt exist in vertex:" + v1);
			if (!v2.edges.remove(this)) Log.warn("removeEdgeWarning", "Edge wasnt removed or doesnt exist in vertex:" + v2);
			if (v1.getGraph().edges.remove(this)) Log.warn("removeEdgeWarning", "Edge wasnt removed or doesnt exist in graph:" + v1.graph);
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