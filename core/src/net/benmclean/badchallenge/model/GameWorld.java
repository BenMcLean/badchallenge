package net.benmclean.badchallenge.model;

public class GameWorld {
	private WorldGenerator genesis;
	public static final int CHUNK_SIZE = 64;
	
	public GameWorld (long SEED) {
		genesis = new WorldGenerator(SEED);
	}
	
	public final long getSeed () {
		return genesis.getSeed();
	}
	
	public TileEnum eval (int x, int y) {
		return genesis.eval(x, y);
	}
	
	public static final String toChunkName (int x, int y) {
		int answerX = x / CHUNK_SIZE;
		int answerY = y / CHUNK_SIZE;
		if (x < 0) answerX--;
		if (y < 0) answerY--;
		
		return Integer.toString(answerX) + "_" + Integer.toString(answerY);
	}
}
