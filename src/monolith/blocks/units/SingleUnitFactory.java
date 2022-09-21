package monolith.blocks.units;

import arc.*;
import arc.util.io.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.graphics.*;

public class SingleUnitFactory extends Block {
	public UnitType unit = UnitTypes.dagger;
	public float craftTime = 60f;
	
	public SingleUnitFactory(String name) {
		super(name);
		destructible = update = sync = true;
		hasItems = hasLiquqids = hasPower = true;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("progress", b -> new Bar(Core.bundle.get("bar.progress"), Pal.accent, () -> b.time/craftTime));
	}

	public class SingleUnitFactoryBuild extends Building {
		public float time;

		@Override
		public void updateTile() {
			time += edelta();
			if (time >= craftTime) {
				consume();
				// craftEffect.at(x, y);
				unit.create(team, x, y);
				time %= 1f;
			}
		}

		@Override
		public void write(Writes write) {
			super.write(write);
			write.f(time);
		}

		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			time = read.f();
		}
	}
}