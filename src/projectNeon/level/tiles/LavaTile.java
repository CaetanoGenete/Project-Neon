package projectNeon.level.tiles;

import projectNeon.graphics.AnimatedSprite;
import projectNeon.graphics.Render;
import projectNeon.level.Level;

public class LavaTile extends Tile {

	public LavaTile(AnimatedSprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Render render, Level level) {
		sprite = animSprite.getSprite();
		if(level.getTile(x, y - 1) == wall)render.drawTile(x << TILE_MODIFIER, y << TILE_MODIFIER, level.getTile(x, y - 1), level);
		render.drawTile(x << TILE_MODIFIER, y << TILE_MODIFIER, this, level);
	}
	
	public boolean deadly() {
		return true;
	}
	
}
