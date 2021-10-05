package projectNeon.graphics.ui.uiListeners;

import projectNeon.graphics.ui.uiComponents.UIButton;

public class UIButtonListener {

	public void entered(UIButton button) {
		button.inside = true;
		button.setColour(button.originalColor.darker().getRGB());
	}
	
	public void exited(UIButton button) {
		button.inside = false;
		button.setColour(button.originalColor.getRGB());
	}
	
	public void pressed(UIButton button) {
		button.setColour(0xFFFF0000);
	}
	
	public void released(UIButton button) {
		button.setColour(button.originalColor.darker().getRGB());
	}
	
}
