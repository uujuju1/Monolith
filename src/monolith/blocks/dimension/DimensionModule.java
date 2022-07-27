package monolith.blocks.dimension;

import arc.util.io.*;
import mindustry.world.modules.*;
import monolith.core.*;

public class DimensionModule /*extends BlockModule*/ {
	public Dimension dimension;

	public DimensionModule create(int x, int y) {
		DimensionModule next = new DimensionModule();
		next.dimension = new Dimension(x, y);
		return next;
	} 
}