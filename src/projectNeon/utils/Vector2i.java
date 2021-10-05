package projectNeon.utils;

public class Vector2i {

	public int x, y;
	
	public Vector2i() {
		set(0, 0);
	}
	
	public Vector2i(int x, int y) {
		set(x, y);
	}
	
	public Vector2i(Vector2i vec) {
		set(vec.getX(), vec.getY());
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2i add(Vector2i vec) {
		this.x += vec.getX();
		this.y += vec.getY();
		return this;
	}
	
	public Vector2i subtract(Vector2i vec) {
		this.x -= vec.getX();
		this.y -= vec.getY();
		return this;
	}
	
	public Vector2i add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) obj;
		if(vec.getX() == x && vec.getY() == y) return true;
		return false;
	}
	
}
