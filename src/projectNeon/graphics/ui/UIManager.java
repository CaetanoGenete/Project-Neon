package projectNeon.graphics.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import projectNeon.Game;
import projectNeon.Game.State;
import projectNeon.entities.mobs.Player;
import projectNeon.events.Event;
import projectNeon.events.EventDispatcher;
import projectNeon.events.EventListener;
import projectNeon.events.types.BackToMenuEvent;
import projectNeon.events.types.GamePausedEvent;
import projectNeon.events.types.GameStartEvent;
import projectNeon.events.types.GameUnpausedEvent;
import projectNeon.events.types.PlayerDeathEvent;
import projectNeon.events.types.PlayerHitEvent;
import projectNeon.events.types.PlayerScoredEvent;
import projectNeon.graphics.ui.uiComponents.UIButton;
import projectNeon.graphics.ui.uiComponents.UICyclingImage;
import projectNeon.graphics.ui.uiComponents.UIImage;
import projectNeon.graphics.ui.uiComponents.UILabel;
import projectNeon.graphics.ui.uiComponents.UILevel;
import projectNeon.graphics.ui.uiComponents.UIProgressBar;
import projectNeon.graphics.ui.uiComponents.UIRectangle;
import projectNeon.graphics.ui.uiComponents.UIScore;
import projectNeon.graphics.ui.uiComponents.UITime;
import projectNeon.graphics.ui.uiListeners.UIActionListener;
import projectNeon.level.Level;
import projectNeon.level.tiles.Tile;
import projectNeon.utils.SaveFile;
import projectNeon.utils.Vector2i;

public class UIManager implements EventListener{

	private List<UserInterface> interfaces = new ArrayList<UserInterface>();
	
	public UILabel deathsLabel;
	public UILabel scoreLabel;
	
	private UIProgressBar health;
	public UIProgressBar progress;
	
	public UIScore scores;
	public UIScore death;
	public UIScore totalScore;
	
	private SaveFile file;
	
	private int saveFile = -1;
	
	public UIManager(Game game) {
		
		file = new SaveFile();
		
		//----------NEW UI---------------------------------------------------------------
		
		UserInterface gameUI = new UserInterface(Game.State.GAME);
		UserInterface menuUI = new UserInterface(Game.State.MENU);
		UserInterface scoreUI = new UserInterface(Game.State.SCORE);
		
		////////////GAME UI//////////////////////////////////////////////////////////////
		
		//----------PANELS---------------------------------------------------------------
		
		UIPanel gamePanel = new UIPanel(new Vector2i(Game.getWindowWidth() - 268, 0), new Vector2i(268, Game.getWindowHeight()));
		UIPanel progressPanel = new UIPanel(new Vector2i(280, Game.getWindowHeight() - 30), new Vector2i(Game.getWindowWidth() - 560, 20));
		
		//Rectangles:
		
		//Borders: 
		
		UIRectangle border = new UIRectangle(new Vector2i(0, 100), new Vector2i(8, Game.getWindowHeight() - 100));
		UIRectangle border1 = new UIRectangle(new Vector2i(-200, 0), new Vector2i(8, 100));
		UIRectangle border2 = new UIRectangle(new Vector2i(-200, 100), new Vector2i(200, 8));
		
		//Images:
		
		UIImage player = new UIImage(new Vector2i(16,32), "/UImages/Player.png");
		UIImage coin = new UIImage(new Vector2i(16, 132), "/UImages/Coin.png");
		
		//Labels:
		
		UILabel time = new UITime(new Vector2i());
		deathsLabel = new UILabel(new Vector2i(116, 48 + 32));
		scoreLabel = new UILabel(new Vector2i(116, 148 + 32));
		
		//Buttons:
		
		UIButton minimise = new UIButton(new Vector2i(-200, 0), new Vector2i(200, 100),time,new UIActionListener() {
			public void action() {
				if(!gamePanel.minimised) {
					gamePanel.minimised = true;
				} else {
					gamePanel.minimised = false;
				}
				gamePanel.setSpeed(1);
			}
		});
		UIButton pauseButton = new UIButton(new Vector2i(8, Game.getWindowHeight()/7 * 5 - 200), new Vector2i(268 - 8, 68), new UIActionListener() {
			public void action() {
				if(Game.paused == true) {
					Game.paused = false;
					GameUnpausedEvent event = new GameUnpausedEvent();
					game.onEvent(event);
				} else {
					Game.paused = true;
					GamePausedEvent event = new GamePausedEvent();
					game.onEvent(event);
				}
			}
		});
		UIButton restart = new UIButton(new Vector2i(8, Game.getWindowHeight()/7 * 7 - 200), new Vector2i(268 - 8, 68), new UIActionListener() {
			public void action() {
				Game.level.restart();
			}
		});
		
		UIButton suicide = new UIButton(new Vector2i(8, Game.getWindowHeight()/7 * 6 - 200), new Vector2i(268 - 8, 68), new UIActionListener() {
			public void action() {
				Game.player.death();
			}
		});
		UIButton exitButton = new UIButton(new Vector2i(8, Game.getWindowHeight()/7 * 8 - 200), new Vector2i(268 - 8, 68), new UIActionListener() {
			public void action() {
				Game.state = State.MENU;
				BackToMenuEvent event = new BackToMenuEvent();
				game.onEvent(event);
			}
		});
		
		//Progress bars:
		
		health = new UIProgressBar(new Vector2i(8, Game.getWindowHeight()/7 * 4 - 192), new Vector2i(268 - 8, 60), 1);
		progress = new UIProgressBar(new Vector2i(0,0), new Vector2i(Game.getWindowWidth() - 560, 20), 0);
		
		//Set colour:
		
		gamePanel.setColour(0x7F0000FF);
		progressPanel.setColour(0x00000000);
		
		pauseButton.setColour(0xFFAAAAAA);
		restart.setColour(0xFFAAAAAA);
		suicide.setColour(0xFFAAAAAA);
		exitButton.setColour(0xFFAAAAAA);
		minimise.setColour(0x7F0000FF);
		
		minimise.setLabelColour(0xFFFFFFFF);
		
		deathsLabel.setColour(0xFFFFFFFF);
		scoreLabel.setColour(0xFFCEAC00);
		
		health.setColour(0xFFFF0000);
		progress.setColour(0xFF0000FF);
		
		//Set Text:
		
		pauseButton.setLabelText("Pause");
		restart.setLabelText("Restart");
		suicide.setLabelText("Suicide");
		exitButton.setLabelText("Exit");
		
		deathsLabel.setText("0");
		scoreLabel.setText("0");
		
		health.setLabelText("Health");
		
		//Set Font:
		
		minimise.setLabelFont(new Font("Verdana", Font.BOLD, 58));
		
		deathsLabel.setFont(new Font("Verdana", Font.BOLD, 72));
		scoreLabel.setFont(new Font("Verdana", Font.BOLD, 72));
		
		//Miscellaneous:
		
		minimise.setLabelCentered();
		deathsLabel.setCenterY();
		scoreLabel.setCenterY();
		
		gamePanel.setMinim(new Vector2i(Game.getWindowWidth(), 0));
		
		progress.setRounder(true);
		
		//Add components:
		
		gamePanel.addComponent(pauseButton);
		gamePanel.addComponent(restart);
		gamePanel.addComponent(suicide);
		gamePanel.addComponent(exitButton);
		gamePanel.addComponent(minimise);
		
		gamePanel.addComponent(border);
		gamePanel.addComponent(border1);
		gamePanel.addComponent(border2);
		
		gamePanel.addComponent(coin);
		gamePanel.addComponent(player);
		
		gamePanel.addComponent(deathsLabel);
		gamePanel.addComponent(scoreLabel);
		
		gamePanel.addComponent(health);
		progressPanel.addComponent(progress);
		
		//Add Game panels:
		
		gameUI.addPanel(gamePanel);
		gameUI.addPanel(progressPanel);
		
		////////////MENU UI//////////////////////////////////////////////////////////////
		
		//PANELS:
		
		UIPanel menuBackground = new UIPanel(new Vector2i(), new Vector2i(Game.getWindowWidth(), Game.getWindowHeight()));
		UIPanel menuPanel = new UIPanel(new Vector2i(0, Game.getWindowHeight() - 186), new Vector2i(Game.getWindowWidth(), 186));
		UIPanel savePanel = new UIPanel(new Vector2i(0, -380), new Vector2i(270, 500));
		UIPanel loadPanel = new UIPanel(new Vector2i(Game.getWindowWidth() - 270, -380), new Vector2i(270, 500));
		
		//Images:
		
		UIImage backround = new UICyclingImage(new Vector2i(), "/UImages/Background.png");
		UIImage platform = new UICyclingImage(new Vector2i(0, Game.getWindowHeight() - 186), "/UImages/Platform.png");
		UIImage helpSheet = new UIImage(new Vector2i(0, 186), "/UImages/HelpSheet.png");
		UIImage title = new UIImage(new Vector2i(Game.getWindowWidth()/2 - 872/2, 20), "/UImages/title.png");
		
		//Rectangles:
		
		UIRectangle menuBorder1 = new UIRectangle(new Vector2i(), new Vector2i(Game.getWindowWidth(), 3));
		UIRectangle menuBorder2 = new UIRectangle(new Vector2i(0, 186), new Vector2i(Game.getWindowWidth(), 3));
		UIRectangle saveBorder1 = new UIRectangle(new Vector2i(0, 500), new Vector2i(270, 8));
		UIRectangle saveBorder2 = new UIRectangle(new Vector2i(270 - 8, 0), new Vector2i(8, 500));
		UIRectangle loadBorder1 = new UIRectangle(new Vector2i(0, 500), new Vector2i(270, 8));
		UIRectangle loadBorder2 = new UIRectangle(new Vector2i(0, 0), new Vector2i(8, 500));
		
		//Misc:
		
		UILevel levels = new UILevel(new Vector2i(Game.getWindowWidth()/2 - 500, Game.getWindowHeight()/2 - 275));
		
		//Buttons:
		
		UIButton save = new UIButton(new Vector2i(5, 500 - 110), new Vector2i(250, 100), new UIActionListener() {
			public void action() {
				if(!savePanel.minimised) {
					savePanel.minimised = true;
				} else {
					savePanel.minimised = false;
				}
				savePanel.setSpeed(5);
			}
		});
		
		UIButton save1 = new UIButton(new Vector2i(5, (500/4) * 1 - 110), new Vector2i(250, 100), new UIActionListener() {
			public void action() {
				
				saveFile = 0;
				
				UILabel label = new UILabel(new Vector2i(Game.getWindowWidth()/2, Game.getWindowHeight()/2), 120);
				label.setCentered();
				label.setColour(0xFFFF0000);
				label.setFont(new Font("Verdana", Font.BOLD, 100));
				label.setText("SAVED");
				menuBackground.addComponent(label);
				
				file.saveConfiguration("save" + (saveFile + 1) + ".txt", "Access", Game.access, "General");
				
				for(int i = 0; i < Game.levels.length; i++) {
					Level level = Game.levels[i];
					
					
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnX", level.spawnX, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnY", level.spawnY, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentX", (int)level.getPlayer().getX() >> Tile.TILE_MODIFIER, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentY", (int)level.getPlayer().getY() >> Tile.TILE_MODIFIER, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Coins", level.getScore(), "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Deaths", level.getDeaths(), "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Time", (int)level.time, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopTime", level.topTime, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopScore", level.topScore, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Finished", String.valueOf(level.finished), "Level" + (i + 1));
				}
			}
		});
		
		UIButton save2 = new UIButton(new Vector2i(5, (500/4) * 2 - 110), new Vector2i(250, 100), new UIActionListener() {
			public void action() {
				
				saveFile = 1;
				
				
				UILabel label = new UILabel(new Vector2i(Game.getWindowWidth()/2, Game.getWindowHeight()/2), 120);
				label.setCentered();
				label.setColour(0xFFFF0000);
				label.setFont(new Font("Verdana", Font.BOLD, 100));
				label.setText("SAVED");
				menuBackground.addComponent(label);
				
				file.saveConfiguration("save" + (saveFile + 1) + ".txt", "Access", Game.access, "General");
				
				for(int i = 0; i < Game.levels.length; i++) {
					Level level = Game.levels[i];
					
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnX", level.spawnX, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnY", level.spawnY, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentX", (int)level.getPlayer().getX() >> Tile.TILE_MODIFIER, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentY", (int)level.getPlayer().getY() >> Tile.TILE_MODIFIER, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Coins", level.getScore(), "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Deaths", level.getDeaths(), "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Time", (int)level.time, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopTime", level.topTime, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopScore", level.topScore, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Finished", String.valueOf(level.finished), "Level" + (i + 1));
				}
			}
		});
		
		UIButton save3 = new UIButton(new Vector2i(5, (500/4) * 3 - 110), new Vector2i(250, 100), new UIActionListener() {
			public void action() {
				
				saveFile = 2;
				
				UILabel label = new UILabel(new Vector2i(Game.getWindowWidth()/2, Game.getWindowHeight()/2), 120);
				label.setCentered();
				label.setColour(0xFFFF0000);
				label.setFont(new Font("Verdana", Font.BOLD, 100));
				label.setText("SAVED");
				menuBackground.addComponent(label);

				file.saveConfiguration("save" + (saveFile + 1) + ".txt", "Access", Game.access, "General");
				
				for(int i = 0; i < Game.levels.length; i++) {
					Level level = Game.levels[i];
					
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnX", level.spawnX, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnY", level.spawnY, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentX", (int)level.getPlayer().getX() >> Tile.TILE_MODIFIER, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentY", (int)level.getPlayer().getY() >> Tile.TILE_MODIFIER, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Coins", level.getScore(), "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Deaths", level.getDeaths(), "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Time", (int)level.time, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopTime", level.topTime, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopScore", level.topScore, "Level" + (i + 1));
					file.saveConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Finished", String.valueOf(level.finished), "Level" + (i + 1));
				}
			}
		});
		
		UIButton load = new UIButton(new Vector2i(15, 500 - 110), new Vector2i(250, 100), new UIActionListener() {
			public void action() {
				if(!loadPanel.minimised) {
					loadPanel.minimised = true;
				} else {
					loadPanel.minimised = false;
				}
				loadPanel.setSpeed(5);
			}
		});
		
		UIButton load1 = new UIButton(new Vector2i(15, (500/4) * 1 - 110), new Vector2i(250, 100), new UIActionListener() {
			public void action() {
				saveFile = 0;
				
				File textFile = new File("save" + (saveFile + 1) + ".txt");
				
				if(!textFile.exists()) {
					return;
				}
				
				
				UILabel label = new UILabel(new Vector2i(Game.getWindowWidth()/2, Game.getWindowHeight()/2), 120);
				label.setCentered();
				label.setColour(0xFFFF0000);
				label.setFont(new Font("Verdana", Font.BOLD, 100));
				label.setText("LOADED");
				menuBackground.addComponent(label);
				
				Game.access  = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "Access"));
				
				for(int i = 0; i < Game.levels.length; i++) {
					
					Level level = Game.levels[i];
					
					level.spawnX = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnX"));
					level.spawnY = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnY"));
					level.setScore(Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Coins")));
					level.setDeaths(Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Deaths")));
					level.time = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Time"));
					level.topTime = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopTime"));
					level.topScore = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopScore"));
					level.finished = Boolean.getBoolean(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Finished"));
					
					int playerX = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentX"));
					int playerY = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentY"));
				
					level.add(new Player(playerX, playerY, Game.input));
					
					Game.player = level.getPlayer();
					
					levels.setScores(i);
					levels.setTimes(i);
				}
			}
		});
		
		UIButton load2 = new UIButton(new Vector2i(15, (500/4) * 2 - 110), new Vector2i(250, 100), new UIActionListener() {
			public void action() {
				saveFile = 1;
				
				File textFile = new File("save" + (saveFile + 1) + ".txt");
				
				if(!textFile.exists()) {
					return;
				}
				
				UILabel label = new UILabel(new Vector2i(Game.getWindowWidth()/2, Game.getWindowHeight()/2), 120);
				label.setCentered();
				label.setColour(0xFFFF0000);
				label.setFont(new Font("Verdana", Font.BOLD, 100));
				label.setText("LOADED");
				menuBackground.addComponent(label);
				
				Game.access  = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "Access"));
				
				for(int i = 0; i < Game.levels.length; i++) {
										
					Level level = Game.levels[i];
					
					level.spawnX = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnX"));
					level.spawnY = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnY"));
					level.setScore(Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Coins")));
					level.setDeaths(Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Deaths")));
					level.time = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Time"));
					level.topTime = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopTime"));
					level.topScore = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopScore"));
					level.finished = Boolean.getBoolean(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Finished"));
					
					int playerX = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentX"));
					int playerY = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentY"));
				
					level.add(new Player(playerX, playerY, Game.input));
					
					Game.player = level.getPlayer();
					
					levels.setScores(i);
					levels.setTimes(i);

				}
			}
		});
		
		UIButton load3 = new UIButton(new Vector2i(15, (500/4) * 3 - 110), new Vector2i(250, 100), new UIActionListener() {
			public void action() {
				saveFile = 2;
				
				File textFile = new File("save" + (saveFile + 1) + ".txt");
				
				if(!textFile.exists()) {
					return;
				}
				
				UILabel label = new UILabel(new Vector2i(Game.getWindowWidth()/2, Game.getWindowHeight()/2), 120);
				label.setCentered();
				label.setColour(0xFFFF0000);
				label.setFont(new Font("Verdana", Font.BOLD, 100));
				label.setText("LOADED");
				menuBackground.addComponent(label);
				
				Game.access  = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "Access"));
				
				for(int i = 0; i < Game.levels.length; i++) {
					
					Level level = Game.levels[i];
					
					level.spawnX = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnX"));
					level.spawnY = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "SpawnY"));
					level.setScore(Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Coins")));
					level.setDeaths(Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Deaths")));
					level.time = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Time"));
					level.topTime = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopTime"));
					level.topScore = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "TopScore"));
					level.finished = Boolean.getBoolean(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "Finished"));
					
					int playerX = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentX"));
					int playerY = Integer.parseInt(file.loadConfiguration("save" + (saveFile + 1) + ".txt", "level" + i + "CurrentY"));
					
					level.add(new Player(playerX, playerY, Game.input));
					
					Game.player = level.getPlayer();
					
					levels.setScores(i);
					levels.setTimes(i);
					
				}
			}
		});
		
		UIButton helpButton = new UIButton(new Vector2i(20, 186/2 - 128/2), "/UImages/HelpButton.png", new UIActionListener() {
			public void action() {
				if(!menuPanel.minimised) {
					menuPanel.minimised = true;
				} else {
					menuPanel.minimised = false;
				}
				menuPanel.setSpeed(6);
			}
		});
		
		UIButton OptionsButton = new UIButton(new Vector2i(Game.getWindowWidth() - 128 - 20, 186/2 - 128/2), "/UImages/OptionsButton.png", new UIActionListener() {
			public void action() {
				System.out.println("worked");
			}
		});
		UIButton left = new UIButton(new Vector2i(Game.getWindowWidth()/9 * 4 - 128, 186/2 - 128/2), "/UImages/LeftButton.png", new UIActionListener() {
			public void action() {
				levels.prevLevel();
			}
		});
		UIButton right = new UIButton(new Vector2i(Game.getWindowWidth()/9 * 6 - 128, 186/2 - 128/2), "/UImages/RightButton.png", new UIActionListener() {
			public void action() {
				levels.nextLevel();
			}
		});
		UIButton play = new UIButton(new Vector2i(Game.getWindowWidth()/9 * 5 - 144, 186/2 - 160/2), "/UImages/PlayButton.png", new UIActionListener() {
			public void action() {
				if(Game.access >= Game.currentLevel) {
					if(Game.level.finished) {
						Game.level.restart();
					}
					
					GameStartEvent event = new GameStartEvent(Game.level);
					game.onEvent(event);
					
					Game.player = Game.level.getPlayer();
					Game.state = State.GAME;
				}
			}
		});
		//Add Levels:
		
		levels.addLevel(new UIImage(new Vector2i(), "/LevelImages/Level1.png"));
		levels.addLevel(new UIImage(new Vector2i(), "/LevelImages/Level2.png"));
		levels.addLevel(new UIImage(new Vector2i(), "/LevelImages/Level3.png"));
		
		//setColour:
		
		menuBackground.setColour(0x00000000);
		menuPanel.setColour(0x7FFFFF00);
		savePanel.setColour(0x7FFFFF00);
		loadPanel.setColour(0x7FFFFF00);
		
		menuBorder1.setColour(0xFFFFFFFF);
		menuBorder2.setColour(0xFFFFFFFF);
		saveBorder1.setColour(0xFFFFFFFF);
		saveBorder2.setColour(0xFFFFFFFF);
		loadBorder1.setColour(0xFFFFFFFF);
		loadBorder2.setColour(0xFFFFFFFF);
		
		save.setColour(0xFFFFFFFF);
		load.setColour(0xFFFFFFFF);
		save1.setColour(0xFFFFFFFF);
		save2.setColour(0xFFFFFFFF);
		save3.setColour(0xFFFFFFFF);
		load1.setColour(0xFFFFFFFF);
		load2.setColour(0xFFFFFFFF);
		load3.setColour(0xFFFFFFFF);
		
		//Set Text:
		
		save.setLabelText("SAVE");
		load.setLabelText("LOAD");
		save1.setLabelText("SAVE 1");
		save2.setLabelText("SAVE 2");
		save3.setLabelText("SAVE 3");
		load1.setLabelText("LOAD 1");
		load2.setLabelText("LOAD 2");
		load3.setLabelText("LOAD 3");
		
		//Miscellaneous:
		
		((UICyclingImage) platform).setSpeed(4);
		
		menuPanel.setMinim(new Vector2i());
		savePanel.setMinim(new Vector2i(0, 0));
		loadPanel.setMinim(new Vector2i(Game.getWindowWidth() - 270, 0));
		
		save.setLabelCentered();
		load.setLabelCentered();
		save1.setLabelCentered();
		save2.setLabelCentered();
		save3.setLabelCentered();
		load1.setLabelCentered();
		load2.setLabelCentered();
		load3.setLabelCentered();
	
		//Add componenet:
		
		menuBackground.addComponent(backround);
		menuBackground.addComponent(levels);
		menuBackground.addComponent(platform);
		menuBackground.addComponent(title);

		menuPanel.addComponent(helpSheet);
		
		menuPanel.addComponent(menuBorder1);
		menuPanel.addComponent(menuBorder2);
		
		menuPanel.addComponent(helpButton);
		menuPanel.addComponent(OptionsButton);
		menuPanel.addComponent(left);
		menuPanel.addComponent(right);
		menuPanel.addComponent(play);
		
		savePanel.addComponent(save);
		loadPanel.addComponent(load);
		
		savePanel.addComponent(saveBorder1);
		savePanel.addComponent(saveBorder2);
		savePanel.addComponent(save1);
		savePanel.addComponent(save2);
		savePanel.addComponent(save3);
		
		loadPanel.addComponent(loadBorder1);
		loadPanel.addComponent(loadBorder2);
		loadPanel.addComponent(load1);
		loadPanel.addComponent(load2);
		loadPanel.addComponent(load3);
		
		
		//Add Panel:
		
		menuUI.addPanel(menuBackground);
		menuUI.addPanel(savePanel);
		menuUI.addPanel(loadPanel);
		menuUI.addPanel(menuPanel);
		
		//----------SCORE UI---------------------------------------------------------------
		
	    UIPanel scorePanel = new UIPanel(new Vector2i(Game.getWindowWidth()/2 - Game.getWindowWidth()/4, Game.getWindowHeight()/2 - Game.getWindowHeight()/4), new Vector2i(Game.getWindowWidth()/2, Game.getWindowHeight()/2));
		
		//Borders:
		
		UIRectangle scoreBorder1 = new UIRectangle(new Vector2i(0,0), new Vector2i(2 * Game.getSCALE(), scorePanel.size.y));
		UIRectangle scoreBorder2 = new UIRectangle(new Vector2i(0,0), new Vector2i(scorePanel.size.x, 2 * Game.getSCALE()));
		UIRectangle scoreBorder3 = new UIRectangle(new Vector2i(scorePanel.size.x - 2 * Game.getSCALE(),0), new Vector2i(2 * Game.getSCALE(), scorePanel.size.y));
		UIRectangle scoreBorder4 = new UIRectangle(new Vector2i(0,scorePanel.size.y - 2 * Game.getSCALE()), new Vector2i(scorePanel.size.x, 2 * Game.getSCALE()));
		
		//Buttons:
		
		UIButton back = new UIButton(new Vector2i(scorePanel.size.x - 100, 0), new Vector2i(100, scorePanel.size.y), new UIActionListener() {
			public void action() {
				BackToMenuEvent event = new BackToMenuEvent();
				game.onEvent(event);
				
				levels.setScores(Game.currentLevel);
				levels.setTimes(Game.currentLevel);
				
				Game.state = State.GAME;
				Game.state = State.MENU;
			}
		});
		
		//Text:
		
		UILabel coins = new UILabel(new Vector2i(10, scorePanel.size.y/5));
		UILabel timer = new UILabel(new Vector2i(10, (scorePanel.size.y/5) * 2));
		UILabel deaths = new UILabel(new Vector2i(10, (scorePanel.size.y/5) * 3));
		UILabel total = new UILabel(new Vector2i(10, (scorePanel.size.y/5) * 4));
		
		UILabel timeScore = new UITime(new Vector2i(300, (scorePanel.size.y/5) * 2));
		
		scores = new UIScore(new Vector2i(300, scorePanel.size.y/5));
		death = new UIScore(new Vector2i(300, scorePanel.size.y/5 * 3));
		totalScore = new UIScore(new Vector2i(300, scorePanel.size.y/5 * 4));
		
		//Set Font:
		
		coins.setFont(new Font("Verdana", Font.BOLD, 40));
		timer.setFont(new Font("Verdana", Font.BOLD, 40));
		deaths.setFont(new Font("Verdana", Font.BOLD, 40));
		total.setFont(new Font("Verdana", Font.BOLD, 40));
		
		scores.setFont(new Font("Verdana", Font.BOLD, 40));
		death.setFont(new Font("Verdana", Font.BOLD, 40));
		timeScore.setFont(new Font("Verdana", Font.BOLD, 40));
		totalScore.setFont(new Font("Verdana", Font.BOLD, 40));
		
		//Set Text:
		
		coins.setText("COINS: ");
		timer.setText("TIME: ");
		deaths.setText("DEATHS: ");
		total.setText("SCORE: ");
		back.setLabelText("Menu");
		
		//Set colour:
		
		scorePanel.setColour(0x7FFF0000);
		scoreBorder1.setColour(0xFFAAAAAA);
		scoreBorder2.setColour(0xFFAAAAAA);
		scoreBorder3.setColour(0xFFAAAAAA);
		scoreBorder4.setColour(0xFFAAAAAA);
		
		scores.setColour(0xFFFFFFFF);
		death.setColour(0xFFFFFFFF);
		timeScore.setColour(0xFFFFFFFF);
		totalScore.setColour(0xFFFFFFFF);
		
		coins.setColour(0xFFFFFFFF);
		timer.setColour(0xFFFFFFFF);
		deaths.setColour(0xFFFFFFFF);
		total.setColour(0xFFFFFFFF);
		
		back.setColour(0xFFAAAAAA);
		
		//Miscellaneous:
		
		back.setLabelCentered();
		
		//add componenets:
		
		scorePanel.addComponent(scoreBorder1);
		scorePanel.addComponent(scoreBorder2);
		scorePanel.addComponent(scoreBorder3);
		scorePanel.addComponent(scoreBorder4);
		scorePanel.addComponent(coins);
		scorePanel.addComponent(timer);
		scorePanel.addComponent(deaths);
		scorePanel.addComponent(total);
		scorePanel.addComponent(scores);
		scorePanel.addComponent(death);
		scorePanel.addComponent(timeScore);
		scorePanel.addComponent(totalScore);
		scorePanel.addComponent(back);
	
		//Add Panels:
		
		scoreUI.addPanel(scorePanel);
		
		
		interfaces.add(scoreUI);
		interfaces.add(gameUI);
		interfaces.add(menuUI);
	}
	
	public void onEvent(Event event) {
		for(int i = interfaces.size() - 1; i >= 0; i--) {
			UserInterface ui = interfaces.get(i);
			if(Game.state == ui.state)ui.onEvent(event);
		}
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.PLAYER_DEATH, (Event e) -> (onDeath((PlayerDeathEvent)e)));
		dispatcher.dispatch(Event.Type.PLAYER_HIT, (Event e) -> (onHit((PlayerHitEvent)e)));
		dispatcher.dispatch(Event.Type.PLAYER_SCORED, (Event e) -> (onScore((PlayerScoredEvent)e)));
		dispatcher.dispatch(Event.Type.GAME_START, (Event e) -> (onStartGame((GameStartEvent)e)));
	}

	public boolean onDeath(PlayerDeathEvent e) {
		deathsLabel.setText(String.valueOf(e.getLevel().getDeaths() + 1));
		health.setPogress(1);
		return false;
	}
	
	public boolean onScore(PlayerScoredEvent e) {
		scoreLabel.setText(String.valueOf(Integer.parseInt(scoreLabel.getText()) + 1));
		return false;
	}
	
	public boolean onStartGame(GameStartEvent e) {
		scoreLabel.setText(String.valueOf(e.getLevel().getScore()));
		deathsLabel.setText(String.valueOf(e.getLevel().getDeaths()));
		return false;
	}
	
	public boolean onHit(PlayerHitEvent e) {
		health.setPogress(e.getHealth() * 0.33f);
		return false;
	}
	
	public void update() {
		for(UserInterface ui: interfaces) {
			ui.update();
		}
	}
	
	public void render(Graphics g) {
		for(UserInterface ui: interfaces) {
			ui.render(g);
		}
	}

	
}
