package projectNeon.events.types;

import projectNeon.events.Event;
import projectNeon.level.Level;

public class GameStartEvent extends Event {

	private Level level;
	
	public GameStartEvent(Level level) {
		super(Event.Type.GAME_START);
		
		this.level = level;
	}
	
	public Level getLevel() {
		return level;
	}

}
