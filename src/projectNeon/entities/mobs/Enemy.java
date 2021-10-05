package projectNeon.entities.mobs;

import projectNeon.entities.Entity;
import projectNeon.entities.spawners.ParticleSpawner;
import projectNeon.events.Event;
import projectNeon.events.EventDispatcher;
import projectNeon.events.types.EnemyDeathEvent;
import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;

public class Enemy extends Mob {

	private boolean falling = false;
	private double speed = 1.5;
	public double xa, ya;
	
	public Enemy(int x, int y) {
		super(x, y);
		
		sprite = Sprite.enemy;
	}
	
	public void update() {
		
		Player player = level.getPlayer((int)x, (int)y, 150);
		
		if(collision(0, 1)) {
			falling = false;
			ya = 0;
		} else {
			falling = true;
			if(ya < 5)ya += gravity;
		}
		
		if(player != null) {
			
			if((int)player.getX() < (int)x) xa = -speed;
			if((int)player.getX() > (int)x) xa = speed;
			
			if(!collision(xa * 2, 1) && !falling) {
				falling = true;
				ya = -5;
			}
			
			if(collision(1, 0) || collision(-1, 0)) {
				falling = false;
				if((int)player.getY() < (int)y) ya = -5;
				else ya = 1;
			}
			
		} else {
			if(ya == 0)xa = 0;
		}
		
		if(xa != 0 || ya != 0) {
			level.add(new ParticleSpawner((int)x + 8, (int)y + 14, 1, -1, 2, 5, 10, 2, this,level));
			move(xa, ya);
		} 
		
	}
	
	public void collisionX(Entity entity) {
		if(entity == level.getPlayer())level.getPlayer().action((int)ya);
		else y -= 1;
	}
	
	public void collisionY(Entity entity) {
		if(entity == level.getPlayer())level.getPlayer().action((int)ya);
		else y -= 1;
	}
	
	public void death() {
		level.add(new ParticleSpawner((int)x, (int)y, 0, 0, 50, new Sprite(3, 3, 0xFF0000), 200, level));
		EnemyDeathEvent event = new EnemyDeathEvent();
		game.onEvent(event);
		remove();
	}

	public void render(Render render) {
		render.drawSprite((int)x, (int)y, sprite);
	}

}
