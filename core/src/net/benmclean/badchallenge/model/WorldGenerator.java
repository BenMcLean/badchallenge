package net.benmclean.badchallenge.model;

import net.benmclean.badchallenge.model.OpenSimplexNoise;
import net.benmclean.badchallenge.model.WhiteNoise2D;
import net.benmclean.badchallenge.model.TileEnum;

public class WorldGenerator {

	private long SEED;
	private OpenSimplexNoise waterNoise;

	public WorldGenerator (long SEED) {
		this.SEED = SEED;
		waterNoise = new OpenSimplexNoise((long)this.SEED);
	}
	
	public final long getSeed () {
		return SEED;
	}

	public TileEnum eval (int x, int y)
	{
		if (waterNoise.eval((double)(x) / 10d, (double)(y) / 10d) > 0.35)
			return TileEnum.WATER;
		else {
			if (waterNoise.eval((double)(x) / 10d, (double)(y) / 10d) > 0.25)
				return TileEnum.MUD;
			else {
				if (WhiteNoise2D.noise (x, y, (int)SEED) % 2 > 0)
					return TileEnum.BOX;
				return TileEnum.LAND;
			}
		}
	}
}
