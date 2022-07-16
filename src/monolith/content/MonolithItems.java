package monolith.content;

import arc.grpahics.*;
import mindustry.type.*;

public class MonolithItems {
	public static Item macrosteel;

	public void load() {
		macrosteel = new Item("macrosteel", Color.valueOf("6E7080"));
	}
}