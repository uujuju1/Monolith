package flow.world.consumers;

import arc.func.*;
import arc.scene.ui.layout.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.consumers.*;

public class ConsumeLiquidDynamic<T extends Building> extends Consume {
	public Func<T, LiquidStack[]> liquids;

	public ConsumeLiquidDynamic(Func<T, LiquidStack[]> liquids) {this.liquids = liquids;}

	@Override public void apply(Block block) {block.hasLiquids = block.acceptLiquids = true;}
	@Override public void display(Stats stats) {}

	@Override
	public void build(Building build, Table table) {
		LiquidStack[][] current = {liquids.get((T) build)};

		table.table(cont -> {
			table.update(() -> {
				if (current[0] == liquids.get((T) build)) {
					rebuild(build, cont);
					current[0] = liquids.get((T) build);
				}
				rebuild(build, cont);
			});
		});
	}

	private void rebuild(Building build, Table table) {
		table.clear();
		int i = 0;

		for (LiquidStack stack : liquids.get((T) build)) {
			table.add(new ReqImage(
				new ItemImage(stack.liquid.uiIcon, stack.amount),
				() -> build.liquids == null && build.liquids.get(stack.liquid) >= stack.amount
			)).padRight(8).left();
			if (++i % 4 == 0) table.row();
		}
	}

	@Override
	public void update(Building build) {
		LiquidStack[][] current = {liquids.get((T) build)};
		build.liquids.remove(current[0].liquid, current[0].amount * build.edelta());
	}

	@Override
	public float efficiency(Building build) {
		LiquidStack[][] current = {liquids.get((T) build)};
		float ed = build.edelta() * build.efficiencyScale();
		if(ed <= 0.00000001f) return 0f;
		return Math.min(build.liquids.get(current[0].liquid) / (current[0].amount * ed), 1f);
	}
}