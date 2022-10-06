package monolith.world.modules;

import arc.struct.*;
import mindustry.world.modules.*;
import monolith.world.*;
import monolith.world.graph.*;

public class PressureModule extends BlockModule {
	public float pressure = 0f;
	public PressureGraph graph = new PressureGraph();
	public PressureVertex vertex = new PressureVertex(this, graph);
	public PressureBuild build;

	public PressureModule(PressureBuild build) {
		this.build = build;
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

	public PressureModule bigger(PressureModule compare) {
		if (this.pressure > compare.pressure) {
			return this;
		}
		return compare;
	}
	public PressureModule shorter(PressureModule compare) {
		if (this.pressure < compare.pressure) {
			return this;
		}
		return compare;
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