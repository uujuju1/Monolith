package monolith;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.mod.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.type.Weather.*;
import monolith.content.*;

public class Monolith extends Mod{

	public boolean hasWeather(Weather in) {
		Vars.state.rules.weather.each(weather -> {
			if (weather.weather == in) return true;
		});
		return false;
	}

	public Monolith() {
		Events.on(WorldLoadEvent.class, () -> {
			Time.runTask(60 * 2, () -> {
				if(!(Vars.state.isMenu() || hasWeather(MonolithWeathers.storm))) {
					Vars.state.rules.weather.add(
						new Weather.WeatherEntry() {{
							weather = MonolithWeathers.storm;
							minFrequency = weather.duration * 6f;
							cooldown = maxFrequency = weather.duration * 12f;
							minDuration = weather.duration / 2f;
							maxDuration = weather.duration * 1.5f;
						}}
					);
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
