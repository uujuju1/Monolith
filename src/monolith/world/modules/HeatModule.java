package monolith.world.modules;

import arc.struct.*;
import arc.util.io.*;
import mindustry.world.modules.*;
import monolith.world.graph.*;
import monolith.world.blocks.HeatBlock.*;

public class HeatModule extends BlockModule {
	public float heat = 0f;
	public HeatGraph graph = new HeatGraph();
	public HeatVertex vertex = new HeatVertex(this, graph);
	public HeatBuild build;

	public HeatModule(HeatBuild build) {
		this.build = build;
		graph.setUpdater(build);
		graph.addVertex(vertex);
	}

	public HeatBuild build() {return build;}

	public void add(float value) {
		Heat += value;
	}
	public void sub(float value) {
		Heat -= value;
	}
	public void set(float value) {
		Heat = value;
	}

	@Override
	public void write(Writes write) {
		write.f(heat);
	}
	@Override
	public void read(Reads read) {
		heat = read.f();
	}
}