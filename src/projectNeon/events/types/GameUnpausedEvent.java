package projectNeon.events.types;

import projectNeon.events.Event;

public class GameUnpausedEvent extends Event {

	public GameUnpausedEvent() {
		super(Event.Type.GAME_UNPAUSED);
	}

}
