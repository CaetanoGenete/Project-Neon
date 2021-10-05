package projectNeon.graphics;

public class AnimatedSprite extends Sprite {

	private int time = 0;
	private Sprite sprite;
	private int frames = 0;
	private int rate = 6;
	private int length = 0;
	
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length;
		if(length > sheet.getSprites().length) System.err.println("Animation too long");
		sprite = sheet.getSprites()[0];
	}

	public void update() {
		if(time < 60000) time++;
		else time = 0;
		if(time % rate == 0) {
			if(frames < length - 1) frames++;
			else frames = 0;
			sprite = sheet.getSprites()[frames];
		}
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setFrameRate(int rate) {
		this.rate = rate;
	}
	
	public void setFrame(int frame) {
		if(frame < length - 1) {
			return;
		}
		sprite = sheet.getSprites()[frame];
	}
	
}
