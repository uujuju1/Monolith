package monolith;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.mod.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.type.Weather.*;
import mindustry.game.EventType.*;
import monolith.content.*;

public class Monolith extends Mod{
	public boolean hasWeather(Weather in) {
		for (WeatherEntry weather : Vars.state.rules.weather) {
			if (weather.weather == in) return true;
		}
		return false;
	}

	public Monolith() {
		Events.on(WorldLoadEvent.class, e -> {
			Time.runTask(60 * 2, () -> {
				if(Vars.state.isMenu() || hasWeather(MonolithWeathers.storm) || !hasWeather(Weathers.rain)) return;
				Vars.state.rules.weather.add(new Weather.WeatherEntry(MonolithWeathers.storm, MonolithWeathers.storm.duration * 6f, MonolithWeathers.storm.duration * 12f, MonolithWeathers.storm.duration / 2f, MonolithWeathers.storm.duration * 1.5f));
			});
		});
	}

	@Override
	public void loadContent(){
		MonolithStatusEffects.load();
		MonolithWeathers.load();
		MonolithItems.load();
		MonolithLiquids.load();
		MonolithUnitTypes.load();
		MonolithBlocks.load();
		// ah fu-
		new MonolithPlanets.load();
		MonolithTechTree.load();
	}
}
