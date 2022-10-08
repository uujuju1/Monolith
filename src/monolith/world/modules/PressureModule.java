package monolith.world.modules;

import arc.struct.*;
import arc.util.io.*;
import mindustry.world.modules.*;
import monolith.world.PressureBlock.*;
import monolith.world.graph.*;

public class PressureModule extends BlockModule {
	public float pressure = 0f;
	public PressureGraph graph = new PressureGraph(build);
	public PressureVertex vertex = new PressureVertex(this, graph);
	public PressureBuild build;

	public PressureModule(PressureBuild build) {
		this.build = build;
		graph.addVertex(vertex);
	}

	public PressureBuild build() {return build;}

	public void add(float value) {
		pressure += value;
	}
	public void sub(float value) {
		pressure -= value;
	}
	public void set(float value) {
		pressure = value;
	}

	@Override
	public void write(Writes write) {
		write.f(pressure);
	}
	@Override
	public void read(Reads read) {
		pressure = read.f();
	}
}