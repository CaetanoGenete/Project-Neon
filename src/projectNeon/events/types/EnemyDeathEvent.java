package projectNeon.events.types;

import projectNeon.events.Event;

public class EnemyDeathEvent extends Event {

	public EnemyDeathEvent() {
		super(Event.Type.ENEMY_DEATH);
	}

}
