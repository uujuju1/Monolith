package monolith.content;

import arc.struct.*;
import mindustry.content.*;

import static mindustry.game.Objectives.*;
import static mindustry.content.TechTree.*;
import static monolith.content.MonolithBlocks.*;
import static monolith.content.MonolithItems.*;
import static monolith.content.MonolithLiquids.*;
import static monolith.content.MonolithUnitTypes.*;

public class MonolithTechTree {
	public static void load() {
		MonolithPlanets.chroma.techTree = nodeRoot("chroma", macrosteelFurnace, true, () -> {
			nodeProduce(macrosteel, () -> {
				nodeProduce(lithium, () -> {
					nodeProduce(meanium, () -> {
						nodeProduce(lathanium, () -> {
							nodeProduce(karanite, () -> {
								nodeProduce(natnium, () -> {});
							});
						});
						nodeProduce(sohrite, () -> {
							nodeProduce(vakyite, () -> {
								nodeProduce(venera, () -> {});
							});
						});
					});
					node(pitch, () -> {
						node(hydroxia, () -> {
							node(methane, () -> {});
						});
						node(makyic, () -> {});
					});
				});
			});

			node(itemLiquidJunction, Seq.with(new Produce(macrosteel)), () -> {});

			node(lithiumWeaver, Seq.with(new Produce(macrosteel)), () -> {
				node(alloyInfuser, Seq.with(new Produce(lithium)), () -> {
					node(industrialPress, Seq.with(new Produce(Items.titanium)), () -> {
						node(karanitePress, Seq.with(new Produce(Items.plastanium)), () -> {});
					});
					node(sohritePress, Seq.with(new Produce(Items.titanium)), () -> {});
				});
			});

			node(move, Seq.with(new Produce(macrosteel)), () -> {
				node(accelerate, Seq.with(new Produce(Items.thorium)), () -> {});

				node(caesar, Seq.with(new Produce(Items.plastanium)), () -> {
					node(vigenere, Seq.with(new Produce(Items.thorium)), () -> {});
				});

				node(revenant, Seq.with(new Produce(lithium)), () -> {});
			});

			node(voidfCrafter, Seq.with(new Produce(MonolithItems.lithium)), () -> {
				node(voidfFactory, Seq.with(new Produce(Items.plastanium)), () -> {});
				node(macroSmelter, () -> {});
				node(voidfConveyor, Seq.with(new Produce(Items.plastanium)), () -> {
					node(voidfRouter, () -> {
						node(voidfTank, () -> {});
					});
				});
				node(coreollis, Seq.with(new Research(voidfConveyor)), () -> {
					node(magnetar, Seq.with(new Research(voidfTank), new Produce(Items.thorium)), () -> {});
				});
			});

			node(artifact, Seq.with(new Produce(Items.plastanium)), () -> {});

			node(sparkWall, Seq.with(new Produce(lithium)), () -> {});
		});
	}
}