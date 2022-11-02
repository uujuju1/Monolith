package flow.world.graph;

public class HeatEdge {
	public HeatVertex v1, v2;

	public HeatEdge(HeatVertex v1, HeatVertex v2) {
		this.v1 = v1;
		this.v2 = v2;
		if (v1 == v2) throw new IllegalArgumentException("edge can't connect to itself"); 
	}
	public void addSelf() {
		v1.getGraph().mergeGraphs(v2.getGraph());
		v1.addEdge(this);
		v2.addEdge(this);
	}
	public void removeSelf() {
		v1.removeEdge(this);
		v2.removeEdge(this);
	}

	public boolean contains(HeatVertex v) {return v1 == v || v2 == v;}
	public boolean equals(HeatEdge other) {return contains(other.v1) && contains(other.v2);}
	public boolean isFrom(HeatGraph graph) {return v1.getGraph() == graph || v2.getGraph() == graph;}

	public HeatVertex bigger() {return v1.module.heat >= v2.module.heat ? v1 : v2;}
	public HeatVertex smaller() {return v1.module.heat < v2.module.heat ? v1 : v2;}

	public String toString() {return "edge: " + this + " {" + v1 + ", " + v2 + "}";}
}