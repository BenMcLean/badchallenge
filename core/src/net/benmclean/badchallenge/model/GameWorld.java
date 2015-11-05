package net.benmclean.badchallenge.model;

import java.util.HashMap;

public class GameWorld {
	private WorldGenerator genesis;
	public static final int CHUNK_SIZE = 16;
    public HashMap<String, TileEnum[][]> chunk = new HashMap<String, TileEnum[][]>();
	
	public GameWorld (long SEED) {
		genesis = new WorldGenerator(SEED);
	}

    public TileEnum genesisEval (int x, int y) {
        return genesis.eval(x, y);
    }

	public final long getSeed () {
		return genesis.getSeed();
	}
	
	public TileEnum eval (int x, int y) {
		if (!chunk.containsKey(chunkName(x, y))) {
            generateChunk(x, y);
        }
        return chunk.get(chunkName(x, y))[withinChunk(x)][withinChunk(y)];
	}

    public void generateChunk(int x, int y) {
        int startX = chunkToCoord(coordToChunk(x));
        int startY = chunkToCoord(coordToChunk(y));
        chunk.put(chunkName(x, y), new TileEnum[CHUNK_SIZE][CHUNK_SIZE]);
        for (int row=startX; row<startX+CHUNK_SIZE; row++)
            for (int col=startY; col<startY+CHUNK_SIZE; col++)
                chunk.get(chunkName(x, y))[withinChunk(row)][withinChunk(col)] =
                        genesis.eval(row, col);
    }

    public static int withinChunk(int x) {
        if (x < 0)
            return CHUNK_SIZE - (Math.abs(x) - 1) % CHUNK_SIZE - 1;
        else
            return x % CHUNK_SIZE;
    }

    public static int chunkToCoord(int x) {
        return x * CHUNK_SIZE;
    }

    public static int coordToChunk(int x) {
        if (x < 0)
            return ((x+1) / CHUNK_SIZE) - 1;
        else
            return x / CHUNK_SIZE;
    }

	public static String chunkName (int x, int y) {
		return Integer.toString(coordToChunk(x)) + "_" + Integer.toString(coordToChunk(y));
	}
}
