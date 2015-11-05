package net.benmclean.badchallenge.model;

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

    public Tile eval (int x, int y)
    {
        if (waterNoise.eval((double)(x) / 10d, (double)(y) / 10d) > 0.35)
            return Tile.WATER;
        else {
            if (waterNoise.eval((double)(x) / 10d, (double)(y) / 10d) > 0.25)
                return Tile.MUD;
            else {
                if (WhiteNoise2D.noise (x, y, (int)SEED) % 2 > 0)
                    return Tile.BOX;
                return Tile.LAND;
            }
        }
    }
}
