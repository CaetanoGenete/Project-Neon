package projectNeon.graphics.ui.uiComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import projectNeon.events.Event;
import projectNeon.events.EventListener;
import projectNeon.graphics.ui.UIPanel;
import projectNeon.utils.Vector2i;

public class UIComponent implements EventListener {

	public Vector2i position, offset;
	public Color color, originalColor;
	public boolean removed = false;
	
	protected UIPanel panel;
	
	public UIComponent(Vector2i position) {
		this.position = position;
		color = new Color(0xFFAAAAAA);
		offset = new Vector2i();
	}
	
	public void onEvent(Event event) {
		
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		
	}
	
	public void init(UIPanel panel) {
		this.panel = panel;
	}

	public Vector2i absolutePosition() {
		return new Vector2i(position).add(offset);
	}
	
	public void setOffset(Vector2i offset) {
		this.offset = offset;
	}
	
	public void setColour(int colour) {
		this.color = new Color(colour, true);
		if(originalColor == null) {
			originalColor = new Color(colour, true);
		}
	}

}
