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

    public boolean move(Direction way) {
        Tile destination = eval(posX + way.dx(), posY + way.dy());
        Tile destination2 = eval(posX + way.dx()*2, posY + way.dy()*2);
        // If you can push the block in front of you, and it can move into the tile you'd be pushing it onto:
        if (destination.canPush() && destination2.canPushTo()) {
            // Set (the tile the block in front of you is moving onto) to be the block:
            set(posX + way.dx()*2, posY + way.dy()*2, destination.afterPush());
            // Set (the tile in front of you) to be (whatever type of tile the block leaves behind after getting pushed):
            set(posX + way.dx(), posY + way.dy(), eval(posX + way.dx()*2, posY + way.dy()*2).behindPush());
        }
        // If you can step onto the tile in front of you:
        if (destination.canStep()) {
            posX += way.dx();
            posY += way.dy();
            return true;
        }
        return false;
    }
}
