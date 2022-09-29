package monolith.world.blocks.production;

import arc.math.*;
import arc.util.*;
import arc.struct.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.meta.*;
import mindustry.world.consumers.*;
import monolith.ui.*;
import monolith.world.meta.*;
/** 
	* TODO might extend both from {@link GenericCrafter} later
	* @author Uujuju
*/
public class MultiCrafter extends Block {
	public Seq<ItemRecipe> recipes = new Seq<>();

	public MultiCrafter(String name) {
		super(name);
		configurable = true;
		hasItems = true;
		solid = update = sync = destructible = true;

		consume(new ConsumeItemDynamic((MultiCrafterBuild e) -> e.currentPlan != -1 ? e.getRecipe().consumeItems : ItemStack.empty));
		consume(new ConsumePowerDynamic(e -> ((MultiCrafterBuild) e).getPowerCons()));
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.output, MonolithStatValues.itemRecipe(recipes));
	}

	public class ItemRecipe {
		public @Nullable ItemStack[]
		consumeItems,
		outputItems;

		// public LiquidStack[]
		// liquids = LiquidStack.empty,
		// outputLiquids = LiquidStack.empty;

		public Effect 
		craftEffect = Fx.none,
		updateEffect = Fx.none;

		public float 
		consumePower = 1f,
		updateEffectChance = 0.03f,
		warmupSpeed = 0.019f,
		craftTime = 60f;

		public @Nullable Item firstOutput() {
			if (outputItems == null) return null;
			if (outputItems.length == 0) return null;
			return outputItems[0].item; 
		}
	}


	public class MultiCrafterBuild extends Building {
		public int currentPlan = -1;
		public float
		progress,
		totalProgres,
		warmup;

		public @Nullable ItemRecipe getRecipe() {
			if (currentPlan == -1) return null;
			return recipes.get(currentPlan);
		}

		public float getPowerCons() {
			return getRecipe() != null ? getRecipe().consumePower : 0f;
		}

		public void dumpOutputs() {
			if(getRecipe() != null && timer(timerDump, dumpTime / timeScale)){
				if (getRecipe().outputItems == null) return; 
				for(ItemStack output : getRecipe().outputItems){
					dump(output.item);
				}
			}
		}

		@Override
		public void buildConfiguration(Table table) {
			TableSelection.itemRecipeSelection(recipes, table, recipe -> currentPlan = recipes.indexOf(recipe), () -> getRecipe());
		}

		@Override
		public boolean acceptItem(Building source, Item item) {
			if (getRecipe() == null) return false;
			return items.get(item) < getMaximumAccepted(item) &&	Structs.contains(getRecipe().consumeItems, stack -> stack.item == item);
		}

		@Override
		public boolean shouldConsume() {
			if (getRecipe() == null) return false;
			if (getRecipe().consumeItems == null) return false;
			return enabled;
		}

		@Override
		public void updateTile() {
			if (efficiency > 0 && getRecipe() != null) {
				warmup = Mathf.approachDelta(warmup, 1f, getRecipe().warmupSpeed);
				progress += getProgressIncrease(getRecipe().craftTime) * warmup;
				totalProgres += edelta() * warmup;

				if (wasVisible && Mathf.chance(getRecipe().updateEffectChance)) getRecipe().updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4f));

				if (progress >= 1f) {
					progress %= 1f;
					consume();
					if (wasVisible) getRecipe().craftEffect.at(x, y);
					if (getRecipe().outputItems != null) {
						for (ItemStack out : getRecipe().outputItems) {
							for (int i = 0; i < out.amount; i++) {
								offload(out.item);
							}
						}	
					}
				}
			} else {
				warmup = Mathf.approachDelta(warmup, 0f, 0.019f);
			}
			dumpOutputs();
		}
	}
}