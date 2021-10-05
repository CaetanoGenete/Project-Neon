package projectNeon.level.tiles;

import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.level.Level;

public class WallTile extends Tile {

	public WallTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Render render, Level level) {
		render.drawTile(x << Tile.TILE_MODIFIER, y << Tile.TILE_MODIFIER, this, level);
	}

}
