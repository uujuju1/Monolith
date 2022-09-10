package monolith.ui;

import arc.*;
import arc.scene.ui.*;
import arc.graphics.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.ui.dialogs.*;
import mindustry.world.meta.*;
import monolith.type.*;

public class BulletDialog extends BaseDialog {
	public BulletDialog() {
		super(Core.bundle.get("info.title") + "");
		addCloseButton();
	}

	public void show(BulletRecipe bullet) {
		cont.clear();
		Table table = new Table();

		table.margin(10);
		table.table(info -> {
			info.add(new Image(bullet.icon)).size(64);
			info.table(name -> {
				name.add(Core.bundle.get("bullet.monolith-" + bullet.name + ".name", bullet.name) + "").left().color(Pal.accent).row();
				name.add("monolith-bullet-" + bullet.name).left().color(Color.gray);
			});
		}).padBottom(10).row();
		table.table(desc -> {
			desc.add(Core.bundle.get("stat.description") + "").left().color(Pal.accent).row();
			desc.add(Core.bundle.get("bullet.monolith-" + bullet.name + ".description", bullet.name) + "").left().padLeft(10).color(Color.lightGray).row();
	
			desc.add(Core.bundle.get("category.general") + "").left().color(Pal.accent).row();
	
			desc.table(damageStat -> {
				damageStat.add(Core.bundle.get("stat.damage") + ":").left().color(Color.lightGray);
				damageStat.add(" " + bullet.damage).left();
			}).left().padLeft(10).row();
			desc.table(rangeStat -> {
				rangeStat.add(Core.bundle.get("stat.range") + ":").left().color(Color.lightGray);
				rangeStat.add(" " + bullet.range/8 + " " + StatUnit.blocks.localized()).left();
			}).left().padLeft(10).row();
			desc.table(reloadStat -> {
				reloadStat.add(Core.bundle.get("stat.reload") + ":").left().color(Color.lightGray);
				reloadStat.add(" " + bullet.reloadTime/60 + " " + StatUnit.seconds.localized()).left();
			}).left().padLeft(10).row();
			if (bullet.req != ItemStack.empty) {
				desc.table(reqsStat -> {
					reqsStat.add(Core.bundle.get("stat.buildcost") + ":").left().color(Color.lightGray);
					for(int i = 0; i < bullet.req.length; i++){
						reqsStat.add(new ItemDisplay(bullet.req[i].item, bullet.req[i].amount, false)).padRight(5).padLeft(5);
					}
				}).left().padLeft(10).row();
			}
			
	
			if (!(bullet.statuses.length <= 0 || bullet.statusDurations.length <= 0)) {
				desc.add(Core.bundle.get("stat.affinities") + "").left().color(Pal.accent).row();
				desc.table(statusStat -> {
					for (int i = 0; i < Math.min(bullet.statuses.length, bullet.statusDurations.length); i++) {
						statusStat.add(new Image(bullet.statuses[i].uiIcon));
						statusStat.add(bullet.statusDurations[i]/60 + " " + StatUnit.seconds.localized()).row();
					}
				}).left().padLeft(10).row();
			}
			
		}).left();

		ScrollPane pane = new ScrollPane(table);
		cont.add(pane);

		show();
	}
}