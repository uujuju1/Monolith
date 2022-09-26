package monolith.content;

import arc.graphics.*;
import mindustry.type.*;

public class MonolithLiquids {
	public static Liquid pitch, markyic, hydroxia, methane;

	public void load() {
		pitch = new Liquid("pitch", Color.valueOf("232B23")) {{
			viscosity = 1.7f;
			flammability = 0.3f;
		}};
		markyic = new Liquid("makyic", Color.valueOf("925439"));
		hydroxia = new Liquid("hydroxia", Color.valueOf("CEB2FE")) {{gas = true;}};
		methane = new Liquid("methane", Color.valueOf("84F491")) {{gas = true;}};
	}
}
