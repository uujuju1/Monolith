package monolith.dimension;

import arc.util.io.*;
import monolith.core.*;

public class DimensionModule extends BlockModule {
	public Dimension dimension;

	public DimensionModule create(int x, int y) {
		DimensionModule next new DimensionModule();
		DimensionModule.dimension = new Dimension(x, y);
		return next;
	} 

	@Override
	public void write(Writes write){
	}

	@Override
	public void read(Reads read){
	}
}