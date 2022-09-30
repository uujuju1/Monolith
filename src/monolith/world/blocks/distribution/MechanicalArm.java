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
		acceptsItems = false;
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
				armRotation = Mathf.approachDelta(armRotation, 180f, armSpeed);
			} else {
				armRotation = Mathf.approachDelta(armRotation, 0f, armSpeed);
			}

			if (previous() != null && next() != null) {
				if (armRotation < 1 && previous().items.get(Items.copper) > block.itemCapacity) {
					grabbed = true;
					previous().removeStack(Items.copper, block.itemCapacity);
					handleStack(Items.copper, block.itemCapacity, null);
				}
				if (armRotation > 179 && items.get(Items.copper) > block.itemCapacity) {
					grabbed = false;
					removeStack(Items.copper, block.itemCapacity);
					next().handleStack(Items.copper, block.itemCapacity, null);
				}
			}
		}

		@Override
		public void draw() {
			super.draw();
			float
			dx = x + Angles.trnsx(armRotation, armLength * 4f, 0f),
			dy = y + Angles.trnsy(armRotation, armLength * 4f, 0f);
			Draw.rect(armRegion, dx, dy, armRotation);
			Draw.rect(topRegion, x, y, 0f);
		}
	}
}