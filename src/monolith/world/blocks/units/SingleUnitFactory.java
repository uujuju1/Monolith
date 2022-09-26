package monolith.world.blocks.units;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.*;

public class SingleUnitFactory extends Block {
	public UnitType unit = UnitTypes.dagger;
	public float craftTime = 60f;
	
	public SingleUnitFactory(String name) {
		super(name);
		destructible = update = sync = true;
		hasItems = hasLiquids = hasPower = true;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("progress", b -> new Bar(Core.bundle.get("bar.progress"), Pal.accent, () -> ((SingleUnitFactoryBuild)b).time/craftTime));
	}

	public class SingleUnitFactoryBuild extends Building {
		public float time, warmup;

		@Override
		public void updateTile() {
			if (efficiency > 0 && team.data().countType(unit) < Units.getCap(team)) {
				time += edelta() * Vars.state.rules.unitBuildSpeed(team);
				warmup = Mathf.lerpDelta(warmup, 1f, 0.05f);
				if (time >= craftTime) {
					consume();
					// craftEffect.at(x, y);
					unit.spawn(team, x, y);
					time %= 1f;
				}
			} else {
				warmup = Mathf.lerpDelta(warmup, 0f, 0.05f);
			}
		}

		@Override
		public void draw() {
			super.draw();
			Draw.draw(Layer.blockOver, () -> Drawf.construct(this, unit, -90f, time / craftTime, warmup, Time.time));
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