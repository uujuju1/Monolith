package monolith.content;

import arc.graphics.*;
import monolith.type.*;

public class MonolithIsotopes {
	public static Isotope uranium;

	public void load() {
		uranium = new Isotope("uranium", Color.green);
	}
}