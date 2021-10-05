package projectNeon.entities.particle;

import projectNeon.entities.Entity;
import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.level.tiles.Tile;

public class Particle extends Entity {

	public boolean falling = false;
	
	public double xa, ya, za;
	
	private int time = 0;
	private int life = 0;
	
	public Particle(int x, int y, int xa, int ya, int life , Sprite sprite) {
		this.falling = true;
		this.x = x;
		this.y = y;
		
		this.life = life + random.nextInt(50);
		this.sprite = sprite;
		
		this.xa = random.nextGaussian() + xa;
		this.ya = random.nextGaussian() + ya;		
	}
	
	public Particle(int x, int y, int xa, int ya, int life, Entity entity) {
		this.falling = false;
		this.x = x;
		this.y = y;
		
		this.life = life + random.nextInt(50);
		
		Sprite entitySprite = entity.getSprite();
		
		sprite = new Sprite(3, 3, entitySprite.pixels[entitySprite.WIDTH/2 + entitySprite.HEIGHT/2 * entitySprite.WIDTH]);
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian()/2 * xa + ya;		
	}
	
	public Particle(int x, int y, int xa, int ya, int smallest, int biggest,int life, Entity entity) {
		this.falling = false;
		this.x = x;
		this.y = y;
		
		this.life = life + random.nextInt(50);
		
		int size = random.nextInt(biggest - smallest) + smallest;
		
		sprite = new Sprite(size, size, entity.getSprite().pixels[0]);
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian()/2 * xa + ya;		
	}
	
	public void move(double x, double y) {
		if(collision(x, y)) {
			this.xa *= -0.5;
			this.ya *= -0.5;
		}	
		
		this.x += xa;
		this.y += ya;
	}
	
	public void update() {
		
		if(time < 60000) time++;
		else time = 0;
		
		if(time >= life) remove();
		
		if(!falling && ya < 4)ya += gravity;
		
		if(time % 3 == 0 && !falling) {
			ya *= 0.5;
			xa *= 0.5;
		}
		
		move(x, y);
	}
	
	public boolean collision(double x, double y) {
		for(int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 16) / Tile.TILE_SIZE;
			double yt = (y - c / 2 * 16) / Tile.TILE_SIZE;
			int xi = (int)Math.ceil(xt);
			int yi = (int)Math.ceil(yt);
			if(c % 2 == 0) xi = (int)Math.floor(xt);
			if(c / 2 == 0) yi = (int)Math.floor(yt);
			if(level.getTile(xi, yi).solid()) return true;
		}
		return false;
	}
	
	public void render(Render render) {
		render.drawSprite((int)x, (int)y, sprite);
	}
	
}
