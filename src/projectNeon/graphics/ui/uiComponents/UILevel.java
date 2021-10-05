package projectNeon.graphics.ui.uiComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import projectNeon.Game;
import projectNeon.utils.StringUtils;
import projectNeon.utils.Vector2i;

public class UILevel extends UIComponent {

	public List<UIImage> levels = new ArrayList<UIImage>();
	public List<UIImage> block = new ArrayList<UIImage>();
	public List<UILabel> time = new ArrayList<UILabel>();
	public List<UILabel> score = new ArrayList<UILabel>();
	
	public int previousLevel = 0;
	
	public boolean moved = false;
	
	public UILevel(Vector2i position) {
		super(position);
	}
	
	public void update() {
		if(moved) {
			
			UIImage current = levels.get(Game.currentLevel);
			
			if(current.position.x != position.x) {
				int increment = Game.currentLevel - previousLevel;
				for(int i = 0; i < levels.size(); i++) {
					levels.get(i).position.x -= increment * 20;
					block.get(i).position.x -= increment * 20;
					time.get(i).position.x -= increment * 20;
					score.get(i).position.x -= increment * 20;
					
				}
			} else {
				moved = false;
			}
		}
		
	}
	
	public void render(Graphics g) {
		
		for(int i = 0; i < levels.size(); i++) {
			UIImage image = levels.get(i);
			
			g.setColor(Color.green);
			
			if(image.position.x - image.size.x > 0 || image.position.x < Game.getWindowWidth()) g.fillRect(image.position.x - 25, image.position.y - 20, 1050, 591);
			
			image.render(g);
			score.get(i).render(g);
			time.get(i).render(g);
			if(i > Game.access)block.get(i).render(g);
		}
	}
	
	public void addLevel(UIImage level) {
		level.position = new Vector2i(position).add(Game.getWindowWidth() * levels.size(), 0);
		UILabel label = new UILabel(new Vector2i(level.position).add(20, 200));
		label.setFont(new Font("Verdana", Font.BOLD, 60));
		label.setText("Time: 0");
		label.setColour(0xFFFFFFFF);
		UILabel label1 = new UILabel(new Vector2i(level.position).add(20, 400));
		label1.setFont(new Font("Verdana", Font.BOLD, 60));
		label1.setText("Score: 0");
		label1.setColour(0xFFFFFFFF);
		time.add(label);
		score.add(label1);
		levels.add(level);
		
		block.add(new UIImage(new Vector2i(level.position), "/UImages/Lock.png"));
	}
	
	public void setScores(int level) {
		score.get(level).setText("Score: " + Game.levels[level].topScore);
	}
	
	public void setTimes(int level) {
		if(Game.levels[level].topTime == 0) return;
		time.get(level).setText("Time: " + StringUtils.to24HourTime(Game.levels[level].topTime));
	}
	
	public void nextLevel() {
		if(Game.currentLevel < levels.size() - 1) {
			previousLevel = Game.currentLevel;
			Game.currentLevel++;
			Game.setLevel(Game.currentLevel);
			moved = true;
		}
	}
	
	public void prevLevel() {
		if(Game.currentLevel > 0) {
			previousLevel = Game.currentLevel;
			Game.currentLevel--;
			Game.setLevel(Game.currentLevel);
			moved = true;
		}
	}
	
}
