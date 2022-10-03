package monolith.content;

import arc.graphics.*;
import mindustry.type.*;

public class MonolithItems {
	public static Item macrosteel, lithium, meanium, lathanium, karanite, sohrite, vakyite, venera, natnium;

	public static void load() {
		macrosteel = new Item("macrosteel", Color.valueOf("6E7080"));
		lithium = new Item("lithium", Color.valueOf("877D7D")) {{flammability = 0.3f;}};
		meanium = new Item("meanium", Color.valueOf("515151")) {{cost = 0.7f;}};
		lathanium = new Item("lathanium", Color.white);
		karanite = new Item("karanite", Color.white);
		sohrite = new Item("sohrite", Color.white);
		vakyite = new Item("vakyite", Color.white);
		venera = new Item("venera", Color.white);
		natnium = new Item("natnium", Color.white);
	}
}