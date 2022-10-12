package monolith.world.graph;

import arc.util.*;
import arc.struct.*;
import monolith.world.blocks.*;
import monolith.world.modules.*;
import monolith.world.blocks.HeatBlock.*;
import monolith.world.graph.HeatVertex.*;

public class HeatGraph {
	public Seq<HeatVertex> vertexes = new Seq<>(false, 28, HeatVertex.class);
	public Seq<HeatEdge> edges = new Seq<>(false, 28, HeatEdge.class);
	public HeatBuild updater;

	public void setUpdater(HeatBuild updater) {this.updater = updater;}

	public void addVertex(HeatVertex vertex) {
		if (!vertexes.contains(vertex)) vertexes.add(vertex);
		vertex.pGraph = this;
	}

	public void removeVertex(HeatVertex vertex) {
		vertexes.remove(vertex);
	}

	public void update() {
		if (vertexes.size == 0) return; 
		float[] value = new float[edges.size];
		for (HeatEdge edge : edges) value[edges.indexOf(edge)] = (edge.bigger().heat - edge.shorter().heat)*((HeatBlock) edge.bigger().build.block).heatFlowMultiplier;
		for (HeatEdge edge : edges) edge.transfer(value[edges.indexOf(edge)]);
		for (HeatVertex vertex : vertexes) vertex.pModule.sub(vertex.pModule.heat > 0 ? ((HeatBlock) vertex.pModule.build.block).heatLossPerSecond/60 * Time.delta : -((HeatBlock) vertex.pModule.build.block).heatLossPerSecond/60 * Time.delta);
	}
}