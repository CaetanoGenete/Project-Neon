package projectNeon.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import projectNeon.events.EventListener;
import projectNeon.events.types.MouseMovedEvent;
import projectNeon.events.types.MousePressedEvent;
import projectNeon.events.types.MouseReleasedEvent;

public class Mouse implements MouseListener, MouseMotionListener{

	private EventListener eventListner;
	
	public Mouse(EventListener listener) {
		eventListner = listener;
	}
	
	public void mouseDragged(MouseEvent e) {
		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), true);
		eventListner.onEvent(event);
	}

	public void mouseMoved(MouseEvent e) {
		MouseMovedEvent event = new MouseMovedEvent(e.getX(), e.getY(), false);
		eventListner.onEvent(event);
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		MousePressedEvent event = new MousePressedEvent(e.getButton(), e.getX(), e.getY());
		eventListner.onEvent(event);
	}

	public void mouseReleased(MouseEvent e) {
		MouseReleasedEvent event = new MouseReleasedEvent(e.getButton(), e.getX(), e.getY());
		eventListner.onEvent(event);
	}

}
