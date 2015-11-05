package net.benmclean.badchallenge.controller;

import net.benmclean.badchallenge.model.Direction;
import net.benmclean.badchallenge.model.GameWorld;
import net.benmclean.badchallenge.model.Tile;

/**
 * Created by Benjamin on 11/5/2015.
 */
public class GameWorldController {
    private GameWorld world;
    private int posX = 0;
    private int posY = 0;

    public GameWorldController(long SEED) {
        world = new GameWorld(SEED);
    }

    public int getX() {
        return posX;
    }

    public void setX(int value) {
        posX = value;
    }

    public int getY() {
        return posY;
    }

    public void setY(int value) {
        posY = value;
    }

    public Tile eval(int x, int y) {
        return world.eval(x, y);
    }

    public void set(int x, int y, Tile value) {
        world.set(x, y, value);
    }

    public final long getSeed() {
        return world.getSeed();
    }

    public boolean move(Direction direction) {
        if (eval(posX + direction.dx(), posY + direction.dy()).canStep()) {
            posX += direction.dx();
            posY += direction.dy();
            return true;
        }
        return false;
    }
}
