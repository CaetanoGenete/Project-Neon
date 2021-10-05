package projectNeon.entities.miscellaneous;


import projectNeon.Game;
import projectNeon.Game.State;
import projectNeon.entities.Entity;
import projectNeon.entities.mobs.Player;
import projectNeon.entities.spawners.ParticleSpawner;
import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.graphics.ui.UIManager;
import projectNeon.level.Level;
import projectNeon.level.tiles.Tile;

public class Finish extends Entity{

	double rotate = 0;
	private Game game;
	
	public Finish(int x, int y) {
		this.x = x << Tile.TILE_MODIFIER;
		this.y = y << Tile.TILE_MODIFIER;
		
		this.sprite = Sprite.finish;
	}
	
	public void update() {
		
		Player player = level.getPlayer((int)x, (int)y, Game.getWIDTH() + 96);
		
		if(player != null) {
		
			if(rotate > Math.PI) rotate = -Math.PI;
		
			rotate += 0.03;
		
			sprite = Sprite.rotateSprite(Sprite.finish, rotate);
		
			level.add(new ParticleSpawner((int)x + 48, (int)y + 48, 0, 0, 50, new Sprite(3, 3, 0xFF7F00FF), 8, level));
			
			if(player.getX() >= x && player.getY() >= y && player.getX() < x + 96 && player.getY() < y + 96) {
				UIManager manager = Game.ui;
				
				manager.scores.setText(String.valueOf(level.getScore()));
				manager.death.setText(String.valueOf(level.getDeaths()));
				
				int score = level.getScore();
				int deaths = level.getDeaths();
				int topScore = (int)((score * 10000000)/(level.time/100 * (deaths + 1)));
				
				manager.totalScore.setText(String.valueOf(topScore));
				
				if(topScore > level.topScore) {
					level.topScore = topScore;
				}
				if(level.time < level.topTime || level.topTime == 0) {
					level.topTime = (int) level.time;
				}
				
				if(Game.currentLevel == Game.access) Game.access++;
				level.finished = true;
				Game.state = State.SCORE;
			}
			
		}
	}
	
	public void init(Level level) {
		super.init(level);
		
		game = level.game;
	}
	
	public void render(Render render) {
		render.drawSprite((int)x, (int)y, sprite);
	}
	
}
