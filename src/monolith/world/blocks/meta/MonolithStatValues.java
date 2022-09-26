package monolith.world.blocks.meta;

import arc.*;
import arc.util.*;
import arc.graphics.*;
import arc.scene.style.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import monolith.world.blocks.voidf.*;
import monolith.world.blocks.voidf.production.*;

import static mindustry.world.meta.StatValues.*;

public class MonolithStatValues {

	public static StatValue voidfUnit(VoidfBlock block) {
		return ta -> {
			ta.table(table -> {
				table.background(((TextureRegionDrawable) Tex.whiteui).tint(0.27f, 0.27f, 0.27f, 1f));
				table.table(t -> {
					t.background(((TextureRegionDrawable) Tex.whiteui).tint(0f, 0f, 0f, 1f));
					t.add(Core.bundle.get("stat.voidfModule")).color(Pal.accent).center().pad(10f).row();
					t.table(stats -> {
						stats.background(Tex.underline);
						stats.add(Core.bundle.get("stat.minVoidf") + ": ").left();
						stats.add(fixValue(block.minVoidf)).color(Color.gray).left().row();
				
						stats.add(Core.bundle.get("stat.maxVoidf") + ": ").left();
						stats.add(fixValue(block.maxVoidf)).color(Color.gray).left().row();
						if (block.outputVoidf) {
							stats.add(Core.bundle.get("stat.transferRate") + ": ").left();
							stats.add(fixValue(block.transferRate)).color(Color.gray).left().row();
						}
						
					}).pad(10f).row();
					
					if (block.consumeVoidf > 0 || block instanceof VoidfCrafter) {
						t.add(Core.bundle.get("category.crafting")).color(Pal.accent).center().pad(10f).row();
						t.table(input -> {
							input.add(Core.bundle.get("stat.consumeVoidf") + ": ").left();
							input.add(fixValue(block.consumeVoidf)).color(Color.gray).left().row();
							if (block instanceof VoidfCrafter) {
								input.add(Core.bundle.get("stat.voidfOutput") + ": ").left();
								input.add(fixValue(((VoidfCrafter) block).voidfOutput)).color(Color.gray).left().row();					
							}
						}).pad(10f);
					}
				}).pad(4f);
			});
		};
	}
}