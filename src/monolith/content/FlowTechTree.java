package flow.content;

import arc.struct.*;
import mindustry.content.*;

import static mindustry.game.Objectives.*;
import static mindustry.content.TechTree.*;
import static flow.content.blocks.FlowDistribution.*;
import static flow.content.blocks.FlowCrafting.*;
import static flow.content.FlowItems.*;
import static flow.content.FlowLiquids.*;
import static flow.content.FlowUnitTypes.*;

public class FlowTechTree {
	public static void load() {
		MonolithPlanets.chroma.techTree = nodeRoot("chroma", Blocks.coreShard, true, () -> {
			nodeProduce(chromium, () -> {
				nodeProduce(steam, () -> {});
			});

			node(itemLiquidJunction, Seq.with(new Produce(macrosteel)), () -> {});
			node(heatPipe, Seq.with(new Produce(meanium)), () -> {
				node(advHeatPipe, Seq.with(new Produce(vakyite)), () -> {});

				node(combustionHeater, Seq.with(new Research(Blocks.laserDrill)), () -> {});
				node(heatFan, Seq.with(new Research(Blocks.laserDrill)), () -> {});
			});

			node(chromiumSmelter, () -> {
				// node(lithiumWeaver, Seq.with(new Produce(macrosteel)), () -> {});
			});
		});
	}
}