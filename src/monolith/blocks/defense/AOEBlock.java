package monolith.blocks.defense;

import arc.*;
import arc.math.*;
import arc.func.*;
import arc.util.*;
import arc.struct.*;
import arc.util.io.*;
import arc.scene.ui.*;
import arc.graphics.*;
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
import mindustry.ui.dialogs.*;
import mindustry.world.consumers.*;

public class AOEBlock extends Block {
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
		public Effect	shootEffect = Fx.none;
		public StatusEffect[] statuses = new StatusEffect[];
		public float[] statusDurations = new Float[];
		public float
		damage = 10f,
		range = 80f,
		reloadTime = 60f;

		public BulletRecipe(String name, ItemStack[] requirements) {
			this.name = name;
			this.req = requirements;
		}

		public void applyStatuses(Building src) {
			if (statuses.length == 0 || statusDurations.length == 0) return;
			int length = Math.min(statusDurations.length, statuses.length);
			for (int i = 0; i < length; i++) {
				Damage.status(src.team, src.x, src.y, range, statuses[i], statusDurations[i], true, true);
			}
		}

		public void load() {
			icon = Core.atlas.find("monolith-icon-bullet-" + name, "monolith-icon-bullet");
		}

		public boolean acceptsItem(Building src) {
			return src.items.has(req);
		}

		public void display(Table t) {
			BaseDialog to = new BaseDialog("ae");
			to.clear();
			to.closeOnBack();

			to.table(table -> {	
				table.add(new Image(icon)).size(64f).padTop(10f).row();
	
				table.table(desc -> {
					desc.add(Core.bundle.get("bullet.monolith-" + name + ".name", "monolith-bullet-" + name)).color(Pal.accent).row();
					desc.add(Core.bundle.get("bullet.monolith-" + name + ".description", "")).color(Color.gray).padBottom(15f).row();
	
					desc.add(Core.bundle.get("stat.damage") + ": " + damage).row();
					desc.add(Core.bundle.get("stat.range") + ": " + range/8f + " " + StatUnit.blocks.localized()).padBottom(15f).row();
	
					desc.add(Core.bundle.get("stat.reload") + ": " + reloadTime/60f +  " " + StatUnit.seconds.localized()).row();
	
					desc.table(cost -> {
						for (ItemStack stack : req) {
							cost.add(new ItemDisplay(stack.item, stack.amount, false)).padLeft(2f).padRight(2f);
						}
					});
				}).pad(10).row();
				table.button("@back", Icon.left, () -> to.hide()).size(210f, 64f);
			}).padBottom(16f).padTop(16f).row();


			t.button(b -> b.table(button -> {
				button.add(new Image(icon)).padRight(10);
				button.add(Core.bundle.get("stat.description"));
			}), () -> {
				to.show();
			}).row();
		}

		public void button(Table t, AOEBlockBuild from) {
			t.button(b -> b.add(new Image(icon)).size(48), () -> {
				if (from.currentPlan != -1) {
					if (plans.get(from.currentPlan) == this) {
						shoot(from);
					}
				}
				from.currentPlan = plans.indexOf(this);
			}).size(48f);
		} 

		public void shoot(AOEBlockBuild src) {
			if (src.reload <= 0 && src.canConsume()) {
				src.consume();
				shootEffect.at(src.x, src.y);
				Damage.damage(src.team, src.x, src.y, range, damage);
				applyStatuses(src);
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
			super.write(w);
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