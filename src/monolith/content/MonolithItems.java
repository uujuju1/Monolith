package monolith.content;

import arc.graphics.*;
import mindustry.type.*;

public class MonolithItems {
	public static Item macrosteel, lithium, meanium, lathanium, karanite, sohrite, vakyite, venera, natnium;

	public static void load() {
		macrosteel = new Item("macrosteel", Color.valueOf("6E7080"));
		lithium = new Item("lithium", Color.valueOf("877D7D")) {{
			cost = 0.3f;
			flammability = 0.3f;
		}};
		meanium = new Item("meanium", Color.valueOf("515151")) {{
			cost = 0.7f;
		}};
		lathanium = new Item("lathanium", Color.valueOf("D4D4D4")) {{
			cost = 1.3f;
		}};
		karanite = new Item("karanite", Color.valueOf("E3AE6F")) {{
			cost = 1.5f;
			flammability = 0.5f;
		}};
		sohrite = new Item("sohrite", Color.valueOf("888DA4")) {{
			cost = 1f;
		}};
		vakyite = new Item("vakyite", Color.valueOf("9AA498")) {{
			cost = 1.4f;
			explosiveness = 0.4f;
		}};
		venera = new Item("venera", Color.valueOf("A47878")) {{
			cost = 2f;
			radioactivity = 0.5f;
		}};
		natnium = new Item("natnium", Color.valueOf("6E7080")) {{
			cost = 2.5f;
			radioactivity = 1f;
		}};
	}
}