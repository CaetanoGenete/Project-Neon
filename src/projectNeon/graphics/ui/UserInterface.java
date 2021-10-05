package projectNeon.graphics.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import projectNeon.Game;
import projectNeon.Game.State;
import projectNeon.events.Event;
import projectNeon.events.EventListener;

public class UserInterface implements EventListener {

	public List<UIPanel> panels = new ArrayList<UIPanel>();
	public State state;
	
	public UserInterface(State state) {
		this.state = state;
	}
	
	public void onEvent(Event event) {
		for(int i = panels.size() - 1; i >= 0; i--) {
			panels.get(i).onEvent(event);
		}
	}
	
	public void update() {
		for(UIPanel panel : panels) {
			if(Game.state == state)panel.update();
		}
	}
	
	public void render(Graphics g) {
		for(UIPanel panel : panels) {
			if(Game.state == state) panel.render(g);
		}
	}
	
	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}

}
