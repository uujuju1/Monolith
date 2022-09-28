package monolith.content;

import arc.graphics.*;
import mindustry.type.*;

public class MonolithLiquids {
	public static Liquid pitch, makyic, hydroxia, methane;

	public void load() {
		pitch = new Liquid("pitch", Color.valueOf("232B23")) {{
			viscosity = 0.9f;
			flammability = 0.3f;
		}};
		makyic = new Liquid("makyic", Color.valueOf("925439")) {{
			temperature = 0.2f;
			viscosity = 0.2f;
		}};
		hydroxia = new Liquid("hydroxia", Color.valueOf("CEB2FE")) {{
			gas = true;
			moveThroughBlocks = true;
		}};
		methane = new Liquid("methane", Color.valueOf("84F491")) {{
			gas = true;
			flammability = 0.4f;
		}};
	}
}
