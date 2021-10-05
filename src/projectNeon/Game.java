package projectNeon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import projectNeon.entities.mobs.Player;
import projectNeon.events.Event;
import projectNeon.events.EventListener;
import projectNeon.graphics.Render;
import projectNeon.graphics.ui.UIManager;
import projectNeon.input.Keyboard;
import projectNeon.input.Mouse;
import projectNeon.level.Level;
import projectNeon.level.LoadLevel;
import projectNeon.sound.SoundManager;
import projectNeon.utils.MathUtils;

public class Game extends Canvas implements Runnable, EventListener {
	
	private static final long serialVersionUID = -665167839372105237L;
	
	private static final int WIDTH = 360;
	private static final int HEIGHT = WIDTH/16 * 9;
	private static final int SCALE = 4;
	private static final String title = "Neon Platformer";
	
	public static int access = 0;
	public static int currentLevel = 0;
	public static boolean paused = false;
	
	public enum State {
		GAME, SCORE, MENU;
	}
	
	private Thread thread;
	private JFrame frame;
	private Render render;
	private SoundManager soundManager;
	
	public static Level level;
	public static Player player;
	public static Keyboard input;
	public static UIManager ui;
	
	public static State state = State.MENU;
	
	private boolean running = false;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Level level1;
	public Level level2;
	public Level level3;
	
	public static Level[] levels;
	
	public Game() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		
		frame = new JFrame();
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		render = new Render(WIDTH, HEIGHT);
		input = new Keyboard();
		soundManager = new SoundManager();
		
		level1 = new LoadLevel("/Levels/Level1.png", this, SoundManager.machine.clip);
		level2 = new LoadLevel("/Levels/Level2.png", this, SoundManager.tantrum.clip);
		level3 = new LoadLevel("/Levels/Level3.png", this, SoundManager.spaceBattle.clip);
		level = level1;
		player = level.getPlayer();
		
		ui = new UIManager(this);
		
		Level[] levels = {level1, level2, level3};
		Game.levels = levels;
		
		Mouse mouse = new Mouse(this);
		
		addKeyListener(input);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public synchronized void start() {
		if(running) return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}
	
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double maxUpdates = 60.0;
		double ns = 1000000000/maxUpdates;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				delta--;
				updates++;
				update();
			}
			frames++;
			render();
			
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle(title + " | Fps: " + frames + " , Ups: " + updates);
				updates = 0;
				frames = 0;
			}
		}
	}
	
	public void update() {		
		input.update();
		ui.update();
		if(state == State.GAME && !paused) {
			soundManager.update();
			level.update();
		}
	}
	
	public void render() {

		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		render.clear();
		int xScroll = (int)player.getX() - WIDTH/2;
		int yScroll = (int)player.getY() - HEIGHT/2;
		if(xScroll > MathUtils.toPixels(level.getWidth()) - WIDTH) xScroll = MathUtils.toPixels(level.getWidth()) - WIDTH;
		if(yScroll > MathUtils.toPixels(level.getHeight()) - HEIGHT) yScroll = MathUtils.toPixels(level.getHeight()) - HEIGHT;
		if(xScroll < 0) xScroll = 0;
		if(yScroll < 0) yScroll = 0;
		level.setScroll(xScroll, yScroll);
		level.render(render);

		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = render.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		Graphics2D g2d = (Graphics2D) g;
		
		if(state == State.GAME || state == State.SCORE) {
		
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			
			if(paused) {
				g2d.setFont(new Font("Verdana", Font.BOLD, 120));
				FontMetrics fm = g2d.getFontMetrics();
				g2d.setColor(new Color(0xFF000000));
				g2d.drawString("PAUSED", Game.getWindowWidth()/2 - fm.stringWidth("Paused")/2, Game.getWindowHeight()/2 - fm.getHeight()/2 + fm.getAscent());
			}
		
		}
		
		ui.render(g);
		
		bs.show();
		g.dispose();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		
		game.frame.setResizable(false);
		game.frame.setVisible(true);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		
		game.start();
	}
	
	public static void setLevel(int currentLevel) {
		level = levels[currentLevel];
		player = level.getPlayer();
	}
	
	public static void setLevel(Level level) {
		
	}
	
	public static int getWIDTH() {
		return WIDTH;
	}
	
	public static int getHEIHT() {
		return HEIGHT;
	}
	
	public static int getWindowWidth() {
		return WIDTH * SCALE;
	}
	
	public static int getWindowHeight() {
		return HEIGHT * SCALE;
	}
	
	public static int getSCALE() {
		return SCALE;
	}
	
	public static String getTitle() {
		return title;
	}

	public void onEvent(Event event) {
		ui.onEvent(event);
		soundManager.onEvent(event);
	}

	
}
