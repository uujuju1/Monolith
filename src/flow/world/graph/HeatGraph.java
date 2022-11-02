package flow.world.graph;

import arc.util.*
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
		Seq<HeatVertex> res = new Seq<>();
		if (start.getGraph() == this) for (HeatBuild build : start.getBuild().heatProximityBuilds()) if (res.contains(build.getVertex())) {
			res.add(build.getVertex());
			res.add(floodFrom(build.getVertex()));
		} else {Log.errTag("FE", "cant floodFill from an vertex that doesnt belong here");}
		return res;
	} 

	public void onVertexAdded(HeatVertex vertex) {}
	public void onVertexRemoved(HeatVertex vertex) {}

	public void onEdgeAdded(HeatEdge added) {}
	public void onEdgeRemoved(HeatEdge removed) {}

	public void mergeGraphs(HeatGraph other) {for (HeatVertex vertex : other.vertexes) addVertex(vertex);}

	public void addVertex(HeatVertex vertex) {
		vertexes.add(vertex);
		vertex.graph = this;
		onGraphUpdate();
		onVertexAdded(vertex);
	}
	public void removeVertex(HeatVertex vertex) {
		if (vertex.getGraph() != this) {Log.errTag("VRE", "can't remove vertex that is not from this graph"); return;}
		vertexes.remove(vertex);
		onGraphUpdate();
		onVertexRemoved(vertex);
	}
	public void addEdge(HeatEdge edge) {
		edges.add(edge);
		onGraphUpdate();
		onEdgeAdded(edge);
	}
	public void removeEdge(HeatEdge edge) {
		if (!edge.isFrom(this)) {Log.errTag("ERE", "can't remove edge that is not from this graph"); return;}
		edges.remove(edge);
		onGraphUpdate();
		onEdgeRemoved(edge);
	}
}