package monolith.blocks.defense;

import arc.*;
import arc.math.*;
import arc.func.*;
import arc.util.*;
import arc.scene.ui.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;

public class AOEBlock extends Block {
	public float 
	craftTime = 60f,
	reloadTime = 60f,
	updateEffectChance = 0.04f,

	damage = 10,
	range = 80;

	public int maxShots = 10;

	public Effect
	updateEffect = Fx.none,
	craftEffect = Fx.none,
	shootEffect = Fx.none;

	public Cons<Building> drawer = build -> {Draw.rect(region, build.x, build.y, rotate ? build.rotdeg() : 0);};

	public AOEBlock(String name) {
		super(name);
		solid = destructible = true;
		update = sync = true;
		hasItems = true;
		configurable = true;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("shots", entity -> new Bar(Core.bundle.get("bar.shots"), Pal.turretHeat, () -> ((float)((AOEBlockBuild) entity).shots/maxShots)));
		addBar("reload", entity -> new Bar(Core.bundle.get("bar.reload"), Pal.turretHeat, () -> ((AOEBlockBuild) entity).reload/reloadTime));
		addBar("progress", entity -> new Bar(Core.bundle.get("bar.progress"), Pal.turretHeat, () -> ((AOEBlockBuild) entity).progress));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.booster);
		stats.remove(Stat.input);
		stats.add(Stat.output, t -> {
			t.setBackground(Tex.whiteui);
			t.setColor(Pal.darkestGray);
			t.add(new Image(Core.atlas.find("monolith-icon-bullet"))).size(48f).padLeft(10f).padRight(10f).padTop(10f).padBottom(10f);

			t.table(stats -> {
				stats.add(Core.bundle.get("stat.damage")).row();
				stats.add(Core.bundle.get("stat.range"));
			}).padRight(48f);

			t.table(reqs -> {
				input.each(item -> {
					reqs.add(new Image(item.item.uiIcon));
					reqs.add("/" + item.amount).padRight(5f);
				});
			}).padRight(10f);
		});
	}

	public class AOEBlockBuild extends Building {
		float
		reload,
		progress;

		int shots;

		@Override
		public void updateTile() {
			if (efficiency > 0 && shots < maxShots) {
				if (progress > 1) {
					progress = 0;
					shots++;
					consume();
					craftEffect.at(x, y);
				}
				progress += getProgressIncrease(craftTime);

				if(wasVisible && Mathf.chanceDelta(updateEffectChance)){
					updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2));
				}
			}	
			reload -= Time.delta;
		}

		@Override
		public void buildConfiguration(Table table) {
			table.button(b -> b.add(new Image(Core.atlas.find("monolith-icon-bullet"))).size(32f), () -> {
				if (shots > 0 && reload <= 0) {
					shootEffect.at(x, y);
					shots--;
					Damage.damage(team, x, y, range, damage);
					reload = reloadTime;
				}
			});
		}

		@Override
		public void draw() {
			drawer.get(this);
		}
	}
}