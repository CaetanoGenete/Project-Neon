package projectNeon.sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundFile {

	private String path;
	public Clip clip;
	
	public SoundFile(String path) {
		this.path = path;
		soundLoader();
	}
	

	private void soundLoader() {
		try {
		     URL defaultSound = SoundFile.class.getResource(path);
	         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
	         clip = AudioSystem.getClip();
	         clip.open(audioInputStream);
	         FloatControl  gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			 gainControl.setValue(-10.0f);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
