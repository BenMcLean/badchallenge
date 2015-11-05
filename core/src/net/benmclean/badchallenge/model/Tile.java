package net.benmclean.badchallenge.model;

public enum Tile {
    WATER, LAND, BOX, MUD, DIAMOND;

    public boolean canStep() { return canStep(this); }
    public static boolean canStep(Tile x) {
        return x == Tile.LAND || x == Tile.MUD;
    }

    public boolean canPushTo() { return canPushTo(this); }
    public static boolean canPushTo(Tile x) {
        return x == Tile.LAND || x == Tile.WATER;
    }

    public boolean canPush() { return canPush(this); }
    public static boolean canPush(Tile x) {
        return x == Tile.BOX;
    }

    public Tile behindStep() { return behindStep(this); }
    public static Tile behindStep(Tile x) {
        return Tile.LAND;
    }

    public Tile behindPush() { return behindPush(this); }
    public static Tile behindPush(Tile x) {
        return Tile.LAND;
    }

    public Tile pushedBecomes (Tile into) {
        /**
         * @param into      What this tile will land on after being pushed
         * @return          What the landing tile becomes after the push
         */
        return pushedBecomes(this, into);
    }
    public static Tile pushedBecomes(Tile pushed, Tile into) {
        /**
         * @param pushed    What tile is being pushed
         * @param into      What the pushed tile will land on after being pushed
         * @return          What the landing tile becomes after the push
         */
        if (pushed == Tile.BOX && into == Tile.WATER)
            return Tile.MUD;
        else
            return pushed;
    }
}
