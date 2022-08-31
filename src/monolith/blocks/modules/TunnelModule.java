package monolith.blocks.modules;

import arc.util.*;
import arc.util.io.*;
import arc.math.geom.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.world.modules.*;
import monolith.blocks.distribution.*;
import monolith.blocks.distribution.Tunnel.*;

import static mindustry.Vars.*;

public class TunnelModule extends BlockModule {
	public @Nullable TunnelBuild start, end;

	public float travelTime = 0f;

	public TunnelModule(TunnelBuild s) {
		updateModule(s);
	}

	public void updateModule(TunnelBuild s) {
		Vec2 angle = new Vec2();
		start = s;

		if (s.block instanceof Tunnel) {
			for(int i = 0; i < ((Tunnel)s.block).maxTravelLength; i++) {
	
				angle.trns(i, s.rotdeg());
	
				Building next = world.tiles.getn((int)(s.x + angle.x), (int)(s.y + angle.y)).build;
	
				if (next instanceof TunnelBuild) {
					end = (TunnelBuild) next;
					end.module = this;
					travelTime = i * ((Tunnel) s.block).travelTime;
				}
			}
		}
		
	}

	// put here cause the start tunnel uses it
	public void update() {

	}

	public void draw() {
		Lines.stroke(3f);
		if (!(start == null || end == null)) {
			Lines.line(start.x, start.y, end.x, end.y);
		}
	}

	// saving
	@Override
	public void write(Writes write){
		write.f(travelTime);
	}

	@Override
	public void read(Reads read, boolean legacy){
		travelTime = read.f();
	}
}