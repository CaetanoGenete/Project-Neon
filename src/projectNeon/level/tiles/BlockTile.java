package projectNeon.level.tiles;

import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.level.Level;

public class BlockTile extends Tile {

	public BlockTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Render render, Level level) {
		render.drawTile(x << TILE_MODIFIER, y << TILE_MODIFIER, this, level);
	}
	
	public boolean solid() {
		return true;
	}
	
}
