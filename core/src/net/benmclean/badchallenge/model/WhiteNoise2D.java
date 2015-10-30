package net.benmclean.badchallenge.model;

public abstract class WhiteNoise2D {
	// link here: http://stackoverflow.com/questions/7746743/parametric-random-function-for-2d-noise-generation/7746829#7746829

	public static int hash32shift(int key)
	{
		key = ~key + (key << 15); // key = (key << 15) - key - 1;
		key = key ^ (key >>> 12);
		key = key + (key << 2);
		key = key ^ (key >>> 4);
		key = key * 2057; // key = (key + (key << 3)) + (key << 11);
		key = key ^ (key >>> 16);
		return key;
	}

	public static int noise(int x, int y, int seed)
	{
		return hash32shift(seed+hash32shift(x+hash32shift(y)));
	}
}
