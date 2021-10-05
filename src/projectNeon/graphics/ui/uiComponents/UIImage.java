package projectNeon.graphics.ui.uiComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import projectNeon.Game;
import projectNeon.utils.MathUtils;
import projectNeon.utils.Vector2i;

public class UIImage extends UIComponent {

	protected BufferedImage image;
	public Vector2i size;
	private String path;
	
	public UIImage(Vector2i position, Vector2i size,String path) {
		super(position);
		this.path = path;
		loadImage();
		this.size = size;
	}
	
	public UIImage(Vector2i position, String path) {
		super(position);
		this.path = path;
		loadImage();
	}
	
	private void loadImage() {
		try {
			image = ImageIO.read(UIImage.class.getResource(path));
			size = new Vector2i(image.getWidth(), image.getHeight());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		if(MathUtils.inBounds(x, y, -size.x, Game.getWindowWidth(), -size.y, Game.getWindowHeight())) {
			g.drawImage(image, x, y, size.x, size.y ,null);
		}
	}
	
}
