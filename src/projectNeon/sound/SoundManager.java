package projectNeon.sound;

import java.util.Random;

import javax.sound.sampled.Clip;

import projectNeon.events.Event;
import projectNeon.events.EventDispatcher;
import projectNeon.events.EventListener;
import projectNeon.events.types.BackToMenuEvent;
import projectNeon.events.types.EnemyDeathEvent;
import projectNeon.events.types.GamePausedEvent;
import projectNeon.events.types.GameStartEvent;
import projectNeon.events.types.GameUnpausedEvent;
import projectNeon.events.types.PlayerDeathEvent;
import projectNeon.events.types.PlayerHitEvent;
import projectNeon.events.types.PlayerScoredEvent;

public class SoundManager implements EventListener {

	public static SoundFile death = new SoundFile("/Sounds/SFX/explode.wav");
	public static SoundFile score1 = new SoundFile("/Sounds/SFX/coin1.wav");
	public static SoundFile score2 = new SoundFile("/Sounds/SFX/coin2.wav");
	public static SoundFile score3 = new SoundFile("/Sounds/SFX/coin3.wav");
	public static SoundFile score4 = new SoundFile("/Sounds/SFX/coin4.wav");
	public static SoundFile score5 = new SoundFile("/Sounds/SFX/coin5.wav");
	public static SoundFile start = new SoundFile("/Sounds/SFX/start.wav");
	
	
	
	public static SoundFile machine = new SoundFile("/Sounds/Tracks/Machine.wav");
	public static SoundFile spaceBattle = new SoundFile("/Sounds/Tracks/SpaceBattle.wav");
	public static SoundFile tantrum = new SoundFile("/Sounds/Tracks/Tantrum.wav");
	public static SoundFile WinterWind = new SoundFile("/Sounds/Tracks/WinterWind.wav");
	
	public static Clip soundTrack;
	
	public Random random = new Random();
	
	public SoundManager() {
		soundTrack = WinterWind.clip;
		soundTrack.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);	
		dispatcher.dispatch(Event.Type.PLAYER_HIT, (Event e) -> (onPlayerHit((PlayerHitEvent)e)));
		dispatcher.dispatch(Event.Type.PLAYER_DEATH, (Event e) -> (onPlayerDeath((PlayerDeathEvent)e)));
		dispatcher.dispatch(Event.Type.PLAYER_SCORED, (Event e) -> (onPlayerScored((PlayerScoredEvent)e)));
		dispatcher.dispatch(Event.Type.GAME_START, (Event e) -> (onGameStart((GameStartEvent)e)));
		dispatcher.dispatch(Event.Type.ENEMY_DEATH, (Event e) -> (onEnemyDeath((EnemyDeathEvent)e)));
		dispatcher.dispatch(Event.Type.BACK_TO_MENU, (Event e) -> (onMenuReturn((BackToMenuEvent)e)));
		dispatcher.dispatch(Event.Type.GAME_PAUSED, (Event e) -> (onGamePaused((GamePausedEvent)e)));
		dispatcher.dispatch(Event.Type.GAME_UNPAUSED, (Event e) -> (onGameUnpaused((GameUnpausedEvent)e)));
	}
	
	public void update() {
	}
	
	public boolean onGameUnpaused(GameUnpausedEvent e) {
		resumeClip(soundTrack);
		return true;
	}
	
	public boolean onGamePaused(GamePausedEvent e) {
		pauseClip(soundTrack);
		return true;
	}
	
	public boolean onPlayerHit(PlayerHitEvent e) {
		return false;
	}
	
	public boolean onPlayerDeath(PlayerDeathEvent e) {
		playClip(death.clip);
		return false;
	}
	
	public boolean onGameStart(GameStartEvent e) {
		stopClip(soundTrack);
		playClip(start.clip);
		soundTrack = e.getLevel().clip;
		soundTrack.loop(Clip.LOOP_CONTINUOUSLY);
		return true;
	}
	
	public boolean onEnemyDeath(EnemyDeathEvent e) {
		playClip(death.clip);
		return true;
	}
	
	public boolean onMenuReturn(BackToMenuEvent e) {
		pauseClip(soundTrack);
		soundTrack = WinterWind.clip;
		soundTrack.loop(Clip.LOOP_CONTINUOUSLY);
		return false;
	}
	
	public boolean onPlayerScored(PlayerScoredEvent e) {
		int rand = random.nextInt(5);
		if(rand == 0) playClip(score1.clip);
		else if(rand == 1) playClip(score2.clip);
		else if(rand == 2) playClip(score3.clip);
		else if(rand == 3) playClip(score4.clip);
		else if(rand == 4) playClip(score5.clip);
		return false;
	}
	
	public void playClip(Clip clip) {
		stopClip(clip);
		clip.start();
	}
	
	public void resumeClip(Clip clip) {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void stopClip(Clip clip) {
		if(clip.isRunning()) {
			clip.stop();
		}
		clip.setFramePosition(0);
	}
	
	public static void pauseClip(Clip clip) {
		if(clip.isRunning()) {
			clip.stop();
		}
	}

}
