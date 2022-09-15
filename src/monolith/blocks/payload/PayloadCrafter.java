package monolith.blocks.payload;

import mindustry.world.*;
import mindustry.content.*;
import mindustry.world.blocks.payloads.*;

public class PayloadCrafter extends PayloadBlock {
	public Block output = Blocks.router;
	public PayloadCrafter(String name) {
		super(name);
		solid = destructible = true;
		outputsPayload = true;
	}

	public class PayloadCrafterBuild extends PayloadBlockBuild<BuildPayload> {
		public float progress;

		@Override
		public void updateTile() {
			if (efficiency > 0f && payload == null) {
				progress += edelta();
				if (progress >= output.buildCost) {
					consume();
					payload = new BuildPayload(output, team);
					payVector.setZero();
					progress %= 1f;
				}
			}
			moveOutPayload();
		}

		@Override
		public void draw() {
			super.draw();
			drawPayload();
		}
	}
}