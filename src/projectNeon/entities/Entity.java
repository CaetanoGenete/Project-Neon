package projectNeon.entities;

import java.util.Random;

import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.level.Level;

public abstract class Entity {

	protected double x, y;
	protected boolean removed = false;
	protected Sprite sprite;
	protected Level level;
	protected Random random = new Random();
	protected double gravity = 0.2;

	
	public void update() {
		
	}
	
	public void render(Render render) {
		
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void init(Level level) {
		this.level = level;
	}
	
}
