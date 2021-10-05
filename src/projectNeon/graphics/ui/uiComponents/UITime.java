package projectNeon.graphics.ui.uiComponents;

import projectNeon.Game;
import projectNeon.utils.Vector2i;

public class UITime extends UILabel {

	private int minutes, seconds;
	private boolean set = false;
	
	public UITime(Vector2i position) {
		super(position);
	}

	public void update() {
		if(!set) {
			seconds = (int)(Game.levels[Game.currentLevel].time/60) % 60;
			minutes = (int)(Game.levels[Game.currentLevel].time/3600) % 60; 
			
			setText(String.valueOf(getTime()));
		}
	}
	
	public String getTime() {
		if(String.valueOf(seconds).length() == 1 && String.valueOf(minutes).length() == 1) return "0" + minutes + ":0" + seconds;
		else if(String.valueOf(minutes).length() == 1 && String.valueOf(seconds).length() == 2) return "0" + minutes + ":" + seconds;
		else if(String.valueOf(minutes).length() == 2 && String.valueOf(seconds).length() == 1) return minutes + ":" + "0" + seconds;
		return minutes + ":" + seconds;
	}
	
	public void setTime(int time) {
		seconds = (time/60) % 60;
		minutes = (time/3600) % 60; 
		
		set = true;
		
		setText(String.valueOf(getTime()));
	}
	
}
