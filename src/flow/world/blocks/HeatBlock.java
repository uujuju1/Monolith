package flow.world.blocks;

import arc.*;
import arc.func.*;
import arc.math.*;
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
import flow.world.graph.HeatVertex.*;

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
			Core.bundle.get("bar.Heat"),
			((HeatBuild) entity).getModule().heat > 0 ? Pal.accent : Pal.lancerLaser,
			() -> ((HeatBuild) entity).heatAlpha()
		));
	}

	public ConsumeHeat consumeHeat(float amount, boolean inverse) {return consume(new ConsumeHeat(amount, inverse));}

	public class HeatBuild extends Building implements HeatInterface {
		public HeatModule pModule = new HeatModule(this);

		public float heatAlpha() {return getModule().heat > 0 ? getModule().heat/maxHeat : getModule().heat/minHeat;}

		public HeatBlock hBlock() {return (HeatBlock) block;}
		@Override public HeatModule getModule() {return pModule;}

		@Override
		public void overheat() {
			if (getModule().heat > maxHeat) kill();
			if (getModule().heat < minHeat) setHeat(minHeat);
		}

		@Override
		public void onProximityUpdate() {
			super.onProximityUpdate();
			getVertex().updateEdges();
		}

		@Override
		public void onProximityRemoved() {
			super.onProximityRemoved();
			getVertex().onRemoved();
			if (getGraph().vertexes.size > 0) getGraph().setUpdater(getGraph().vertexes.get(0).getModule().build);
		}

		@Override
		public void updateTile() {
			overheat();
			if (getGraph().updater == this) getGraph().update();
		}

		@Override
		public void write(Writes write) {
			super.write(write);
			getModule().write(write);
		}
		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			getModule().read(read);
			getVertex().updateEdges();
		}
	}
}