package projectNeon.level.tiles;

import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.level.Level;

public class SpikeTile extends Tile {

	public SpikeTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Render render, Level level) {
		if(level.getTile(x, y - 1) == wall) render.drawTile(x << TILE_MODIFIER, y << TILE_MODIFIER, level.getTile(x, y - 1), level);
		render.drawTile(x << TILE_MODIFIER, y << TILE_MODIFIER, this, level);
	}
	
	public boolean deadly() {
		return true;
	}
	

}
