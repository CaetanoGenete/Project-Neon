package projectNeon.graphics.ui.uiComponents;

import projectNeon.utils.Vector2i;

public class UIScore extends UILabel{
	
	private double currentScore = 0;
	private double score = 0;
	private double increment = 0;
	
	public UIScore(Vector2i position) {
		super(position);
		
		text = "0";
		
		increment = score/180;
	}
	
	public void update() {
		if(score != 0)  {
			if(currentScore < score)currentScore += increment;
			else currentScore = score;

		}
		text = String.valueOf((int)currentScore);
		
	}
	
	public void setText(String text) {
		this.score = Integer.parseInt(text);
		
		increment = Double.valueOf(text)/60;
	}
	
}
