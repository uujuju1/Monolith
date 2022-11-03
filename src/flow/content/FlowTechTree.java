package flow.content;

import arc.struct.*;
import mindustry.game.*;
import mindustry.ctype.*;
import mindustry.content.*;

import static mindustry.game.Objectives.*;
import static mindustry.content.TechTree.*;
import static flow.content.FlowItems.*;
import static flow.content.FlowLiquids.*;
import static flow.content.FlowUnitTypes.*;
import static flow.content.blocks.FlowTurrets.*;
import static flow.content.blocks.FlowCrafting.*;
import static flow.content.blocks.FlowDistribution.*;

public class FlowTechTree {

	public static void load() {
		FlowPlanets.chroma.techTree = nodeRoot("chroma", Blocks.coreShard, true, () -> {
			nodeProduce(chromium, () -> {});
			nodeProduce(vapour, () -> {});

			node(itemLiquidJunction, Seq.with(new Produce(chromium)), () -> {});

			node(chromiumSmelter, Seq.with(new SectorComplete(SectorPresets.craters)), () -> {
				node(boiler, Seq.with(new SectorComplete(SectorPresets.ruinousShores)), () -> {});
			});

			node(holder, Seq.with(new SectorComplete(SectorPresets.stainedMountains)), () -> {
				node(pusher, Seq.with(new SectorComplete(SectorPresets.biomassFacility)), () -> {});
			});
		});
	}
}