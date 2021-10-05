package projectNeon.level.tiles;

import projectNeon.graphics.AnimatedSprite;
import projectNeon.graphics.Render;
import projectNeon.graphics.Sprite;
import projectNeon.graphics.SpriteSheet;
import projectNeon.level.Level;

public class Tile {

	public static int TILE_MODIFIER = 4;
	
	public static int TILE_SIZE = 1 << 4;
	
	public AnimatedSprite animSprite;
	
	public Sprite sprite;
	
	//AnimatedSprites:
	
	public static AnimatedSprite lavaMoving = new AnimatedSprite(SpriteSheet.lavaMovingTiles, 16, 16, 5);
	public static AnimatedSprite lavaStatic = new AnimatedSprite(SpriteSheet.lavaStaticTiles, 16, 16, 5);
	public static AnimatedSprite checkpoint = new AnimatedSprite(SpriteSheet.checkPointTiles, 16, 16, 8);
	public static AnimatedSprite coins = new AnimatedSprite(SpriteSheet.coinTiles, 16, 16, 20);
	
	//AnimatedTiles:
	
	public static Tile lavaMovingTile = new LavaTile(lavaMoving);
	public static Tile lavaStaticTile = new LavaTile(lavaStatic);
	public static Tile checkPointTile = new CheckpointTile(checkpoint);
	public static Tile coinTile = new CoinTile(coins);
	
	//BLock Tiles: 

	public static Tile chainStartTile = new ChainTile(Sprite.chainStart);
	public static Tile chainTile = new ChainTile(Sprite.chain);
	
	public static Tile spikeTile = new SpikeTile(Sprite.spike);
	public static Tile spikeReverseTile = new SpikeTile(Sprite.reverseSprite);
	
	public static Tile voidTile = new VoidTile(Sprite.voidTile);
	
	public static Tile checkPointTile2 = new CheckpointTile(null);
	
	public static Tile block = new BlockTile(Sprite.block);
	
	public static Tile wall = new WallTile(Sprite.blockM);
	
	public static Tile blockTL = new BlockTile(Sprite.blockTL);
	public static Tile blockT = new BlockTile(Sprite.blockT);
	public static Tile blockTR = new BlockTile(Sprite.blockTR);
	public static Tile blockML = new BlockTile(Sprite.blockML);
	public static Tile blockM = new BlockTile(Sprite.blockM);
	public static Tile blockMR = new BlockTile(Sprite.blockMR);
	public static Tile blockBL = new BlockTile(Sprite.blockBL);
	public static Tile blockB = new BlockTile(Sprite.blockB);
	public static Tile blockBR = new BlockTile(Sprite.blockBR);
	public static Tile blockVPipe = new BlockTile(Sprite.blockVPipe);
	public static Tile blockHPipe = new BlockTile(Sprite.blockHPipe);
	public static Tile blockLPipe = new BlockTile(Sprite.blockLPipe);
	public static Tile blockRPipe = new BlockTile(Sprite.blockRPipe);
	public static Tile blockTPipe = new BlockTile(Sprite.blockTPipe);
	public static Tile blockBpipe = new BlockTile(Sprite.blockBPipe);
	
	//Portal Tiles:
	
	public static Tile portalEnterTLTile = new PortalEnterTile(Sprite.portalTL);
	public static Tile portalEnterTRTile = new PortalEnterTile(Sprite.portalTR);
	public static Tile portalEnterMLTile = new PortalEnterTile(Sprite.portalML);
	public static Tile portalEnterMRTile = new PortalEnterTile(Sprite.portalMR);
	public static Tile portalEnterBLTile = new PortalEnterTile(Sprite.portalBL);
	public static Tile portalEnterBRTile = new PortalEnterTile(Sprite.portalBR);
	
	public static Tile portalExitTLTile = new PortalExitTile(Sprite.portalTL);
	public static Tile portalExitTRTile = new PortalExitTile(Sprite.portalTR);
	public static Tile portalExitMLTile = new PortalExitTile(Sprite.portalML);
	public static Tile portalExitMRTile = new PortalExitTile(Sprite.portalMR);
	public static Tile portalExitBLTile = new PortalExitTile(Sprite.portalBL);
	public static Tile portalExitBRTile = new PortalExitTile(Sprite.portalBR);
	
	//Deadly Tiles
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Tile(AnimatedSprite animSprite) {
		this.animSprite = animSprite;
	}
	
	public void render(int x, int y, Render render, Level level) {
	
	}
	
	public boolean solid() {
		return false;
	}
	
	public boolean deadly() {
		return false;
	}
	
}
