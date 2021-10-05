package projectNeon.entities.mobs;

import projectNeon.entities.Entity;
import projectNeon.entities.spawners.ParticleSpawner;
import projectNeon.events.types.EnemyDeathEvent;
import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.utils.MathUtils;

public class FlyingEnemy extends Mob {
	
	public double xa = 2.5, ya;
	public boolean active = false;
	
	public FlyingEnemy(int x, int y) {
		super(x, y);
		
		sprite = Sprite.enemy;
	}

	public void update() {
		
		Player player = level.getPlayer((int)x, (int)y, 150);
		
		if(player != null) {
			
			if((int)player.getX() > (int)getX()) active = true;
			
			if(active) {
			
				if((int)player.getX() < (int)x) if(xa > 2) xa -= 0.003;
				if((int)player.getX() > (int)x) if(xa < 3.5) xa += 0.003;
				if((int)player.getY() < (int)y) if(ya > -5) ya -= 0.08;
				if((int)player.getY() > (int)y) if(ya < 5) ya += 0.08;
				
				if(collision(1, 0)) {
					deaths();
				}
				if(collision(0, ya)) ya = 0;
			}
			
		} else {
			if(xa < 3.5 && active) xa += 0.006;
		}
		
		if(active) {
			if(xa != 0 || ya != 0) {
				level.add(new ParticleSpawner((int)x + 18, (int)y + 8, 3, -1, 2, 5,(int)(50 * this.xa), 5, this,level));
				move(xa, ya);
			}
		
		}
		
	}
	
	public void collisionX(Entity entity) {
		
	}
	
	public void collisionY(Entity entity) {
		y -= ya + MathUtils.abs(ya);
		ya = -ya;
		if(entity == level.getPlayer()) level.getPlayer().action((int)ya);
	}
	
	public void deaths() {
		level.add(new ParticleSpawner((int)x, (int)y, 0, 0, 50, new Sprite(3, 3, 0xFF0000), 200, level));
		EnemyDeathEvent event = new EnemyDeathEvent();
		game.onEvent(event);
		remove();
	}

	public void render(Render render) {
		render.drawMob((int)x, (int)y, this);
	}

}
