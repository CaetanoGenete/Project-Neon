package projectNeon.graphics.ui.uiComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import projectNeon.graphics.ui.UIPanel;
import projectNeon.utils.Vector2i;

public class UIProgressBar extends UIComponent{

	private Vector2i size;
	private float progress;
	private UILabel label;
	
	private boolean rounded = false;
	
	public UIProgressBar(Vector2i position, Vector2i size ,int progress) {
		super(position);
		
		label = new UILabel(new Vector2i(position).add(4, size.y/2));
		label.setCenterY();
		
		this.progress = progress;
		this.size = size;
	}
	
	public void update() {
		
	}
	
	public void setPogress(float progress) {
		if(progress > 1.0) this.progress = 1;
		else if(progress < 0.0) this.progress = 0;
		else this.progress = progress;
	}
	
	public void setRounder(boolean rounded) {
		this.rounded = rounded;
	}
	
	public void render(Graphics g) {
		
		if(!rounded) { 
			g.setColor(new Color(0xFFAAAAAA));
			g.fillRect(position.x + offset.x - 3, position.y + offset.y - 3, size.x + 6, size.y + 6);
			g.setColor(color);
			g.fillRect(position.x + offset.x, position.y + offset.y, (int)(size.x * progress), size.y);
		} else if(rounded) {
			g.setColor(new Color(0xFFAAAAAA));
			g.fillRoundRect(position.x + offset.x - 3, position.y + offset.y - 3, size.x + 6, size.y + 6, 16, 16);
			g.setColor(color);
			g.fillRoundRect(position.x + offset.x, position.y + offset.y, (int)(size.x * progress), size.y, 14, 14);
		}
	}
	
	public void init(UIPanel panel) {
		super.init(panel);
		panel.addComponent(label);
	}
	
	public void setLabelCentered() {
		label.position = new Vector2i(size.x/2, size.y/2).add(position);
		label.setCentered();
	}
	
	public void setLabelFont(Font font) {
		label.setFont(font);
	}
	
	public void setLabelText(String text) {
		label.setText(text);
	}
	
	public void setLabelColour(int colour) {
		label.setColour(colour);
	}

}
