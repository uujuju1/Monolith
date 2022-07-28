package monolith.blocks.dimension;

import arc.util.io.*;
import mindustry.world.modules.*;
import monolith.*;
import monolith.core.*;

public class DimensionModule extends BlockModule {
	public int id;
	public Dimension dimension;
	public static Dimension save;

	public static DimensionModule create(int x, int y) {
		save = new DimensionModule();
		save.dimension = new Dimension(x, y);
		save.id = MonolithVars.dimensions.size - 1;
		MonolithVars.dimensions.add(new Dimension(save.dimension));
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