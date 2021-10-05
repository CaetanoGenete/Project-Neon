package projectNeon.graphics;

import projectNeon.entities.mobs.Mob;
import projectNeon.level.Level;
import projectNeon.level.tiles.Tile;
import projectNeon.level.tiles.WallTile;
import projectNeon.utils.ColourUtils;

public class Render {

	public int width, height;
	private int xOffset, yOffset;
	public int[] pixels;
	
	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void drawTile(int xp, int yp, Tile tile, Level level) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < tile.sprite.HEIGHT; y++) {
			int ya = y + yp;
			for(int x = 0; x < tile.sprite.WIDTH; x++) {
				int xa = x + xp;
				if(xa < -tile.sprite.WIDTH || ya < 0 || xa >= width || ya >= height) break;
				if(xa < 0) xa = 0;
				int col = tile.sprite.pixels[x + y * tile.sprite.WIDTH];
				if(col == 0xFFAAAAAA) col = level.getColour();
				if(tile instanceof WallTile) {
					if(col == 0xFFBBBBBB) col = ColourUtils.shadeColour(level.getColour(), 70);
					if(col == 0xFFCCCCCC) col = ColourUtils.shadeColour(level.getColour(), 60);
				} else {
					if(col == 0xFFBBBBBB) col = ColourUtils.shadeColour(level.getColour(), 90);
					if(col == 0xFFCCCCCC) col = ColourUtils.shadeColour(level.getColour(), 80);
				}
				if(col != 0xFFFF00FF)pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void drawSprite(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < sprite.HEIGHT; y++) {
			int ya = y + yp;
			for(int x = 0; x < sprite.WIDTH; x++) {
				int xa = x + xp;
				if(xa < -sprite.WIDTH || ya < 0 || xa >= width || ya >= height) continue;
				if(xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sprite.WIDTH];
				if(col != 0xFFFF00FF)  pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void drawMob(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y = 0; y < mob.getSprite().HEIGHT; y++) {
			int ya = y + yp;
			for(int x = 0; x <  mob.getSprite().WIDTH; x++) {
				int xa = x + xp;
				if(xa < -mob.getSprite().WIDTH || ya < 0 || xa >= width || ya >= height) continue;
				if(xa < 0) xa = 0;
				int col = mob.getSprite().pixels[x + y *  mob.getSprite().WIDTH];
				if(col != 0xFFFF00FF) pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void drawBackground(Sprite sprite, Level level) {
		for(int y = 0; y < height; y++) {
			int ya = y;
			for(int x = 0; x < width; x++) {
				int xa = x;
				if(xa < 0 || ya < 0 || xa >= width || ya >= height) continue;
				if(xa < 0) xa = 0;
				int col = sprite.pixels[((x + (xOffset/2)) % sprite.WIDTH) + ((y + (yOffset/2)) % sprite.HEIGHT) * sprite.WIDTH];
				if(col == 0xFFAAAAAA) col = level.getColour();
				if(col == 0xFFBBBBBB) col = ColourUtils.shadeColour(level.getColour(), 200);
				pixels[x + y * width] = col;
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
}
