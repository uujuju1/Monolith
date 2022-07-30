package monolith.ui;

import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.world.*;
import mindustry.ui.dialogs.*;
import monolith.blocks.dimension.*;

public class TilesDialog extends BaseDialog {
	public TilesDialog(String name) {
		super(name);
		addCloseButton();
	}

	public void show(Tiles tiles) {
		cont.clear();
		Table table = new Table();
		table.margin(10);
		table.add(new Image(new TilesDrawable(tiles))).size(Math.max(tiles.width, tiles.height) * 128);
		ScrollPane pane = new ScrollPane(table);
		cont.add(pane);
		show();
	}
}