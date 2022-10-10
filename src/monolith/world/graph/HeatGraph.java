package monolith.world.graph;

import arc.struct.*;
import monolith.world.blocks.*;
import monolith.world.modules.*;
import monolith.world.blocks.HeatBlock.*;
import monolith.world.graph.HeatVertex.*;

public class HeatGraph {
	public Seq<HeatVertex> vertexes = new Seq<>(false, 28, HeatVertex.class);
	public Seq<HeatEdge> edges = new Seq<>(false, 28, HeatEdge.class);
	public HeatBuild updater;

	public void setUpdater(HeatBuild updater) {
		this.updater = updater;
	}

	public void addVertex(HeatVertex vertex) {
		if (!vertexes.contains(vertex)) vertexes.add(vertex);
		vertex.pGraph = this;
	}

	// for testing
	public void addVertex(HeatModule pModule) {
		vertexes.add(new HeatVertex(pModule, this));
	}

	public void destroyVertex(HeatVertex vertex) {
		vertexes.remove(vertex);
		for (HeatEdge edge : vertex.edges) edge.removeSelf();
	}

	public void update() {
		float[] value = new float[edges.size];
		for (HeatEdge edge : edges) value[edges.indexOf(edge)] = (edge.bigger().heat - edge.shorter().heat)*((HeatBlock)edge.bigger().pModule.build.block).heatFlowMultiplier;
		for (HeatEdge edge : edges) edge.transfer(value[edges.indexOf(edge)]);
		for (HeatVertex vertex : vertexes) vertex.pModule.heat.sub(vertex.pModule.heat * ((HeatBlock) vertex.pModule.build.block).heatLossMultiplier); 
	}
}