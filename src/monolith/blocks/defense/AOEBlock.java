package monolith.blocks.defense;

import arc.*;
import arc.math.*;
import arc.func.*;
import arc.util.*;
import arc.struct.*;
import arc.scene.ui.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;

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
	public Seq<BulletRecipe> plans = new Seq<>();

	public AOEBlock(String name) {
		super(name);
		solid = destructible = true;
		update = sync = true;
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
		stats.add(Stat.output, table -> {
			plans.each(bullet -> bullet.display(table));

			table.table(t -> {
				t.setBackground(Tex.whiteui);
				t.setColor(Pal.darkestGray);
				t.add(new Image(Core.atlas.find("monolith-icon-bullet"))).size(48f).padLeft(10f).padRight(10f).padTop(10f).padBottom(10f);
				t.table(aoe -> {
					aoe.table(stats -> {
						stats.setBackground(Tex.underline);
						stats.add(Core.bundle.get("stat.damage") + ": " + damage).row();
						stats.add(Core.bundle.get("stat.range") + ": " + range/8 + " " +StatUnit.blocks.localized());
					}).padRight(48f).row();
					
					aoe.table(craft -> {
						craft.setBackground(Tex.underline);
						craft.add(Core.bundle.get("stat.productiontime") + ": " + craftTime/60f +  " " + StatUnit.seconds.localized()).row();
						craft.add(Core.bundle.get("stat.reload") + ": " + reloadTime/60f +  " " + StatUnit.seconds.localized());
					});
					aoe.add(Core.bundle.get("stat.itemcapacity") + ": " + maxShots);
				});
			});
		});
	}

	@Override
	public void init() {
		consume(new ConsumeItemFilter(i -> {
			plans.each(p -> if (p.acceptsItem(new ItemStack(i, 1))) {
				return true;
			});
			return false;
		}));
	}

	public class BulletRecipe {
		public String name;
		public TextureRegion icon;
		public ItemStack[] req;
		public Effect
		shootEffect = Fx.none,
		craftEffect = Fx.none;

		public float
		damage = 10f,
		range = 80f,
		reloadTime = 60f,
		craftTime = 60f;

		public BulletRecipe(String name, ItemStack[] requirements) {
			this.name = name;
			this.req = requirements;
		} 

		public void load() {
			icon = Core.atlas.find("monolith-icon-bullet-" + name, "monolith-icon-bullet");
		}

		public boolean acceptsItem(ItemStack has) {
			for (ItemStack stack : req) {
				if (stack.item == has.item) return true;
			}
			return false;
		}

		public void display(Table t) {
			t.table(table -> {
				table.table(stats -> {
					stats.setBackground(Tex.underline);
					stats.add(Core.bundle.get("stat.damage") + ": " + damage).row();
					stats.add(Core.bundle.get("stat.range") + ": " + range/8 + " " +StatUnit.blocks.localized());
				}).padRight(48f).row();
					
				table.table(craft -> {
					craft.setBackground(Tex.underline);
					craft.add(Core.bundle.get("stat.productiontime") + ": " + craftTime/60f +  " " + StatUnit.seconds.localized()).row();
					craft.add(Core.bundle.get("stat.reload") + ": " + reloadTime/60f +  " " + StatUnit.seconds.localized());
				});
				table.add(Core.bundle.get("stat.itemcapacity") + ": " + maxShots);
			});
		}
		public void shoot(AOEBlockBuild src) {
			if (src.shots > 0 && src.reload <= 0) {
				shootEffect.at(src.x, src.y);
				src.shots--;
				Damage.damage(src.team, src.x, src.y, range, damage);
				src.reload = reloadTime;
			}
		}
	}

	public class AOEBlockBuild extends Building {
		public float
		reload,
		progress;

		public int shots,
		currentPlan;

		@Override
		public void updateTile() {
			if (efficiency > 0 && shots < maxShots) {
				progress += getProgressIncrease(craftTime);
				if (progress > 1) {
					progress = 0;
					shots++;
					consume();
					craftEffect.at(x, y);
				}

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
			for (int i = 0; i < plans.size; i++) {
				table.button(b -> b.add(new Image(plans.get(i).icon)), () -> plans.get(i).shoot(this));
			}
		}

		@Override
		public void draw() {
			drawer.get(this);
		}
	}
}