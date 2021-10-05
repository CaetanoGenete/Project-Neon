package projectNeon.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import projectNeon.events.Event;
import projectNeon.events.EventListener;
import projectNeon.graphics.ui.uiComponents.UIComponent;
import projectNeon.utils.Vector2i;

public class UIPanel implements EventListener {

	private Vector2i position, origin, minim;
	public Vector2i size;
	private Color color;

	public boolean minimised = false;
	public boolean moved = false;
	
	private int speed = 1;
	
	private List<UIComponent> componenets = new ArrayList<UIComponent>();
	
	public UIPanel(Vector2i position, Vector2i size) {
		this.position = position;
		this.origin = new Vector2i(position);
		this.minim = new Vector2i(position);
		this.size = size;
		
		setColour(0xFFAAAAAA);
	}
	
	public void onEvent(Event event) {
		for(int i = componenets.size() - 1; i >= 0; i--) {
			componenets.get(i).onEvent(event);
		}
	}
	
	public void update() {
		for(int i = 0; i < componenets.size(); i++) {
			UIComponent component = componenets.get(i);
			
			if(component.removed) componenets.remove(i);
			
			component.update();
			component.setOffset(position);
		}
		
		if(minimised) {
			if(position.x < minim.x) {
				position.x+=speed;
				moved = false;
			} else if(position.x > minim.x) {
				position.x-=speed;
				moved = false;
			} else {
				position.x = minim.x;
				moved = true;
			}
			
			if(position.y > minim.y) {
				position.y-=speed;
				moved = false;
			} else if(position.y < minim.y) {
				position.y+=speed;
				moved = false;
			} else {
				position.y = minim.y;
				moved = true;
			}
		} else {
			if(position.x > origin.x) {
				position.x-=speed;
				moved = false;
			} else if(position.x < origin.x) {
				position.x+=speed;
				moved = false;
			} else {
				position.x = origin.x;
				moved = true;
			}
			
			if(position.y < origin.y) {
				position.y+=speed;
				moved = false;
			} else if(position.y > origin.y) {
				position.y-=speed;
				moved = false;
			} else {
				position.y = origin.y;
				moved = true;
			}
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x, position.y, size.x, size.y);
		
		for(UIComponent component : componenets) {
			component.render(g);
		}
	}
	
	public void setColour(int colour) {
		this.color = new Color(colour, true);
	}
	
	public void addComponent(UIComponent component) {
		componenets.add(component);
		component.init(this);
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setMinim(Vector2i minim) {
		this.minim = minim;
	}
	
}
