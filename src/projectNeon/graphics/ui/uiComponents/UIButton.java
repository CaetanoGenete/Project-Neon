package projectNeon.graphics.ui.uiComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import projectNeon.events.Event;
import projectNeon.events.EventDispatcher;
import projectNeon.events.types.MouseMovedEvent;
import projectNeon.events.types.MousePressedEvent;
import projectNeon.events.types.MouseReleasedEvent;
import projectNeon.graphics.ui.UIPanel;
import projectNeon.graphics.ui.uiListeners.UIActionListener;
import projectNeon.graphics.ui.uiListeners.UIButtonListener;
import projectNeon.utils.ImageUtils;
import projectNeon.utils.Vector2i;

public class UIButton extends UIComponent {

	private UILabel label;
	private UIActionListener actionListener;
	private UIButtonListener buttonListener;
	private Rectangle bounds = new Rectangle();
	private Vector2i size;
	
	public BufferedImage image, hoverImage;
	public BufferedImage imageClicked, originalImage;
	
	private boolean border = false;
	public boolean inside = false;
	
	public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
		super(position);
		this.actionListener = actionListener;
		this.size = size;
		
		label = new UILabel(new Vector2i(position).add(4, size.y/2));
		label.setCenterY();
		
		buttonListener = new UIButtonListener();
	}
	
	public UIButton(Vector2i position, Vector2i size, UILabel label ,UIActionListener actionListener) {
		super(position);
		this.actionListener = actionListener;
		this.label = label;
		this.size = size;
		
		buttonListener = new UIButtonListener();
	}
	
	public UIButton(Vector2i position, String path, UIActionListener actionListener) {
		super(position);
		this.actionListener = actionListener;
		try {
			System.out.print("Trying to load..." + path);
			image = ImageIO.read(UIButton.class.getResource(path));
			System.out.println("...Suceeded");
			size = new Vector2i(image.getWidth(), image.getHeight());
		} catch(IOException e) {
			System.out.println("...failed because " + e.getMessage());
		}
		
		originalImage = image;
		hoverImage = ImageUtils.changeBrigthness(image, -50);
		imageClicked = ImageUtils.changeBrigthness(image, 50);
		
		buttonListener = new UIButtonListener() {
			public void entered(UIButton button) {
				button.inside = true;
				setImage(hoverImage);
			}
			
			public void exited(UIButton button) {
				button.inside = false;
				setImage(originalImage);
			}
			
			public void pressed(UIButton button) {
				setImage(imageClicked);
			}
			
			public void released(UIButton button) {
				setImage(originalImage);
			}
		};
	}
	
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> (onMousePressed((MousePressedEvent)e)));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> (onMouseReleased((MouseReleasedEvent)e)));
		dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> (onMouseMoved((MouseMovedEvent)e)));
	}
	
	public boolean onMousePressed(MousePressedEvent e) {
		if(inside) {
			buttonListener.pressed(this);
			return true;
		}
		return false;
	}
	
	public boolean onMouseReleased(MouseReleasedEvent e) {
		if(inside) {
			buttonListener.released(this);
			actionListener.action();
			return true;
		}
		return false;
	}
	
	public boolean onMouseMoved(MouseMovedEvent e) {
		if(bounds.contains(new Point(e.getX(), e.getY()))) {
			if(!inside) buttonListener.entered(this);
			inside = true;
			return true;
		} else {
			if(inside) buttonListener.exited(this);
			inside = false;
			return false;
		}
	}
	
	public void update() {
		bounds = new Rectangle(absolutePosition().x, absolutePosition().y, size.x, size.y);
	}
	
	public void render(Graphics g) {
		if(image != null) {
			g.drawImage(image, position.x + offset.x, position.y + offset.y, null);
		} else {
			g.setColor(color);
			g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
		}
		if(border) {
			g.setColor(Color.black);
			g.drawRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
		}
	}
	
	public void init(UIPanel panel) {
		super.init(panel);
		if(label != null) 
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
	
	public void setBorder(boolean border) {
		this.border = border;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
