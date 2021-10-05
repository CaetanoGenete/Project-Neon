package projectNeon.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.Clip;

import projectNeon.Game;
import projectNeon.entities.Entity;
import projectNeon.entities.miscellaneous.Finish;
import projectNeon.entities.mobs.Enemy;
import projectNeon.entities.mobs.FlyingEnemy;
import projectNeon.entities.mobs.Player;
import projectNeon.entities.particle.Particle;
import projectNeon.entities.spawners.ParticleSpawner;
import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.level.tiles.Tile;

public class Level {
	
	protected int width, height;
	protected int goalX, goalY;
	public int spawnX, spawnY;
	protected int[] tiles;
	
	private int xScroll, yScroll;
	private int colour = 0x000000;
	private int colType = 0;
	private int deaths = 0;
	private int score = 0;
	
	public float progress = 0;

	public long time = 0;
	public int topScore = 0;
	public int topTime = 0;
	
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Particle> particles = new ArrayList<Particle>();
	
	private Random random = new Random();
	private Player player;
	
	public Game game;
	public Clip clip;
	
	public boolean finished = false;
	
	public Level(String path, Game game, Clip clip) {
		loadLevel(path);
		
		this.game = game;
		this.clip = clip;
		
		colour = random.nextInt(0xFFFFFF);
	}
	
	protected void loadLevel(String path) {
	
	}
	
	public void generateLevel() {
		for(int y = 0; y < height; y++) {
		    for(int x = 0; x < width; x++) {
				if(tiles[x + y * width] == 0xFF0000FF) {
					spawnX = x;
					spawnY = y;
					add(new Player(x, y, Game.input));
				} else if(tiles[x + y * width] == 0xFFFF0000) {
					add(new Enemy(x, y));
				} else if(tiles[x + y * width] == 0xFF7F00FF) {
					add(new Finish(x, y));
					goalX = x;
					goalY = y;
				} else if(tiles[x + y * width] == 1 || tiles[x + y * width] == 2) {
					tiles[x + y * width] = 0xFFFFD800;
				} else if(tiles[x + y * width] == 0xFFFF0001) {
					add(new FlyingEnemy(x, y));
				}
			}
		}
	}
	
	public void update() {
		
		player.update();
		time();
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		
		Tile.lavaMoving.setFrameRate(30);
		Tile.lavaStatic.setFrameRate(30);
		Tile.checkpoint.setFrameRate(3);
		Tile.coins.setFrameRate(3);
		Tile.checkpoint.update();
		Tile.lavaMoving.update();
		Tile.lavaStatic.update();
		Tile.coins.update();
		
		progress = (float)player.getX()/(float)(goalX << Tile.TILE_MODIFIER);
		Game.ui.progress.setPogress(progress);
		
		remove();
	}
	
	public void remove() {
		if(player.isRemoved()) {
			add(new Player(spawnX, spawnY, Game.input));
			entities.clear();
			deaths++;
			
			for(int y = 0; y < height; y++) {
				for(int x = spawnX; x < width; x++) {
					if(tiles[x + y * width] == 0xFFFF0000) add(new Enemy(x, y));
					else if(tiles[x + y * width] == 0xFFFF0001) add(new FlyingEnemy(x, y));
					else if(tiles[x + y * width] == 0xFF7F00FF) add(new Finish(x, y));
				}
			}
			
			Game.player = player;
		}
		
		for(int i = 0; i < entities.size(); i++) {
			if(entities.get(i).isRemoved()) {
				entities.remove(i);
			}
		}
		
		for(int i = 0; i < particles.size(); i++) {
			if(particles.get(i).isRemoved()) {
				particles.remove(i);
			}
		}
		
	}
	
	private void time() {
		time++;

		if(time % 600 == 0) {
			colType = random.nextInt(3);
		}
		
		if(time % 6 == 0) {
			if(colType == 0) {
				colour += 0x010000;
			} else if(colType == 1) {
				colour += 0x000100;
			} else if(colType == 2) {
				colour += 0x000001;
			}
		}
	}
	
	public void restart() {
		entities.clear();
		particles.clear();
		player.death();
		generateLevel();
		
		Game.ui.deathsLabel.setText("0");
		Game.ui.scoreLabel.setText("0");
		
		Game.player = player;
		
		clip.setFramePosition(0);
		
		finished = false;
		
		score = 0;
		deaths = 0;
		time = 0;
	}
	
	public void load() {
		for(int y = 0; y < height; y++) { 
			for(int x = 0; x < (int)player.getX() >> Tile.TILE_MODIFIER; x++) {
				if(tiles[x + y * width] == 0xFFFFD800) removeTile(x, y);
			}
		}
	}
	
	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		for(int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if(entity instanceof ParticleSpawner) continue; 
			int x = (int)entity.getX();
			int y = (int)entity.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt(dx * dx + dy * dy);
			if(distance <= radius) result.add(entity);
		}
		return result;
	}
	
	public Entity EntityCollision(int x, int y, Entity e) {
		List<Entity> entities = getEntities(e, e.getSprite().WIDTH);
		entities.add(player);
		entities.remove(e);
		for(int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int sx = entity.getSprite().WIDTH;
			int sy = entity.getSprite().HEIGHT;
			int ex = (int)entity.getX();
			int ey = (int)entity.getY();
			for(int c = 0; c < 4; c++) {
				int xt = (x + c % 2 * sx);
				int yt = (y + c / 2 * sy);
				if(xt >= ex && xt <= ex + sx && yt >= ey && yt <= ey + sy) return entity;
			}
		}
		return null;
	}
	
	public void setScroll(int xScroll, int yScroll) {
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}
	
	public void render(Render render) {
		
		render.setOffset(xScroll, yScroll);
		
		int x0 = xScroll >> Tile.TILE_MODIFIER;
		int x1 = (xScroll + render.width + Tile.TILE_SIZE) >> Tile.TILE_MODIFIER;
		int y0 = yScroll >> Tile.TILE_MODIFIER;
		int y1 = (yScroll + render.height + Tile.TILE_SIZE) >> Tile.TILE_MODIFIER;
		
		render.drawBackground(Sprite.backgroundResized, this);
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, render, this);
			}
		}
		
		player.render(render);
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render(render);
		}
		
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(render);
		}
		
	}
	
	public void add(Entity e) {
		e.init(this);
		if(e instanceof Player) {
			this.player = (Player)e;
			Game.player = player;
		} else if(e instanceof Particle) {
			particles.add((Particle)e);
		} else {
			entities.add(e);
		}
	}
	
	public void removeTile(int x, int y) {
		if(getTile(x, y - 1) == Tile.wall) tiles[x + y * width] = 2;
		else {
			tiles[x + y * width] = 1;
			score++;
		}
	}
	
	public Player getPlayer(int x, int y, int radius) {
		int xx = (int)player.getX();
		int yy = (int)player.getY();
		int dx = x - xx;
		int dy = y - yy;
		double dist = Math.sqrt(dx*dx+dy*dy);
		if(dist < radius) return player;
		else return null;
	}
	
	public boolean playerCollision(int x, int y, int width, int height) {
		for(int c = 0; c < 4; c++) {
			int xt = (int)(player.getX() + c % 2 * 15);
			int yt = (int)(player.getY() + c / 2 * 15);
			if(xt >= x && yt >= y && xt < x + width && yt < y + height) return true; 
		}
		return false;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public Tile getTile(int x, int y) {	
		if(x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if(tiles[x + y * width] == 0xFFAAAAAA) {
			
			boolean north = false, south = false, west = false, east = false;
			
			if(y - 1 >= 0) north = tiles[x + (y - 1) * width] == 0xFFAAAAAA;
			if(y + 1 < height) south = tiles[x + (y + 1) * width] == 0xFFAAAAAA;
			if(x - 1 >= 0) west = tiles[(x - 1) + y * width] == 0xFFAAAAAA;
			if(x + 1 < width) east = tiles[(x + 1) + y * width] == 0xFFAAAAAA;
			
			if(!north && !south && !east && !west) return Tile.block;
			
			if(!north && south && east && !west) return Tile.blockTL;
			else if(!north && south && east && west) return Tile.blockT;
			else if(!north && south && !east && west) return Tile.blockTR;
			else if(north && south && east && !west) return Tile.blockML;
			else if(north && south && east && west) return Tile.blockM;
			else if(north && south && !east && west) return Tile.blockMR;
			else if(north && !south && east && !west) return Tile.blockBL;
			else if(north && !south && east && west) return Tile.blockB;
			else if(north && !south && !east && west) return Tile.blockBR;
			else if(north && south && !east && !west) return Tile.blockVPipe;
			else if(!north && !south && east && west) return Tile.blockHPipe;
			else if(!north && !south && east && !west) return Tile.blockLPipe;
			else if(!north && !south && !east && west) return Tile.blockRPipe;
			else if(!north && south && !east && !west) return Tile.blockTPipe;
			else if(north && !south && !east && !west) return Tile.blockBpipe;

			return Tile.blockM;
		} else if(tiles[x + y * width] == 0xFFBBBBBB || tiles[x + y * width] == 2) {
			return Tile.wall;
		} else if(tiles[x + y * width] == 0xFF7F0000) {
			if(y - 1 < 0) return Tile.lavaStaticTile;
			
			if(tiles[x + (y - 1) * width] != 0xFF7F0000) {
				return Tile.lavaMovingTile;
			} else {
				return Tile.lavaStaticTile;
			}
			
		} else if(tiles[x + y * width] == 0xFFFFFF00) {
			if(y + 1 > height) return Tile.chainTile;
			else if(tiles[x + (y + 1) * width] == 0xFFFFFF00) {
				return Tile.chainTile;
			} else {
				return Tile.chainStartTile;
			}
		} else if(tiles[x + y * width] == 0xFF00FF00) {
			
			boolean south = false, north = false, east = false, west = false;
			
			if(y - 1 >= 0) north = tiles[x + (y - 1) * width] == 0xFF00FF00;
			if(y + 1 < height) south = tiles[x + (y + 1) * width] == 0xFF00FF00;
			west = tiles[(x - 1) + y * width] == 0xFF00FF00;
			east = tiles[(x + 1) + y * width] == 0xFF00FF00;
			
			if(!west && !north && east && south) return Tile.portalEnterTLTile;
			if(west && !north && !east && south) return Tile.portalEnterTRTile;
			if(!west && north && east && south) return Tile.portalEnterMLTile;
			if(west && north && !east && south) return Tile.portalEnterMRTile;
			if(!west && north && east && !south) return Tile.portalEnterBLTile;
			if(west && north && !east && !south) return Tile.portalEnterBRTile;
		} else if(tiles[x + y * width] == 0xFF4CFF00) {
			
			boolean south = false, north = false, east = false, west = false;
			
			if(y - 1 >= 0) north = tiles[x + (y - 1) * width] == 0xFF4CFF00;
			if(y + 1 < height) south = tiles[x + (y + 1) * width] == 0xFF4CFF00;
			west = tiles[(x - 1) + y * width] == 0xFF4CFF00;
			east = tiles[(x + 1) + y * width] == 0xFF4CFF00;
			
			if(!west && !north && east && south) return Tile.portalExitTLTile;
			if(west && !north && !east && south) return Tile.portalExitTRTile;
			if(!west && north && east && south) return Tile.portalExitMLTile;
			if(west && north && !east && south) return Tile.portalExitMRTile;
			if(!west && north && east && !south) return Tile.portalExitBLTile;
			if(west && north && !east && !south) return Tile.portalExitBRTile;
		} else if(tiles[x + y * width] == 0xFFFFD800) { 	
			return Tile.coinTile;
		} else if(tiles[x + y * width] == 0xFF7F7F7F) {
			if(tiles[x + (y + 1) * width] == 0xFFAAAAAA) return Tile.spikeTile;
			if(y - 1 >= 0) {
				if(tiles[x + (y - 1) * width] == 0xFFAAAAAA) {
					return Tile.spikeReverseTile;
				} 
			}
			return Tile.spikeTile;
		} else if(tiles[x + y * width] == 0xFFFF7F00) {
			return Tile.checkPointTile;
		} else if(tiles[x + y * width] == 0xFFFF6A00) {
			return Tile.checkPointTile2;
		}
		return Tile.voidTile;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getColour() {
		return colour;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
