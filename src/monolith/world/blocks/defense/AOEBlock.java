package monolith.world.blocks.defense;

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
import mindustry.world.consumers.*;
import monolith.ui.*;
import monolith.world.meta.*;

public class AOEBlock extends Block {
	public Seq<BulletRecipe> plans = new Seq<>();

	public AOEBlock(String name) {
		super(name);
		solid = destructible = true;
		update = sync = true;
		configurable = true;

		consume(new ConsumeItemDynamic((AOEBlockBuild e) -> e.currentPlan != -1 ? e.getRecipe().req : ItemStack.empty));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.output, MonolithStatValues.bulletRecipe(plans));
	}

	@Override
	public void load() {
		super.load();
		plans.each(p -> p.uiIcon = Core.atlas.find(p.name));
	}

	public class BulletRecipe {
		public String name;
		public TextureRegion uiIcon;
		public ItemStack[] requirements = ItemStack.empty;
		public ObjectFloatMap<StatusEffect> statuses = new ObjectFloatMap<>();
		public float 
		damage = 10f,
		reload = 60f,
		range = 80f;

		public BulletRecipe(String name) {
			this.name = name;
		}

		public void status(StatusEffect effect, float duration) {
			statuses.put(effect, duration);
		}
	}

	public class AOEBlockBuild extends Building {
		public float reload;
		public int 
		currentPlan = -1;

		public @Nullable BulletRecipe getRecipe() {
			if (currentPlan == -1) return null;
			return plans.get(currentPlan);
		}

		public void shoot() {
			consume();
			getRecipe().shootEffect.at(x, y);
			Damage.damage(src.team, src.x, src.y, range, damage);
			getRecipe().statuses.each(s -> {
				Damage.status(team, x, y, range, s.key, s.value, true, true);
			});
			reload = 0f;
		}

		@Override
		public boolean acceptItem(Building source, Item item){
			return currentPlan != -1 && items.get(item) < getMaximumAccepted(item) &&	Structs.contains(plans.get(currentPlan).req, stack -> stack.item == item);
		}

		@Override
		public void updateTile() {
			if (getRecipe() == null) return;
			reload += edelta();
			if (reload >= getRecipe().reload) {
				shoot();
			}
		}

		@Override
		public void buildConfiguration(Table table) {
			TableSelection.bulletRecipeSelection(plans, table, plan -> {
				reload = 0f;
				currentPlan = plans.indexOf(plan);
			}, () -> getRecipe());
		}

		@Override
		public void write(Writes w) {
			super.write(w);
			w.f(reload);
			w.i(currentPlan);
		}

		@Override
		public void read(Reads r, byte revision) {
			super.read(r, revision);
			reload = r.f();
			currentPlan = r.i();
		}
	}
}