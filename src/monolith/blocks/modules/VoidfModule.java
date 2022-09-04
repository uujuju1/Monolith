package monolith.blocks.modules;

import arc.util.io.*;
import mindustry.world.modules.*;

public class VoidfModule extends BlockModule {
	public float voidf = 0;

	@Override
	public void write(Writes write) {
		write.f(voidf);
	}

	@Override
	public void read(Reads read, boolean legacy){
		voidf = read.f();
	}
}