package projectNeon.graphics.ui.uiComponents;

import java.awt.Graphics;

import projectNeon.Game;
import projectNeon.utils.MathUtils;
import projectNeon.utils.Vector2i;

public class UICyclingImage extends UIImage {

	private Vector2i imagePosition;
	private int speed;
	
	public UICyclingImage(Vector2i position, Vector2i size, String path) {
		super(position, size, path);
		
		imagePosition = new Vector2i(position).add(image.getWidth(), 0);
		
		speed = 1;
		
	}
	
	public UICyclingImage(Vector2i position, String path) {
		super(position, path);
		
		imagePosition = new Vector2i(position).add(image.getWidth(), 0);
		
		speed = 1;
	}

	public void update() {
		if(position.getX() > Game.getWindowWidth()) position.x = -image.getWidth();
		else position.add(speed, 0);
		if(imagePosition.getX() > Game.getWindowWidth()) imagePosition.x = -image.getWidth();
		else imagePosition.add(speed, 0);
		
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void render(Graphics g) {
		super.render(g);
		
		int x = imagePosition.x + offset.x + (speed * 2);
		int y = imagePosition.y + offset.y;
		if(MathUtils.inBounds(x, y, -size.x, Game.getWindowWidth(), -size.y, Game.getWindowHeight())) {
			g.drawImage(image, x, y ,null);
		}
	}
	
}
