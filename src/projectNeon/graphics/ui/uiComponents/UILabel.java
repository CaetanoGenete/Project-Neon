package projectNeon.graphics.ui.uiComponents;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import projectNeon.Game;
import projectNeon.utils.MathUtils;
import projectNeon.utils.Vector2i;

public class UILabel extends UIComponent {

	protected String text = "";
	private int time = 0, life = 0;
	private boolean centeredX = false;
	private boolean centeredY = false;
	private Font font;
	
	public UILabel(Vector2i position) {
		super(position);
		
		font = new Font("Verdana", Font.PLAIN, 40); 
		
		setColour(0xFF000000);
	}
	
	public UILabel(Vector2i position, int life) {
		super(position);
		
		font = new Font("Verdana", Font.BOLD, 40);
		
		this.life = life;
	}
	
	public void update() {
		if(life != 0)time++;
		
		if(time > life) removed = true;
	}
	
	public void render(Graphics g) {
		g.setFont(font);
		g.setColor(color);
		
		FontMetrics fm = g.getFontMetrics();
		
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		
		if(!MathUtils.inBounds(x - fm.stringWidth(text), -fm.stringWidth(text) * 2, Game.getWindowWidth())) return;
		if(!MathUtils.inBounds(y - fm.getHeight(), -fm.getHeight(), Game.getWindowHeight())) return;
		if(centeredX && centeredY && text != null)g.drawString(text, x - fm.stringWidth(text)/2, y - (fm.getHeight()/2) + fm.getAscent());
		else if(centeredX && text != null) g.drawString(text, x - fm.stringWidth(text)/2, y);
		else if(centeredY && text != null) g.drawString(text, x, y - (fm.getHeight()/2) + fm.getAscent());
		else if(text != null) g.drawString(text, x, y);
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public String getText() {
		return text;
	}
	
	public void setCenterX() {
		centeredX = true;
	}
	
	public void setCenterY() {
		centeredY = true;
	}
	
	public void setCentered() {
		centeredX = true;
		centeredY = true;
	}

}
