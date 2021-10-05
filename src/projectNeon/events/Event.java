package projectNeon.events;

public abstract class Event {

	public enum Type {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED,
		
		PLAYER_DEATH,
		PLAYER_SCORED,
		PLAYER_HIT,
		
		ENEMY_DEATH,
		
		GAME_START,
		GAME_END,
		
		BACK_TO_MENU,
		
		GAME_PAUSED,
		GAME_UNPAUSED;
	}
	
	private Type type;
	boolean handled;
	
	protected Event(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
}
