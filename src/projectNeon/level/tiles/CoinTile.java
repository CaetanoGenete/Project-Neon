package projectNeon.level.tiles;

import projectNeon.graphics.AnimatedSprite;
import projectNeon.graphics.Render;
import projectNeon.level.Level;

public class CoinTile extends Tile {

	public CoinTile(AnimatedSprite animSprite) {
		super(animSprite);
	}
	
	public void render(int x, int y, Render render, Level level) {
		sprite = animSprite.getSprite();
		if(level.getTile(x, y - 1) == Tile.wall) render.drawTile(x << Tile.TILE_MODIFIER, y << Tile.TILE_MODIFIER, level.getTile(x, y - 1), level);
		render.drawTile(x << Tile.TILE_MODIFIER, y << Tile.TILE_MODIFIER, this, level);
	}

}
