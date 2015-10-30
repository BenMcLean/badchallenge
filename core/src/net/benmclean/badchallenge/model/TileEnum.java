package net.benmclean.badchallenge.model;

public enum TileEnum {
	WATER, LAND, BOX, MUD, DIAMOND;
	
	public static boolean canStep(TileEnum x) {
		return x == TileEnum.LAND || x == TileEnum.MUD;
	}
}
