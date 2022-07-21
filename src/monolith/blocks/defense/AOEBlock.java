package monolith.blocks.defense;

import arc.*;
import arc.math.*;
import arc.func.*;
import arc.util.*;
import arc.struct.*;
import arc.util.io.*;
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
import mindustry.world.consumers.*;

public class AOEBlock extends Block {
	public float 
	craftTime = 60f,
	reloadTime = 60f,
	updateEffectChance = 0.04f,

	damage = 10,
	range = 80;

	public Effect	shootEffect = Fx.none;

	public Cons<Building> drawer = build -> {Draw.rect(region, build.x, build.y, rotate ? build.rotdeg() : 0);};
	public Seq<BulletRecipe> plans = new Seq<>();

	public AOEBlock(String name) {
		super(name);
		solid = destructible = true;
		update = sync = true;
		configurable = true;

		consume(new ConsumeItemDynamic((AOEBlockBuild e) -> e.currentPlan != -1 ? plans.get(Math.min(e.currentPlan, plans.size - 1)).req : ItemStack.empty));
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("reload", entity -> new Bar(Core.bundle.get("bar.reload"), Pal.turretHeat, () -> ((AOEBlockBuild) entity).reload/plans.get(currentPlan).reloadTime));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.output, table -> {
			table.table(t -> {
				plans.each(bullet -> bullet.display(t));
			});
		});
	}

	@Override
	public void load() {
		super.load();
		plans.each(p -> p.load());
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
		reloadTime = 60f;

		public BulletRecipe(String name, ItemStack[] requirements) {
			this.name = name;
			this.req = requirements;
		} 

		public void load() {
			icon = Core.atlas.find("monolith-icon-bullet-" + name, "monolith-icon-bullet");
		}

		public boolean acceptsItem(Building src) {
			return src.items.has(req);
		}

		public void display(Table t) {
			t.table(table -> {
				t.setBackground(Tex.whiteui);
				t.setColor(Pal.darkestGray);
				t.add(new Image(icon)).size(48f).padLeft(10f).padRight(10f).padTop(10f).padBottom(10f);
				table.table(stats -> {
					stats.setBackground(Tex.underline);
					stats.add(Core.bundle.get("stat.damage") + ": " + damage).row();
					stats.add(Core.bundle.get("stat.range") + ": " + range/8 + " " +StatUnit.blocks.localized());
				}).padRight(48f).row();
					
				table.table(craft -> {
					craft.setBackground(Tex.underline);
					craft.add(Core.bundle.get("stat.productiontime") + ": " + craftTime/60f +  " " + StatUnit.seconds.localized()).row();
					craft.add(Core.bundle.get("stat.reload") + ": " + reloadTime/60f +  " " + StatUnit.seconds.localized());
				}).row();
				StatValues.items(req);
			}).padBottom(5f).padTop(5f).row();
		}

		public void button(Table t, AOEBlockBuild from) {
			t.button(b -> b.add(new Image(icon)), () -> shoot(from)).size(48f);
			from.currentPlan = plans.indexOf(this);
		} 

		public void shoot(AOEBlockBuild src) {
			if (src.reload <= 0) {
				shootEffect.at(src.x, src.y);
				Damage.damage(src.team, src.x, src.y, range, damage);
				src.reload = reloadTime;
			}
		}
	}

	public class AOEBlockBuild extends Building {
		public float reload;
		public int currentPlan = -1;

		@Override
		public boolean acceptItem(Building source, Item item){
			return currentPlan != -1 && items.get(item) < getMaximumAccepted(item) &&	Structs.contains(plans.get(currentPlan).req, stack -> stack.item == item);
		}

		@Override
		public void updateTile() {
			reload -= Time.delta;
		}

		@Override
		public void buildConfiguration(Table table) {
			plans.each(p -> p.button(table, this));
		}

		@Override
		public void draw() {
			drawer.get(this);
		}

		@Override
		public void write(Writes w) {
			super.writes(w);
			w.f(reload);
			w.i(currentPlan);
		}

		@Override
		public void read(Reads r) {
			super.read(r);
			reload = r.f();
			currentPlan = r.i();
		}
	}
}