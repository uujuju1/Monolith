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
		addBuild(start);
	}

	public void addBuild(GravityBuild build) {
		if (build.graph != this) return;
		build.graph = this;
		if (build.block instanceof GravityBlock) {
			builds.add(build);
			if (((GravityBlock) build.block).producesGravity) {
				sources.add(build);
			}
			if (((GravityBlock) build.block).usesGravity) {
				ultilizers.add(build);
			}
		}
	}

	public void removeBuild(GravityBuild build) {
		builds.remove(b -> b == build);
		sources.remove(b -> b == build);
		ultilizers.remove(b -> b == build);	
	}
}
