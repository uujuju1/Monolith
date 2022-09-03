package monolith.content;

import arc.struct.*;
import mindustry.content.*;

import static mindustry.game.Objectives.*;
import static mindustry.content.TechTree.*;

public class MonolithTechTree {
	public void load() {
		MonolithPlanets.chroma.techtree = nodeRoot("chroma", MonolithBlocks.furnace, true, () -> {
			nodeProduce(MonolithItems.macrosteel, () -> {
				nodeProduce(MonolithItems.lithium);
			});

			node(MonolithBlocks.itemLiquidJunction, Seq.with(MonolithItems.macrosteel));

			node(MonolithBlocks.lithiumWeaver, Seq.with(new Produce(MonolithItems.macrosteel)));

			node(MonolithBlocks.move, Seq.with(new Produce(MonolithItems.macrosteel)), () -> {
				node(MonolithBlocks.accelerate, Seq.with(new Produce(Items.thorium)));
				node(MonolithBlocks.caesar, Seq.with(new Produce(Items.plastanium)), () -> {
					node(MonolithBlocks.vigenere, Seq.with(new Produce(Items.thorium)));
				});
				node(MonolithBlocks.revenant, Seq.with(new Produce(MonolithItems.lithium)));
			});

			node(MonolithBlocks.artifact, Seq.with(new Produce(Items.plastanium)));

			node(MonolithBlocks.sparkWall, Seq.with(new Produce(MonolithItems.lithium)));
		});
	}
}