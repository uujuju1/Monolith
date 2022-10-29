package flow.world.blocks.units;

import arc.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import arc.scene.style.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.*;
import mindustry.world.meta.*;

public class SingleUnitFactory extends Block {
	public TextureRegion topRegion;
	public UnitType unit = UnitTypes.dagger;
	public Effect craftEffect = Fx.none;
	public float craftTime = 60f;
	
	public SingleUnitFactory(String name) {
		super(name);
		destructible = update = sync = true;
		hasItems = hasLiquids = hasPower = true;
	}

	@Override
	public void load() {
		super.load();
		topRegion = Core.atlas.find(name + "-top");
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("progress", b -> new Bar(Core.bundle.get("bar.progress"), Pal.accent, () -> ((SingleUnitFactoryBuild)b).time/craftTime));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.output, t -> t.table(((TextureRegionDrawable) Tex.whiteui).tint(Pal.darkestGray), name -> {
			name.image(unit.uiIcon).size(48).row();
			name.add(unit.localizedName);
		}).pad(10));
	}

	public class SingleUnitFactoryBuild extends Building {
		public float time, warmup;

		@Override public boolean shouldConsume() {return enabled && team.data().countType(unit) < Units.getCap(team);}

		@Override
		public void updateTile() {
			if (efficiency > 0) {
				time += getProgressIncrease(craftTime) * Vars.state.rules.unitBuildSpeed(team);
				warmup = Mathf.lerpDelta(warmup, 1f, 0.05f);
				if (time >= 1) {
					consume();
					craftEffect.at(x, y);
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
			Draw.z(Layer.blockOver + 0.1f);
			Draw.rect(topRegion, x, y);
		}

		@Override
		public void write(Writes write) {
			super.write(write);
			write.f(time);
			write.f(warmup);
		}

		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			time = read.f();
			warmup = read.f();
		}
	}
}