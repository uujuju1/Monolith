package monolith.blocks.meta;

import arc.*;
import arc.util.*;
import arc.graphics.*;
import arc.scene.style.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.meta.*;
import monolith.blocks.voidf.*;
import monolith.blocks.voidf.production.*;

public class MonolithStatValues {

	// yoinked from source, might remove later
	public static String fixValue(float value){
		int precision = Math.abs((int)value - value) <= 0.001f ? 0 : Math.abs((int)(value * 10) - value * 10) <= 0.001f ? 1 : 2;
		return Strings.fixed(value, precision);
	}

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
						stats.add(block.minVoidf + "").color(Color.gray).left().row();
				
						stats.add(Core.bundle.get("stat.maxVoidf") + ": ").left();
						stats.add(block.maxVoidf + "").color(Color.gray).left().row();
				
						stats.add(Core.bundle.get("stat.transferRate") + ": ").left();
						stats.add(block.transferRate + "").color(Color.gray).left().row();
					}).pad(10f).row();
					
					if (block instanceof VoidfCrafter) {
						t.add(Core.bundle.get("category.crafting")).color(Pal.accent).center().pad(10f).row();
						t.table(input -> {
							input.add(Core.bundle.get("stat.voidfConsumption") + ": ").left();
							input.add(((VoidfCrafter) block).voidfConsumption + "").color(Color.gray).left().row();
							input.add(Core.bundle.get("stat.voidfOutput") + ": ").left();
							input.add(((VoidfCrafter) block).voidfOutput + "").color(Color.gray).left().row();
						}).pad(10f);
					}
				}).pad(4f);
			});
		};
	}
}