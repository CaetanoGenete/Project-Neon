package projectNeon.events.types;

import projectNeon.events.Event;

public class GameEndEvent extends Event {

	public GameEndEvent() {
		super(Event.Type.GAME_END);
	}

}
