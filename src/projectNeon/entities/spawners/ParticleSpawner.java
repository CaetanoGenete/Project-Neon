package projectNeon.entities.spawners;

import projectNeon.entities.Entity;
import projectNeon.entities.particle.Particle;
import projectNeon.graphics.Sprite;
import projectNeon.level.Level;

public class ParticleSpawner extends Spawner{

	public ParticleSpawner(int x, int y, int xa, int ya , int life, Sprite sprite ,int amount, Level level) {
		super(x, y, amount);
		init(level);
		
		for(int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, xa, ya, life, sprite));
		}
		
		remove();
		
	}	
	
	public ParticleSpawner(int x, int y, int xa, int ya , int life , int amount, Entity entity,Level level) {
		super(x, y, amount);
		init(level);
		
		for(int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, xa, ya, life, entity));
		}
		
		remove();
		
	}	
	
	public ParticleSpawner(int x, int y, int xa, int ya , int smallest, int biggest,int life , int amount, Entity entity,Level level) {
		super(x, y, amount);
		init(level);
		
		for(int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, xa, ya, smallest, biggest, life, entity));
		}
		
		remove();
		
	}	

}
