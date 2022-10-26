package flow.world.graph;

import arc.util.*;
import arc.struct.*;
import flow.world.blocks.*;
import flow.world.modules.*;
import flow.world.blocks.HeatBlock.*;
import flow.world.graph.HeatVertex.*;

public class HeatGraph {
	public Seq<HeatVertex> vertexes = new Seq<>(false, 28, HeatVertex.class);
	public Seq<HeatEdge> edges = new Seq<>(false, 28, HeatEdge.class);
	public HeatBuild updater;

	public void setUpdater(HeatBuild updater) {this.updater = updater;}

	public void addVertex(HeatVertex vertex) {
		if (!vertexes.contains(vertex)) vertexes.add(vertex);
		vertex.graph = this;
	}

	public void removeVertex(HeatVertex vertex) {
		vertexes.remove(vertex);
	}

	public void update() {
		if (vertexes.size == 0) return; 
		float[] value = new float[edges.size];
		for (HeatEdge edge : edges) value[edges.indexOf(edge)] = (edge.bigger().heat - edge.shorter().heat)*((HeatBlock) edge.bigger().build.block).heatFlowMultiplier;
		for (HeatEdge edge : edges) edge.transfer(value[edges.indexOf(edge)]);
		for (HeatEdge edge : edges) {
			edge.v1.graph = this;
			edge.v2.graph = this;
		}
		for (HeatVertex vertex : vertexes) vertex.getModule().sub(vertex.getModule().heat > 0 ? ((HeatBlock) vertex.getModule().build.block).heatLossPerSecond/60 * Time.delta : -((HeatBlock) vertex.getModule().build.block).heatLossPerSecond/60 * Time.delta);
	}
}