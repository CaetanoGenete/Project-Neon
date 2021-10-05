package projectNeon.events.types;

import projectNeon.events.Event;

public class PlayerHitEvent extends Event{

	private int health;
	
	public PlayerHitEvent(int health) {
		super(Event.Type.PLAYER_HIT);
		
		this.health = health;
	}
	
	public int getHealth() {
		return health;
	}

}
