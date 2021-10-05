package projectNeon.graphics.ui.uiComponents;

import java.awt.Graphics;

import projectNeon.Game;
import projectNeon.utils.MathUtils;
import projectNeon.utils.Vector2i;

public class UIRectangle extends UIComponent{

	private Vector2i size;
	
	public UIRectangle(Vector2i position, Vector2i size) {
		super(position);
		this.size = size;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		if(MathUtils.inBounds(x, y, -size.x, Game.getWindowWidth(), -size.y, Game.getWindowHeight()))
			g.fillRect(x, y, size.x, size.y);
	}

}
