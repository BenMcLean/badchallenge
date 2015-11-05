package net.benmclean.badchallenge.controller;

import net.benmclean.badchallenge.model.GameWorld;
import net.benmclean.badchallenge.model.Tile;

/**
 * Created by Benjamin on 11/5/2015.
 */
public class GameWorldController {
    private GameWorld world;

    public GameWorldController (long SEED) {
        world = new GameWorld(SEED);
    }

    public Tile eval(int x, int y) { return world.eval(x, y); }
    public void set(int x, int y, Tile value) { world.set(x, y, value); }
}
