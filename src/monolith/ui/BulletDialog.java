package monolith.ui;

import arc.*;
import arc.scene.ui.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import monolith.blocks.defense.*;

public class BulletDialog extends BaseDialog {
	public BulletRecipe bullet;

	public BulletDialog(BulletRecipe bullet) {
		this.bullet = bullet;
	}

	public void setup() {
		clear();
		closeOnBack();

		table(table -> {	
			table.setBackground(Tex.whiteui);
			table.setColor(Pal.darkestGray);
			table.add(new Image(bullet.icon)).size(64f).padTop(10f).row();

			table.table(desc -> {
				desc.add(Core.bundle.get("bullet.monolith-" + bullet.name + ".name", "monolith-bullet-" + bullet.name)).color(Pal.accent).row();
				desc.add(Core.bundle.get("bullet.monolith-" + bullet.name + ".description", "")).color(Color.gray).padBottom(15f).row();

				desc.add(Core.bundle.get("stat.damage") + ": " + bullet.damage).row();
				desc.add(Core.bundle.get("stat.range") + ": " + bullet.range/8f + " " + StatUnit.blocks.localized()).padBottom(15f).row();

				desc.add(Core.bundle.get("stat.reload") + ": " + bullet.reloadTime/60f +  " " + StatUnit.seconds.localized()).row();

				desc.table(cost -> {
					cost.setBackground(Tex.underline);
					for (ItemStack stack : bullet.req) {
						cost.add(new ItemDisplay(stack.item, stack.amount, false)).padLeft(2f).padRight(2f);
					}
				});
			}).pad(10).row();
			table.button("@back", Icon.left, this.hide()).size(210f, 64f);
		}).padBottom(16f).padTop(16f).row();
	}
}