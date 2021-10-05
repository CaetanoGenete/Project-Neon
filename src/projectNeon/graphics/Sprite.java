package projectNeon.graphics;

public class Sprite {

	private int x, y;
	public final int WIDTH, HEIGHT;
	protected SpriteSheet sheet;
	public int[] pixels;
	
	//Block Tiles:
	
	public static Sprite voidTile = new Sprite(16, 16, 0xFFFF00FF);
	
	public static Sprite blockTL = new Sprite(16, 16, 0, 0, SpriteSheet.tiles);
	public static Sprite blockT = new Sprite(16, 16, 1, 0, SpriteSheet.tiles);
	public static Sprite blockTR = new Sprite(16, 16, 2, 0, SpriteSheet.tiles);
	public static Sprite blockML = new Sprite(16, 16, 0, 1, SpriteSheet.tiles);
	public static Sprite blockM = new Sprite(16, 16, 1, 1, SpriteSheet.tiles);
	public static Sprite blockMR = new Sprite(16, 16, 2, 1, SpriteSheet.tiles);
	public static Sprite blockBL = new Sprite(16, 16, 0, 2, SpriteSheet.tiles);
	public static Sprite blockB = new Sprite(16, 16, 1, 2, SpriteSheet.tiles);
	public static Sprite blockBR = new Sprite(16, 16, 2, 2, SpriteSheet.tiles);
	public static Sprite blockVPipe = new Sprite(16, 16, 0, 3, SpriteSheet.tiles);
	public static Sprite blockHPipe = new Sprite(16, 16, 1, 3, SpriteSheet.tiles);
	public static Sprite block = new Sprite(16, 16, 2, 3, SpriteSheet.tiles);
	public static Sprite blockLPipe = new Sprite(16, 16, 0, 4, SpriteSheet.tiles);
	public static Sprite blockRPipe = new Sprite(16, 16, 1, 4, SpriteSheet.tiles);
	public static Sprite blockTPipe = new Sprite(16, 16, 2, 4, SpriteSheet.tiles);
	public static Sprite blockBPipe = new Sprite(16, 16, 3, 0, SpriteSheet.tiles);
	
	//Deadly Tiles:
	
	public static Sprite spike = new Sprite(16, 16, 3, 3, SpriteSheet.tiles);
	public static Sprite reverseSprite = new Sprite(16, 16, 3, 4, SpriteSheet.tiles);
		
	//Backgrounds:
	
	public static Sprite background = new Sprite(360, 203, 0, 0, SpriteSheet.background);
	public static Sprite backgroundResized = Sprite.resizeSprite(background, 2, 2);
	
	//Entities@
	
	public static Sprite enemy = new Sprite(16, 16, 0xFF0000);
	
	//Miscellaneous Tiles:
	
	public static Sprite chainStart = new Sprite(16, 16, 3, 2, SpriteSheet.tiles);
	public static Sprite chain = new Sprite(16, 16, 3, 1, SpriteSheet.tiles);
	
	public static Sprite portalTL = new Sprite(16, 16, 4, 0, SpriteSheet.tiles);
	public static Sprite portalTR = new Sprite(16, 16, 5, 0, SpriteSheet.tiles);
	public static Sprite portalML = new Sprite(16, 16, 4, 1, SpriteSheet.tiles);
	public static Sprite portalMR = new Sprite(16, 16, 5, 1, SpriteSheet.tiles);
	public static Sprite portalBL = new Sprite(16, 16, 4, 2, SpriteSheet.tiles);
	public static Sprite portalBR = new Sprite(16, 16, 5, 2, SpriteSheet.tiles);
	
	public static Sprite finish = new Sprite(96,96, 0, 0, SpriteSheet.finish);

	
	public Sprite(SpriteSheet sheet, int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		pixels = new int[width * height];
		this.sheet = sheet;
	}
	
	public Sprite(int WIDTH, int HEIGHT, int x, int y, SpriteSheet sheet) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.x = x * WIDTH;
		this.y = y * HEIGHT;
		pixels = new int[WIDTH * HEIGHT];
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int WIDTH, int HEIGHT, int colour) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		pixels = new int[WIDTH * HEIGHT];
		setColour(colour);
	}
	
	public Sprite(int[] spritePixels, int WIDTH, int HEIGHT) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.pixels = spritePixels;
	}
	
	public static Sprite resizeSprite(Sprite sprite, int WIDTH, int HEIGHT) {
		
		int newWidth = sprite.WIDTH * WIDTH;
		int newHeight = sprite.HEIGHT * HEIGHT;
		
		int[] pixels = new int[newWidth * newHeight];
		
		for(int y = 0; y < sprite.HEIGHT; y++) {
			for(int x = 0; x < sprite.WIDTH; x++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					for(int xx = 0; xx < WIDTH; xx++) {
						pixels[(x * WIDTH + xx) + (y * HEIGHT + yy) * newWidth] = sprite.pixels[x + y * sprite.WIDTH];
					}
				}
			}
		}
		
		return new Sprite(pixels, newWidth, newHeight);
		
	}
	
	public static Sprite rotateSprite(Sprite sprite, double angle) {
		return new Sprite(Sprite.rotate(sprite.pixels, sprite.WIDTH, sprite.HEIGHT, angle), sprite.WIDTH, sprite.HEIGHT);
	}
	
	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		
		double nx_x = rotX(-angle, 1.0 , 0.0);
		double nx_y = rotY(-angle, 1.0 , 0.0);
		double ny_x = rotX(-angle, 0.0 , 1.0);
		double ny_y = rotY(-angle, 0.0 , 1.0);
		
		double x0 = rotX(-angle, -width/2.0, -height/2.0) + width/2.0;
		double y0 = rotY(-angle, -width/2.0, -height/2.0) + height/2.0;
		
		for(int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for(int x = 0; x < width; x++) {
				int xx = (int)x1;
				int yy = (int)y1;
				int col = 0;
				if(xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xFFFF00FF;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}
		
		return result;
	}
	
	private static double rotX(double angle, double x, double y) {
		double sin = Math.sin(angle + Math.PI);
		double cos = Math.cos(angle + Math.PI);
		return x * cos + y * -sin;
	}
	
	private static double rotY(double angle, double x, double y) {
		double sin = Math.sin(angle + Math.PI);
		double cos = Math.cos(angle + Math.PI);
		return x * sin + y * cos;
	}
	
	private void setColour(int colour) {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = colour;
		}
	}
	
	private void load() {
		for(int y = 0; y < HEIGHT; y++) {
			for(int x = 0; x < WIDTH; x++) {
				pixels[x + y * WIDTH] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.WIDTH];
			}
		}
	}
	
}
