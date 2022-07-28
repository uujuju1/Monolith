package monolith.blocks.dimension;

import arc.util.io.*;
import mindustry.world.modules.*;
import monolith.*;
import monolith.core.*;

public class DimensionModule extends BlockModule {
	public int id;
	public Dimension dimension;

	public static DimensionModule create(int x, int y) {
		DimensionModule next = new DimensionModule();
		next.dimension = new Dimension(x, y);
		next.id = Monolith.vars.dimensions.size - 1;
		Monolith.vars.dimensions.add(dimension);
		return next;
	} 

	@Override
	public void write(Writes write){
		write.i(id);
	}

	@Override
	public void read(Reads read){
		id = read.i();
		dimension = MonolithVars.dimensions.get(id);
	}
}