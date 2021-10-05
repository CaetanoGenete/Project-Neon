package projectNeon.utils;

public class ColourUtils {

	public static int shadeColour(int colour, int light) {

		if(light < 0) light = 0;
		
		int r = (colour >> 16) & 0xFF;
		int g = (colour >> 8) & 0xFF;
		int b = (colour) & 0xFF;
		
		r = r * light/100;
		g = g * light/100;
		b = b * light/100;
		
		if(r > 255) r = 255;
		if(g > 255) g = 255;
		if(b > 255) b = 255;
		
		return r << 16 | g << 8 | b;
	}
	
}
