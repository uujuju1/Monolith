package monolith;

import mindustry.mod.*;
import monolith.content.*;

public class Monolith extends Mod{
	@Override
	public void loadContent(){
		new MonolithItems().load();
		new MonolithBlocks().load();
	}
}
