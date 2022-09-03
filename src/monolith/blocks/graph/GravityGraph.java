package monolith.blocks.graph;

import arc.struct.*;
import mindustry.gen.*;
import monolith.blocks.gravity.GravityBlock;
import monolith.blocks.gravity.GravityBlock.*;

public class GravityGraph {
	public Seq<GravityBuild> 
	builds = new Seq<>(),
	sources = new Seq<>(),
	ultilizers = new Seq<>();

	public GravityGraph(GravityBuild start) {
		if (start.block instanceof GravityBlock) {
			builds.add(start);
			if (((GravityBlock) start.block).producesGravity) {
				sources.add(start);
			}
			if (((GravityBlock) start.block).usesGravity) {
				ultilizers.add(start);
			}
		}
	}

	public void addBuild(GravityBuild build) {
		if (build.graph == this) return;

		if (build.block instanceof GravityBlock) {
			builds.add(build);
			if (((GravityBlock) build.block).producesGravity) {
				sources.add(build);
			}
			if (((GravityBlock) build.block).usesGravity) {
				ultilizers.add(build);
			}
		}
		build.graph = this;
	}

	public void mergeGraph(GravityGraph ngraph) {
		if (ngraph == this) return;

		ngraph.builds.each(b -> {
			addBuild(b);
		});
	}

	public void removeBuild(GravityBuild build) {
		builds.remove(b -> b == build);
		sources.remove(b -> b == build);
		ultilizers.remove(b -> b == build);	
	}
}
