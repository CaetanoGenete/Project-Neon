package projectNeon.entities.mobs;

import static projectNeon.utils.MathUtils.abs;
import static projectNeon.utils.MathUtils.inBounds;
import static projectNeon.utils.MathUtils.toPixels;
import static projectNeon.utils.MathUtils.toTiles;

import projectNeon.Game;
import projectNeon.entities.Entity;
import projectNeon.entities.spawners.ParticleSpawner;
import projectNeon.events.types.PlayerScoredEvent;
import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.level.Level;
import projectNeon.level.tiles.CheckpointTile;
import projectNeon.level.tiles.CoinTile;
import projectNeon.level.tiles.PortalEnterTile;
import projectNeon.level.tiles.PortalExitTile;
import projectNeon.level.tiles.Tile;

public abstract class Mob extends Entity {

	protected boolean walking = false;
	protected boolean moving = false;
	
	protected Game game;
	
	public enum State {
		FLYING, NORMAL, REVERSE
	}
	
	public State state = State.NORMAL;
	
	public Mob(int x, int y) {
		this.x = x << Tile.TILE_MODIFIER;
		this.y = y << Tile.TILE_MODIFIER;
	}
	
	public void move(double xa, double ya) {
		
		if(xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		while(xa != 0) {
			if(Math.abs(xa) > 1) {
				if(!collision(abs(xa), ya) && inBounds((int)(x + xa), 0, toPixels(level.getWidth()) - sprite.WIDTH)) {
					Entity collision = level.EntityCollision((int)x + abs(xa),(int)(y + ya), this);
					if(collision != null) collisionX(collision);
					else x += abs(xa); 
				}
				xa -= abs(xa);
			} else {
				if(!collision(abs(xa), ya) && (x + xa) > 0 && (x + xa) <= toPixels(level.getWidth()) - sprite.WIDTH) {
					Entity collision = level.EntityCollision((int)x + abs(xa),(int)(y + ya), this);
					if(collision != null) collisionX(collision);
					else x += xa;
				}
				xa = 0;
			}
		}
		
		while(ya != 0) {
			if(Math.abs(ya) > 1) {
				if(!collision(xa, abs(ya)) && (y + ya) > 0 && (y + ya) <= toPixels(level.getHeight()) - sprite.HEIGHT) {
					Entity collision = level.EntityCollision((int)(x + xa),(int)y + abs(ya), this);
					if(collision != null) collisionY(collision);
					else y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if(!collision(xa, abs(ya)) && (y + ya) > 0 && (y + ya) <= toPixels(level.getHeight()) - sprite.HEIGHT) {
					Entity collision = level.EntityCollision((int)(x + xa),(int)y + abs(ya), this);
					if(collision != null) collisionY(collision);
					else y += ya;
				}
				ya = 0;
			}
		}
	
	}
	
	public void collisionX(Entity entity) {
	}
	
	public void collisionY(Entity entity) {
		
	}
	
	public void death() {
		
	}
	
	public void init(Level level) {
		super.init(level);
		
		this.game = level.game;
	}
	
	public abstract void update();
	
	public abstract void render(Render render);
	
	public boolean collision(double xa, double ya) {
		boolean solid = false;
		boolean deadly = false;
		for(int c = 0; c < 4; c++) {
			int xt = ((int)(x + xa) + c % 2 * 15) >> Tile.TILE_MODIFIER;
			int yt = ((int)(y + ya) + c / 2 * 15) >> Tile.TILE_MODIFIER;
			if(level.getTile(xt, yt).solid()) solid = true;
			if(level.getTile(xt, yt).deadly()) deadly = true;
			if(level.getTile(xt, yt) instanceof PortalEnterTile) state = State.FLYING;
			if(level.getTile(xt, yt) instanceof PortalExitTile) state = State.NORMAL;
			if(level.getTile(xt, yt) instanceof CoinTile && this instanceof Player) {
				PlayerScoredEvent event = new PlayerScoredEvent();
				game.onEvent(event);
				
				level.removeTile(xt, yt);
				level.add(new ParticleSpawner(toPixels(xt), toPixels(yt), 0, 0, 20, new Sprite(3, 3, 0xFFFFFF00), 30, level));
			}
			if(level.getTile(xt, yt) instanceof CheckpointTile) {
				level.spawnX = toTiles((int)x);
				level.spawnY = toTiles((int)y);
			}
		}
		if(deadly && !solid) death();
		return solid;
	}
	
}
