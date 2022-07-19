package monolith.blocks.defense;

import arc.*;
import arc.math.*;
import arc.func.*;
import arc.scene.ui.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;

public class AOEBlock extends Block {
	public float 
	craftTime = 60f,
	reloadTime = 60f,
	updateEffectChance = 0.04f,

	damage = 10,
	range = 80;

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
	}

	@Override
	public void setBars() {
		addBar("shots", entity -> new Bar(Core.bundle.get("bar.shots"), Pal.turretHeat, ((AOEBlockBuild) entity).shots));
		addBar("reload", entity -> new Bar(Core.bundle.get("bar.reload"), Pal.turretHeat, ((AOEBlockBuild) entity).reload));
		addBar("progress", entity -> new Bar(Core.bundle.get("bar.progress"), Pal.turretHeat, ((AOEBlockBuild) entity).progress));
	}

	public class AOEBlockBuild extends Building {
		float
		reload,
		progress;

		int shots;

		@Override
		public void updateTile() {
			if (efficiency > 0) {
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
			reload -= getProgressIncrease(reloadTime);
		}

		@Override
		public void buildConfiguration(Table table) {
			table.button(b -> b.add(new Image(Core.atlas.find("monolith-icon-bullet"))), () -> {
				if (shots > 0 && reload <= 0) {
					shootEffect.at(x, y);
					shots--;
					Damage.damage(x, y, range, damage);
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