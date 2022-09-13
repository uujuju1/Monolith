package monolith.content;

import arc.graphics.*;
import mindustry.type.*;

public class MonolithItems {
	public static Item macrosteel, lithium, meanium;

	public void load() {
		macrosteel = new Item("macrosteel", Color.valueOf("6E7080"));
		lithium = new Item("lithium", Color.valueOf("877D7D")) {{
			flammability = 0.3f;
		}};
		meanium = new Item("meanium", Color.valueOf("515151")) {{
			cost = 0.7f;
		}};
	}
}