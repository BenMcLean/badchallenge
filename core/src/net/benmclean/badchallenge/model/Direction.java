package net.benmclean.badchallenge.model;

/**
 * Created by Benjamin on 11/5/2015.
 */
public enum Direction {
    NORTH(0, 1), SOUTH(0, -1), WEST(-1, 0), EAST(1, 0), NONE(0, 0);

    private final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() { return dx; }
    public int dy() { return dy; }

    public static Direction getRandomDirection(){
        int len = Direction.values().length - 1;
        return Direction.values()[(int)(Math.random() * len)];
    }

    public Direction opposite() { return opposite(this); }
    public static Direction opposite(Direction value) {
        switch(value) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST: return WEST;
            case WEST: return EAST;
            default: return NONE;
        }
    }
}
