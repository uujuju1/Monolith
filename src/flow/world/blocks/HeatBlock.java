package flow.world.blocks;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.struct.*;
import arc.util.io.*;
import arc.graphics.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import flow.world.graph.*;
import flow.world.modules.*;
import flow.world.consumers.*;
import flow.world.interfaces.*;

public class HeatBlock extends Block {
	public float
	heatFlowMultiplier = 0.05f,
	heatLossPerSecond = 1f,
	minHeat = -283f,
	maxHeat = 1000f;

	public HeatBlock(String name) {
		super(name);
		update = sync = destructible = true;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("heat", entity -> new Bar(
			Core.bundle.get("bar.heat"),
			((HeatBuild) entity).getModule().heat > 0 ? Pal.accent : Pal.lancerLaser,
			((HeatBuild) entity).heatAlpha()
		));
	}

	public ConsumeHeat consumeHeat(float amount, boolean inverse) {return consume(new ConsumeHeat(amount, inverse));}

	public class HeatBuild extends Building implements HeatInterface {
		public HeatModule pModule = new HeatModule(this);


		public HeatBlock hBlock() {return (HeatBlock) block;}
		public Floatp heatAlpha() {return () -> 1f;}

		public Seq<HeatBuild> heatProximityBuilds() {
			Seq<HeatBuild> out = new Seq<>();
			for (Building build : proximity) out.add((HeatBuild) build);
			return out;
		}

		@Override
		public HeatModule getModule() {return pModule;}

		@Override
		public void onProximityUpdate() {
			super.onProximityUpdate();
			getVertex().onProximityUpdate();
		}
		@Override
		public void onProximityRemoved() {
			super.onProximityRemoved();
			getVertex().disconnect();
		}

		@Override
		public void overheat() {
			if (getModule().heat > maxHeat) kill();
			if (getModule().heat < minHeat) setHeat(minHeat);
		}

		@Override
		public void updateTile() {overheat();}

		@Override
		public void write(Writes write) {
			super.write(write);
			getModule().write(write);
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			getModule().read(read);
		}
	}
}