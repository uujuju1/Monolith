package flow.content;

import flow.content.blocks.*;
import java.lang.*;

public class FlowBlocks /*implements Runnable*/ {
	public static Runnable[] list = {
		FlowDistribution::load,
		FlowCrafting::load
	};

	public static void load() {for (Runnable r : list) r.run();}

	// @Override
	// public void run() {load();}
}