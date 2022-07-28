package monolith.blocks.dimension;

import arc.util.*;
import arc.util.io.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.world.*;

public class DimensionBlock extends Block {
	public int dimensionW = 10, dimensionH = 10;

	public DimensionBlock(String name) {
		super(name);
		solid = destructible = true;
		update = sync = configurable = true;
	}

	public class DimensionBlockBuild extends Building {
		public DimensionModule module = DimensionModule.create(dimensionW, dimensionH);

		@Override
		public void buildConfiguration(Table table) {
			TilesDialog dialog = new TilesDialog("Dimension");
			table.button(Icon.settings, () -> {
				dialog.show(module.dimension.tiles);
			});
		}
	}
}