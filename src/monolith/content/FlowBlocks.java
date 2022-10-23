package flow.content;

import flow.content.blocks.*;
import java.lang.*;

public class FlowBlocks {
	public static Runnable[] list = {
		FlowTurrets::load,
		FlowDistribution::load,
		FlowCrafting::load
	};

	public static void load() {for (Runnable r : list) r.run();}
}