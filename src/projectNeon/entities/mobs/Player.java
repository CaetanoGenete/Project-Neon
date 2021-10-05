package projectNeon.entities.mobs;

import projectNeon.entities.spawners.ParticleSpawner;
import projectNeon.events.types.PlayerDeathEvent;
import projectNeon.events.types.PlayerHitEvent;
import projectNeon.graphics.AnimatedSprite;
import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.graphics.SpriteSheet;
import projectNeon.input.Keyboard;
import projectNeon.level.tiles.Tile;
import projectNeon.utils.MathUtils;

public class Player extends Mob {

	private double ya = 0;
	private double xa = 2.5;
	private double speed = 2.5;
	
	private int deathTimer = 120;
	private int health = 3;
	
	private boolean falling = false;
	
	private Keyboard input;
	
	private AnimatedSprite player1 = new AnimatedSprite(SpriteSheet.player1, 16, 16, 8);
	
	public Player(int x, int y,  Keyboard input) {
		super(x, y);
		
		sprite = new Sprite(16, 16, 0xFF0000);
		
		this.input = input;
	}
	
	public void update() {
		
		double xa = 0;
		
		if(health != 0)player1.setFrameRate(18/health);
		
		if(walking) player1.update();
		
		if(deathTimer != 0) deathTimer--;
		
		if(health == 0) death();
		
		if(state == State.NORMAL) {
		
			if(collision(0, 1)) {
				falling = false;
				ya = 0;
			} else {
				falling = true;
				if(ya < 5)ya += gravity;
			}
			
			
			if(input.left) xa-=speed;
			if(input.right) xa+=speed;
			
			
			if(collision(MathUtils.abs(xa), 0)){
				falling = false;
				if(ya > 1)ya -= 0.3;
				if(ya < 1) ya = 1;
			}
			
			if(input.up && !falling) ya = -5;
			
		} else if(state == State.FLYING) {
			
			xa += this.xa;
			
			if(collision(1, 0)) death();
			if(collision(0, 1)) ya = 0;
			if(collision(0, -1)) ya = 0;
			
			if(input.up && ya > -5) ya-= 0.15;
			if(input.down && ya < 5) ya += 0.15;
			if(input.left && this.xa > 2.5) this.xa -= 0.01;
			if(input.right && this.xa < 3) this.xa += 0.01;
			
		}
				
		if(xa != 0 || ya != 0) {
			if(state == State.NORMAL)level.add(new ParticleSpawner((int)x + 8, (int)y + 14, 1, -1, 2, 5,10, 2, this, level));
			if(state == State.FLYING)level.add(new ParticleSpawner((int)x + 18, (int)y + 8, 3, -1, 2, 5,(int)(50 * this.xa), 5, this,level));
			walking = true;
			move(xa, ya);
		} else {
			walking = false;
		}
	}
	
	public void action(int ya) {
		if(state == State.NORMAL) {
			if(y - 1  > 0 && !collision(0, -1))y -= 1;
			this.ya = -5;
			if(deathTimer == 0) {
				health -= 1;
				deathTimer = 45;

				PlayerHitEvent event = new PlayerHitEvent(health);
				game.onEvent(event);
			}
		}
		if(state == State.FLYING) {
			if(ya == 0) {
				if(MathUtils.inBounds((int)y + MathUtils.abs(ya), 0, MathUtils.toPixels(level.getHeight()))) y += MathUtils.abs(ya) * 2;
				this.ya += MathUtils.abs(ya) * 5;
				if(deathTimer == 0) {
					health -= 1;
					deathTimer = 45;

					PlayerHitEvent event = new PlayerHitEvent(health);
					game.onEvent(event);
				}
			} else if(ya != 0) {
				if(MathUtils.inBounds((int)y - MathUtils.abs(ya), 0, MathUtils.toPixels(level.getHeight()))) y -= MathUtils.abs(ya) * 2;
				this.ya -= MathUtils.abs(this.ya) * 5;
				if(deathTimer == 0) {
					health -= 1;
					deathTimer = 45;
					
					PlayerHitEvent event = new PlayerHitEvent(health);
					game.onEvent(event);
					
				}
			}
		}
	}
	
	public void collisionX() {
		
	}
	
	public void collisionY() {
		
	}
	
	public void death() {
		PlayerDeathEvent event = new PlayerDeathEvent(level);
		game.onEvent(event);
		
		level.add(new ParticleSpawner(level.spawnX << Tile.TILE_MODIFIER, level.spawnY << Tile.TILE_MODIFIER, 0, 0, 50, new Sprite(3, 3, player1.getSprite().pixels[0]), 200, level));
		remove();
	}

	public void render(Render render) {
		sprite = player1.getSprite();
		render.drawMob((int)x, (int)y, this);
	}

}
