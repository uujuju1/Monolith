package monolith.world.blocks;

import arc.*;
import arc.func.*;
import arc.math.*;
import arc.util.io.*;
import arc.graphics.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.graphics.*;
import monolith.world.graph.*;
import monolith.world.modules.*;
import monolith.world.consumers.*;
import monolith.world.interfaces.*;
import monolith.world.graph.HeatVertex.*;

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
		addBar("Heat", entity -> new Bar(
			Core.bundle.get("bar.Heat") + ": " + (entity.getModule().heat + 10f),
			Pal.lancerLaser.cpy().lerp(Pal.accent, entity.heatFraction()),
			() -> ((HeatBuild) entity).heatFraction()
		)).set(
			() -> Core.bundle.get("bar.Heat") + ": " + entity.getModule().heat,
			() -> ((HeatBuild) entity).heatFraction(),
			Pal.lancerLaser.cpy().lerp(Pal.accent, entity.heatFraction())
		);
	}

	public ConsumeHeat consumeHeat(float amount, boolean inverse) {return consume(new ConsumeHeat(amount, inverse));}

	public class HeatBuild extends Building implements HeatInterface {
		public HeatModule pModule = new HeatModule(this);

		public float heatAlpha() {return Math.abs(getModule().heat)/Math.max(Math.abs(minHeat), maxHeat);}
		public float heatFraction() {return (getModule().heat + Math.abs(minHeat))/(maxHeat + Math.abs(minHeat));}

		@Override
		public HeatModule getModule() {return pModule;}

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
			if (getGraph().vertexes.size > 0) if (getGraph().updater == this) getGraph().setUpdater(getGraph().vertexes.get(0).getModule().build);
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
		}
	}
}