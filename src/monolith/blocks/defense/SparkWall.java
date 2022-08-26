package monolth.blocks.defense;

import arc.math.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.defense.*;
import static mindustry.Vars.*;

public class SparkWall extends Wall {
	public BulletType shoot = Bullets.placeholder;
	public float minDamage = 10;

	public SparkWall(String name) {
		super(name);
		sync = true;
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid){
		super.drawPlace(x, y, rotation, valid);
		Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, shoot.lifetime/shoot.speed, Pal.placing);
	}

	public class SparkWallBuild extends Wall.WallBuild {
		public float lastHealth;

		@Override
		public void drawSelect(){
			Drawf.dashCircle(x, y, shoot.lifetime/shoot.speed, team.color);
		}

		public void updateTile() {
			super.updateTile();
			if (lastHealth - minDamage < health) {
				float rnd = Mathf.random(360f);
				shoot.create(this, x, y, rnd);
				shoot.shootEffect.at(x, y, rnd, shoot.hitColor);
			}
			lastHealth = health;
		}
	}
}