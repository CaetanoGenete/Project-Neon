package projectNeon.events.types;

import projectNeon.events.Event;
import projectNeon.level.Level;

public class PlayerDeathEvent extends Event {

	private Level level;
	
	public PlayerDeathEvent(Level level) {
		super(Event.Type.PLAYER_DEATH);
		
		this.level = level;
	}
	
	public Level getLevel() {
		return level;
	}

}
