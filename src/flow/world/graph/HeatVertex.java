package flow.world.graph;

import arc.struct.*;
import flow.world.modules.*;
import flow.world.blocks.HeatBlock.*;

public class HeatVertex {
	public HeatModule module;
	public HeatGraph graph;
	public Seq<HeatEdge> edges = new Seq<>(false, 28, HeatEdge.class);

	public HeatVertex(HeatModule module, HeatGraph graph) {
		this.graph = graph;
		this.module = module;
		graph.addVertex(this);
	}

	public HeatGraph getGraph() {return graph;}
	public HeatModule getModule() {return module;}
	public HeatBuild getBuild() {return module.build;}

	public void disconnect() {getGraph().removeVertex(this);}

	public void onProximityUpdate() {
		clearEdges();
		// for (HeatBuild build : getBuild().heatProximityBuilds()) new HeatEdge(this, build.getVertex()).addSelf();
	}

	public HeatEdge newEdge(HeatVertex v1, HeatVertex v2) {return new HeatEdge(v1, v2);}

	public void addEdge(HeatEdge edge) {if (edge.contains(this)) edges.add(edge);}
	public void removeEdge(HeatEdge edge) {if (edge.contains(this)) edges.remove(edge);}
	public void clearEdges() {edges.each(e -> e.removeSelf());}
}