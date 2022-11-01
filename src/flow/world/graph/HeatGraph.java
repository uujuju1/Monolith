package flow.world.graph;

import arc.struct.*;
import flow.world.blocks.*;
import flow.world.modules.*;
import flow.world.blocks.HeatBlock.*;

public class HeatGraph {
	public Seq<HeatVertex> vertexes = new Seq<>(false, 28, HeatVertex.class);
	public Seq<HeatEdge> edges = new Seq<>(false, 28, HeatEdge.class);
	public HeatBuild updater;

	public void update() {}
	public void updateEdges() {}
	public void updateVertexes() {}

	public void onGraphUpdate() {}	

	public Seq<HeatVertex> floodFrom(HeatVertex start) {
		if (start.getGraph() != this) return;
		Seq<HeatVertex> res = new Seq<>();
		start.getBuild().getProximityBuilds().each(b -> if (res.contains(b.getVertex())) {
				res.add(b.getVertex());
				res.add(floodFrom(b.getVertex()));
			}
		);
		return res;
	} 

	public void onVertexAdded(HeatVertex vertex) {}
	public void onVertexRemoved(HeatVertex vertex) {}

	public void onEdgeAdded(HeatEdge added) {}
	public void onEdgeRemoved(HeatEdge removed) {}

	public void mergeGraphs(HeatGraph other) {for (HeatVertex vertex : other.vertexes) addVertex(vertex);}

	public void addVertex(HeatVertex vertex) {
		if (vertex.getGraph() != this) return;
		vertexes.add(vertex);
		vertex.graph = this;
		onGraphUpdate();
		onVertexAdded();
	}
	public void removeVertex(HeatVertex vertex) {
		if (vertex.getGraph() != this) return;
		vertexes.remove(vertex);
		onGraphUpdate();
		onVertexRemoved();
	}
	public void addEdge(HeatEdge edge) {
		edges.add(edge);
		onGraphUpdate();
		onEdgeAdded();
	}
	public void removeEdge(HeatEdge edge) {
		edges.remove(edge);
		onGraphUpdate();
		onEdgeRemoved();
	}
}