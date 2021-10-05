package projectNeon.entities.spawners;

import projectNeon.entities.Entity;

public class Spawner extends Entity {

	public Spawner(int x, int y, int amount) {
		this.x = x;
		this.y = y;
		remove();
	}
	
}
