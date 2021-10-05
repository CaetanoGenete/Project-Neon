package projectNeon.events.types;

import projectNeon.events.Event;

public class GamePausedEvent extends Event {

	public GamePausedEvent() {
		super(Event.Type.GAME_PAUSED);
	}

}
