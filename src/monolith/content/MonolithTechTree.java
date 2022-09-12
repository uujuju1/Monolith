package monolith.content;

import arc.struct.*;
import mindustry.content.*;

import static mindustry.game.Objectives.*;
import static mindustry.content.TechTree.*;

public class MonolithTechTree {
	public void load() {
		MonolithPlanets.chroma.techTree = nodeRoot("chroma", MonolithBlocks.furnace, true, () -> {
			nodeProduce(MonolithItems.macrosteel, () -> {
				nodeProduce(MonolithItems.lithium, () -> {});
			});

			node(MonolithBlocks.itemLiquidJunction, Seq.with(new Produce(MonolithItems.macrosteel)), () -> {});

			node(MonolithBlocks.lithiumWeaver, Seq.with(new Produce(MonolithItems.macrosteel)), () -> {});

			node(MonolithBlocks.move, Seq.with(new Produce(MonolithItems.macrosteel)), () -> {
				node(MonolithBlocks.accelerate, Seq.with(new Produce(Items.thorium)), () -> {});

				node(MonolithBlocks.caesar, Seq.with(new Produce(Items.plastanium)), () -> {
					node(MonolithBlocks.vigenere, Seq.with(new Produce(Items.thorium)), () -> {});
				});

				node(MonolithBlocks.revenant, Seq.with(new Produce(MonolithItems.lithium)), () -> {});
			});

			node(MonolithBlocks.voidfCrafter, Seq.with(new Produce(MonolithItems.lithium)), () -> {
				node(MonolithBlocks.voidfFactory, Seq.with(new Produce(Items.plastanium)), () -> {});
				node(MonolithBlocks.macroSmelter, () -> {});
				node(MonolithBlocks.voidfConveyor, Seq.with(new Produce(Items.plastanium)), () -> {
					node(MonolithBlocks.voidfRouter, () -> {
						node(MonolithBlocks.voidfTank, () -> {});
					});
				});
				node(MonolithBlocks.coreollis, Seq.with(new Research(MonolithBlocks.voidfConveyor)), () -> {
					node(MonolithBlocks.magnetar, Seq.with(new Research(MonolithBlocks.voidfTank), new Produce(Items.thorium)), () -> {});
				});
			});

			node(MonolithBlocks.artifact, Seq.with(new Produce(Items.plastanium)), () -> {});

			node(MonolithBlocks.sparkWall, Seq.with(new Produce(MonolithItems.lithium)), () -> {});
		});
	}
}