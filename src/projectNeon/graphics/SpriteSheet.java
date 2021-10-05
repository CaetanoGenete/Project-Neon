package projectNeon.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	public final int WIDTH, HEIGHT;
	public int[] pixels;
	private String path;
	
	//Tiles:
	
	public static SpriteSheet tiles = new SpriteSheet(112, 80, "/SpriteSheets/Tiles/Tiles.png");
	
	public static SpriteSheet animatedTiles = new SpriteSheet(128, 80, "/SpriteSheets/Tiles/AnimatedTiles/AnimatedTiles.png");
	public static SpriteSheet lavaMovingTiles = new SpriteSheet(animatedTiles, 1, 5, 0, 0, 16);
	public static SpriteSheet lavaStaticTiles = new SpriteSheet(animatedTiles, 1, 5, 1, 0, 16);
	public static SpriteSheet checkPointTiles = new SpriteSheet(animatedTiles, 2, 5, 2, 0, 16);
	public static SpriteSheet coinTiles = new SpriteSheet(animatedTiles, 4, 5, 4, 0, 16);
	
	//Sprites:
	
	public static SpriteSheet animatedSprites = new SpriteSheet(64, 64, "/SpriteSheets/Sprites/AnimatedSprites.png");
	public static SpriteSheet player1 = new SpriteSheet(animatedSprites, 2, 4, 0, 0, 16);
	
	//Backgrounds:
	
	public static SpriteSheet background = new SpriteSheet(360, 203, "/backgrounds/Background.png");
	
	//Miscellaneous:
	
	public static SpriteSheet finish = new SpriteSheet(96, 96, "/SpriteSheets/Sprites/End.png");
	
	private Sprite[] sprites;
	
	public SpriteSheet(SpriteSheet sheet, int width, int height, int x, int y, int spriteSize) {
		int w = width * spriteSize;
		int h = height * spriteSize;
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		WIDTH = w;
		HEIGHT = h;
		pixels = new int[w * h];
		
		for(int ya = 0; ya < h; ya++) {
			int yp = yy + ya;
			for(int xa = 0; xa < w; xa++) {
				int xp = xx + xa;
				pixels[xa + ya * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}
		
		int frames = 0;
		sprites = new Sprite[width * height];
		for(int xa = 0; xa < width; xa++) {
			for(int ya = 0; ya < height; ya++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for(int y0 = 0; y0 < spriteSize; y0++) {
					for(int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * w];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frames++] = sprite;
			}
		}
		
	}
	
	public SpriteSheet(int WIDTH, int HEIGHT, String path) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		pixels = new int[WIDTH * HEIGHT];
		this.path = path;
		load();
	}
	
	private void load() {
		try {
			System.out.print("Trying to load..." + path);
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println("...Succeded");
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch(IOException e) {
			System.out.println("...Failed..." + e.getMessage());
		}
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
}
