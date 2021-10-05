package projectNeon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import projectNeon.Game;
import projectNeon.graphics.SpriteSheet;

public class LoadLevel extends Level {


	
	public LoadLevel(String path, Game game, Clip clip) {
		super(path, game, clip);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(tiles[x + y * width] == 0xFFFFFF00) {
					for(int yy = y - 1; yy >= 0; yy--) {
						if(tiles[x + yy * width] != 0xFF000000) break;
						tiles[x + yy * width] = 0xFFFFFF00;
					}
				} else if(tiles[x + y * width] == 0xFFFF7F00) {
					for(int yy = y - 1; yy >= 0; yy--) {
						if(tiles[x + yy * width] != 0xFF000000) break;
						tiles[x + yy * width] = 0xFFFF6A00;
					}
				}
			}
		}
		generateLevel();
	}
	
	public void loadLevel(String path) {
		try {
			System.out.print("Trying to load..." + path);
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println("...Succeded");
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, width, height, tiles, 0, width);
		} catch(IOException e) {
			System.out.println("...Failed..." + e.getMessage());
		}
	}

}
