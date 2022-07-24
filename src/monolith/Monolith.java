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
		Weather storm = MonolithWeathers.storm;
		Events.on(WorldLoadEvent.class, () -> {
			Time.runTask(60 * 2, () -> {
				if(!(Vars.state.isMenu() || hasWeather(storm))) {
					Vars.state.rules.weather.add(new Weather.WeatherEntry(storm, storm.duration * 6f, storm.duration * 12f, storm.duration / 2f, storm.duration * 1.5f));
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
