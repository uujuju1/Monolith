package monolith.blocks.dimension;

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
			table.add(new Image(new TilesDrawable(module.dimension.tiles)));
		}
	}
}