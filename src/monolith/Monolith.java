package monolith;

import mindustry.mod.*;

public class Monolith extends Mod{
	@Override
	public void loadContent(){
		new MonolithItems().load();
		new MonolithBlocks().load();
	}
}
