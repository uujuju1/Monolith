package monolith.world.blocks.distribution;

import arc.*;
import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.content.*;

public class MechanicalArm extends Block {
	public TextureRegion armRegion, topRegion;
	public float armSpeed = 0.007f;
	public int armLength = 2;

	public MechanicalArm(String name) {
		super(name);
		solid = destructible = true;
		update = sync = rotate = true;
		acceptsItem = false;
	}

	@Override
	public void load() {
		super.load();
		armRegion = Core.atlas.find(name + "-arm");
		topRegion = Core.atlas.find(name + "-top");
	}

	public class MechanicalArmBuild extends Building {
		public boolean grabbed;
		public float armRotation;

		public @Nullable Building next() {
			return tile.nearby(Geometry.d4x(rotation) * armLength, Geometry.d4y(rotation) * armLength).build;
		}
		public @Nullable Building previous() {
			return tile.nearby(-Geometry.d4x(rotation) * armLength, -Geometry.d4y(rotation) * armLength).build;
		}

		@Override
		public void updateTile() {
			if (grabbed) {
				armRotation = Mathf.approachDelta(armRrotation, 180f, armSpeed);
			} else {
				armRotation = Mathf.approachDelta(armRotation, 0f, armSpeed);
			}
			if (previous() != null && next() != null) {
				if (armRotation < 1 && previous().items.get(Items.copper) > block.itemCapacity) {
					grabbed = true;
					previous().removeStack(Items.copper, block.itemCapacity);
					handleStack(Items.copper, block.itemCapacity, previous());
				}
				if (armRotation > 179 && items.get(Items.copper) > block.itemCapacity) {
					grabbed = false;
					removeStack(Items.copper, block.itemCapacity);
					next().handleStack(Items.copper, block.itemCapacity, this);
				}
			}
		}

		@Override
		public void draw() {
			Draw.rect(region, x, y, 0);
			Tmp.v1.set(armLength * 4f, 0).rotate(armRotation);
			Draw.rect(armRegion, x + Tmp.v1.x, y + Tmp.v1.y, rotation + 90);
			Draw.rect(topRegion, x, y, 0);
		}
	}
}