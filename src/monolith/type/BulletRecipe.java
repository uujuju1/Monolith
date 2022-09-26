package monolith.type;

import arc.*;
import arc.scene.ui.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.entities.*;
import monolith.ui.*;
import monolith.world.blocks.defense.*;
import monolith.world.blocks.defense.AOEBlock.*;

public class BulletRecipe {
	public String name;
	public TextureRegion icon;
	public ItemStack[] req = ItemStack.empty;
	public Effect	shootEffect = Fx.none;
	public StatusEffect[] statuses = new StatusEffect[]{};
	public float[] statusDurations = new float[]{};
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
		BulletDialog dialog = new BulletDialog();
		t.button(b -> b.table(button -> {
			button.add(new Image(icon)).padRight(10);
			button.add(Core.bundle.get("stat.description"));
		}), () -> {
			dialog.show(this);
		}).row();
	}

	public void button(Table t, AOEBlockBuild from) {
		t.button(b -> b.add(new Image(icon)).size(48), () -> {
			if (from.currentPlan != -1) {
				if (((AOEBlock) from.block).plans.get(from.currentPlan) == this) {
					shoot(from);
				}
			}
			from.currentPlan = ((AOEBlock) from.block).plans.indexOf(this);
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