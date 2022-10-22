package flow.content;

import flow.content.blocks.*;

public class FlowBlocks implements Runnable {
	public static Runnable[] list = {
		FlowDistribution::load,
		FlowCrafting::load
	};

	public static void load() {}

	@Override
	public void run() {load();}
}