package projectNeon.utils;

public class StringUtils {

	public static String to24HourTime(int time) {
		int seconds = (time/60) % 60;
		int minutes = (time/3600) % 60; 
		if(String.valueOf(seconds).length() == 1 && String.valueOf(minutes).length() == 1) return "0" + minutes + ":0" + seconds;
		else if(String.valueOf(minutes).length() == 1 && String.valueOf(seconds).length() == 2) return "0" + minutes + ":" + seconds;
		else if(String.valueOf(minutes).length() == 2 && String.valueOf(seconds).length() == 1) return minutes + ":" + "0" + seconds;
		return minutes + ":" + seconds;
	}
	
}
