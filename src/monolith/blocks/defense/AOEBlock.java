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
import monolith.ui.*;
import mindustry.type.modifiers.*;

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
			w.f(reload);
			w.i(currentPlan);
			super.write(w);
		}

		@Override
		public void read(Reads r) {
			reload = r.f();
			currentPlan = r.i();
			super.read(r);
		}
	}
}