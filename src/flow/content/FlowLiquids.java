package flow.content;

import arc.graphics.*;
import mindustry.type.*;

public class FlowLiquids {
	public static Liquid vapour;

	public static void load() {vapour = new Liquid("vapour", Color.valueOf("FFFFFF")) {{gas = true;}};}
}
