package monolith.ui;

import arc.*;
import arc.graphics.*;
import arc.scene.ui.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import mindustry.ui.dialogs.*;
import monolith.blocks.defense.AOEBlock.*;
// custom dialog that uses a bullet recipe for stats
public class BulletDialog extends BaseDialog {
	public BulletRecipe bullet;

	public BulletDialog(String title, DialogStyle style) {
		super(title, style);
		setup(this);
	}

	public BulletDialog(String title){
    this(title, Core.scene.getStyle(DialogStyle.class));
  }

	public void setup(BaseDialog to) {
		to.clear();
		to.closeOnBack();

		to.table(table -> {	
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
			table.button("@back", Icon.left, () -> to.hide()).size(210f, 64f);
		}).padBottom(16f).padTop(16f).row();
	}
}