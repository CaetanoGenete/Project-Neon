package projectNeon.level.tiles;

import projectNeon.graphics.AnimatedSprite;
import projectNeon.graphics.Render;
import projectNeon.level.Level;

public class CheckpointTile extends Tile {

	public CheckpointTile(AnimatedSprite animSprite) {
		super(animSprite);
	}
	
	public void render(int x, int y, Render render, Level level) {
		if(animSprite != null) {
			sprite = animSprite.getSprite();
			if(level.getTile(x, y - 1) == Tile.wall) render.drawTile(x << TILE_MODIFIER, y << TILE_MODIFIER, level.getTile(x, y - 1), level);
			render.drawTile(x << TILE_MODIFIER, y << TILE_MODIFIER, this, level);
		}
	}
	
}
