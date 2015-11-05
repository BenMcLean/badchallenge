package net.benmclean.badchallenge.model;

public enum Tile {
    WATER, LAND, BOX, MUD, DIAMOND;

    public boolean canStep() { return canStep(this); }
    public static boolean canStep(Tile x) {
        return x == Tile.LAND || x == Tile.MUD;
    }

    public boolean canPushTo() { return canPushTo(this); }
    public static boolean canPushTo(Tile x) {
        return canStep(x);
    }

    public boolean canPush() { return canPush(this); }
    public static boolean canPush(Tile x) {
        return x == Tile.BOX;
    }

    public Tile afterPush() { return afterPush(this); }
    public static Tile afterPush(Tile x) {
        return Tile.BOX;
    }

    public Tile behindPush() { return behindPush(this); }
    public static Tile behindPush(Tile x) {
        return Tile.LAND;
    }
}
