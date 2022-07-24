package monolith;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.mod.*;
import mindustry.type.*;
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
		Events.on(WorldLoadEvent.class, () -> {
			Time.runTask(60 * 2, () -> {
				if(!(Vars.state.isMenu() || hasWeather(MonolithWeathers.storm))) {
					Vars.state.rules.weather.add(new Weather.WeatherEntry(MonolithWeathers.storm, weather.duration * 6f, weather.duration * 12f, weather.duration / 2f, weather.duration * 1.5f));
				}
			});
		});
	}
	@Override
	public void loadContent(){
		new MonolithStatusEffects().load();
		new MonolithWeathers().load();
		new MonolithItems().load();
		new MonolithBlocks().load();
	}
}
