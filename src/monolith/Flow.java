package monolith;

import arc.*;
import arc.util.*;
import arc.struct.*;
import mindustry.*;
import mindustry.mod.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.type.Weather.*;
import mindustry.game.EventType.*;
import flow.content.*;

public class Flow extends Mod{
	public boolean hasWeather(Weather in) {
		for (WeatherEntry weather : Vars.state.rules.weather) {
			if (weather.weather == in) return true;
		}
		return false;
	}

	public Flow() {
		Events.on(WorldLoadEvent.class, e -> {
			Time.runTask(60 * 2, () -> {
				if(Vars.state.isMenu() || hasWeather(FlowWeathers.storm) || !hasWeather(Weathers.rain)) return;
				Vars.state.rules.weather.add(new Weather.WeatherEntry(FlowWeathers.storm, FlowWeathers.storm.duration * 6f, FlowWeathers.storm.duration * 12f, FlowWeathers.storm.duration / 2f, FlowWeathers.storm.duration * 1.5f));
			});
		});
	}

	@Override
	public void loadContent(){
		Runnable[] load = Seq<Runnable>with(
			FlowStatusEffects::load,
			FlowWeathers::load,
			FlowItems::load,
			FlowLiquids::load,
			FlowUnitTypes::load,
			new FlowBlocks(),
			FlowPlanets::loadc(),
			FlowTechTree::load
		).flatMap(c -> Seq.with(c instanceof FlowBlocks b ? b.list : new Runnable[]{c})).toArray(Runnable.class);
		
		for (Runnable r : load) {
			r.run();
		}
	}
}
