package projectNeon.utils;

import projectNeon.level.tiles.Tile;

public class MathUtils {

	public static int abs(double value) {
		if(value < 0) return -1;
		if(value > 0) return 1;
		return 0;
	}
	
	public static boolean inBounds(int pos, int lowerBounds, int upperBounds) {
		if(pos >= lowerBounds && pos < upperBounds) return true;
		else return false;
	}
	
	public static boolean inBounds(int posX, int posY, int lowerBoundsX, int upperBoundsX, int lowerBoundsY, int upperBoundsY) {
		if(posX >= lowerBoundsX && posY >= lowerBoundsY && posX < upperBoundsX && posY < upperBoundsY) return true;
		else return false;
	}
	
	public static int min(int value, int min) {
		return value < min ? min : value;
	}
	
	public static int max(int value, int max) {
		return value > max ? max : value;
	}
	
	public static int clamp(int value, int min, int max) {
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	
	public static int toPixels(int tilePos) {
		return tilePos << Tile.TILE_MODIFIER;
	}
	
	public static int toTiles(int pixelPos) {
		return pixelPos >> Tile.TILE_MODIFIER;
	}
	
}
