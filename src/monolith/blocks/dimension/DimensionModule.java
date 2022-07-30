package monolith.blocks.dimension;

import arc.util.io.*;
import mindustry.world.modules.*;
import monolith.*;
import monolith.core.*;

public class DimensionModule extends BlockModule {
	// id for saving stuff
	public int id;
	// dimension
	public Dimension dimension;
	// static variable used for saving stuff
	public static DimensionModule save;

	public static DimensionModule create(int x, int y) {
		save = new DimensionModule();
		save.dimension = new Dimension(x, y);
		MonolithVars.dimensions.add(new Dimension(save.dimension));
		save.id = MonolithVars.dimensions.size - 1;
		return save;
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