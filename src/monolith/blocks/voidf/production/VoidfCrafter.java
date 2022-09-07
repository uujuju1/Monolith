package monolith.blocks.voidf.production;

import arc.util.*;
import arc.math.*;
import arc.struct.*;
import arc.util.io.*;
import arc.math.geom.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.logic.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.entities.units.*;
import monolith.blocks.voidf.*;

import static mindustry.Vars.*;

public class VoidfCrafter extends VoidfBlock{
	public @Nullable ItemStack outputItem;
	public @Nullable ItemStack[] outputItems;

	public @Nullable LiquidStack outputLiquid;
	public @Nullable LiquidStack[] outputLiquids;
	public int[] liquidOutputDirections = {-1};

	public boolean 
	dumpExtraLiquid = true,
	ignoreLiquidFullness = false,
	legacyReadWarmup = false;

	public float 
	craftTime = 80,
	updateEffectChance = 0.04f,
	warmupSpeed = 0.019f,
	voidfConsumption = 0f,
	voidfOutput = 0f;

	public Effect 
	craftEffect = Fx.none,
	updateEffect = Fx.none;

	public DrawBlock drawer = new DrawDefault();

	public VoidfCrafter(String name){
		super(name);
		hasItems = true;
		ambientSound = Sounds.machine;
		sync = true;
		ambientSoundVolume = 0.03f;
		flags = EnumSet.of(BlockFlag.factory);
		drawArrow = false;

	}

	@Override
	public void setStats(){
		stats.timePeriod = craftTime;
		super.setStats();
		stats.add(Stat.productionTime, craftTime / 60f, StatUnit.seconds);

		if(outputItems != null){
			stats.add(Stat.output, StatValues.items(craftTime, outputItems));
		}

		if(outputLiquids != null){
			stats.add(Stat.output, StatValues.liquids(1f, outputLiquids));
		}
	}

	@Override
	public void setBars(){
		super.setBars();

		//set up liquid bars for liquid outputs
		if(outputLiquids != null && outputLiquids.length > 0){
			//no need for dynamic liquid bar
			removeBar("liquid");

			//then display output buffer
			for(var stack : outputLiquids){
				addLiquidBar(stack.liquid);
			}
		}
	}

	@Override
	public boolean rotatedOutput(int x, int y){
		return false;
	}

	@Override
	public void load(){
		super.load();

		drawer.load(this);
	}

	@Override
	public void init(){
		if(outputItems == null && outputItem != null){
			outputItems = new ItemStack[]{outputItem};
		}
		if(outputLiquids == null && outputLiquid != null){
			outputLiquids = new LiquidStack[]{outputLiquid};
		}
		//write back to outputLiquid, as it helps with sensing
		if(outputLiquid == null && outputLiquids != null && outputLiquids.length > 0){
			outputLiquid = outputLiquids[0];
		}
		outputsLiquid = outputLiquids != null;

		if(outputItems != null) hasItems = true;
		if(outputLiquids != null) hasLiquids = true;
		if(voidfOutput > 0) outputVoidf = true; 

		super.init();
	}

	@Override
	public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
		drawer.drawPlan(this, plan, list);
	}

	@Override
	public TextureRegion[] icons(){
		var out = new TextureRegion[drawer.finalIcons(this).length + 1];
		out[0] = bottomRegion;
		for(int i = 1; i < out.length; i++){
			out[i] = drawer.finalIcons(this)[i - 1];
		}
		return out;
	}

	@Override
	public boolean outputsItems(){
		return outputItems != null;
	}

	@Override
	public void getRegionsToOutline(Seq<TextureRegion> out){
		drawer.getRegionsToOutline(this, out);
	}

	@Override
	public void drawOverlay(float x, float y, int rotation){
		if(outputLiquids != null){
			for(int i = 0; i < outputLiquids.length; i++){
				int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

				if(dir != -1){
					Draw.rect(
						outputLiquids[i].liquid.fullIcon,
						x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
						y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
						8f, 8f
					);
				}
			}
		}
	}

	public class VoidfCrafterBuild extends VoidfBuild{
		public float progress,
		totalProgress,
		warmup;

		@Override
		public void draw(){
			Draw.rect(bottomRegion, x, y, 0);
			super.draw();
			drawer.draw(this);
		}

		@Override
		public void drawLight(){
			super.drawLight();
			drawer.drawLight(this);
		}

		@Override
		public boolean shouldConsume(){
			if (!(block instanceof VoidfBlock)) {
				return false;
			} else if (voidfModule().voidf + voidfOutput > ((VoidfBlock) block).maxVoidf) {
				return false;
			}

			if(outputItems != null){
				for(var output : outputItems){
					if(items.get(output.item) + output.amount > itemCapacity){
						return false;
					}
				}
			}
			if(outputLiquids != null && !ignoreLiquidFullness){
				boolean allFull = true;
				for(var output : outputLiquids){
					if(liquids.get(output.liquid) >= liquidCapacity - 0.001f){
						if(!dumpExtraLiquid){
							return false;
						}
					}else{
						//if there's still space left, it's not full for all liquids
						allFull = false;
					}
				}

				//if there is no space left for any liquid, it can't reproduce
				if(allFull){
					return false;
				}
			}

			return enabled;
		}

		@Override
		public void updateTile(){
			super.updateTile();
			if(efficiency > 0){

				progress += getProgressIncrease(craftTime);
				warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);

				//continuously output based on efficiency
				if(outputLiquids != null){
					float inc = getProgressIncrease(1f);
					for(var output : outputLiquids){
						handleLiquid(this, output.liquid, Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
					}
				}

				if(wasVisible && Mathf.chanceDelta(updateEffectChance)){
					updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
				}
			}else{
				warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
			}

			//TODO may look bad, revert to edelta() if so
			totalProgress += warmup * Time.delta;

			if(progress >= 1f){
				craft();
			}

			dumpOutputs();
		}

		public float warmupTarget(){
			return 1f;
		}

		@Override
		public float warmup(){
			return warmup;
		}

		@Override
		public float totalProgress(){
			return totalProgress;
		}

		public void craft(){
			consume();
			subVoidf(voidfConsumption, this);

			if(outputItems != null){
				for(var output : outputItems){
					for(int i = 0; i < output.amount; i++){
						offload(output.item);
					}
				}
			}
			addVoidf(voidfOutput, this);

			if(wasVisible){
				craftEffect.at(x, y);
			}
			progress %= 1f;
		}

		public void dumpOutputs(){
			if(outputItems != null && timer(timerDump, dumpTime / timeScale)){
				for(ItemStack output : outputItems){
					dump(output.item);
				}
			}

			if(outputLiquids != null){
				for(int i = 0; i < outputLiquids.length; i++){
					int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

					dumpLiquid(outputLiquids[i].liquid, 2f, dir);
				}
			}
		}

		@Override
		public double sense(LAccess sensor){
			if(sensor == LAccess.progress) return progress();
			//attempt to prevent wild total liquid fluctuation, at least for crafters
			if(sensor == LAccess.totalLiquids && outputLiquid != null) return liquids.get(outputLiquid.liquid);
			return super.sense(sensor);
		}

		@Override
		public float progress(){
			return Mathf.clamp(progress);
		}

		@Override
		public int getMaximumAccepted(Item item){
			return itemCapacity;
		}

		@Override
		public boolean shouldAmbientSound(){
			return efficiency > 0;
		}

		@Override
		public void write(Writes write){
			super.write(write);
			write.f(progress);
			write.f(warmup);
			if(legacyReadWarmup) write.f(0f);
		}

		@Override
		public void read(Reads read, byte revision){
			super.read(read, revision);
			progress = read.f();
			warmup = read.f();
			if(legacyReadWarmup) read.f();
		}
	}
}
