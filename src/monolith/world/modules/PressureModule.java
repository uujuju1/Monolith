package monolith.world.modules;

import arc.struct.*;
import monolith.block.*;
import monolith.world.graph.*;

public class PressureModule extends BlockModule {
	public float pressure = 0f;
	public PressureGraph graph = new PressureGraph();
	public PressureVertex vertex = new PressureVertex(this, graph);
	public PressureBuild self;

	public PressureModule(Build self) {
		this.self = self;
	}

	public PressureBuild self() {return self;}

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
}