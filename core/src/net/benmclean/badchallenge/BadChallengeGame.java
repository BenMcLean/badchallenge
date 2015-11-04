package net.benmclean.badchallenge;

import com.badlogic.gdx.Game;

import net.benmclean.badchallenge.screen.GameScreen;

public class BadChallengeGame extends Game {
    @Override
    public void create() {
//        int x = -9;
//        int y = 0;
//        System.out.println("x: " + x + ", y: " + y);
//        System.out.println("Chunk: " + GameWorld.chunkName(x, y));
////        System.out.println("chunkToCoord x: " + GameWorld.chunkToCoord(GameWorld.coordToChunk(x)) + ", y: " + GameWorld.chunkToCoord(GameWorld.coordToChunk(y)));
//        System.out.println("Within chunk: x: " + GameWorld.withinChunk(x) + ", y: " + GameWorld.withinChunk(y));
//        Gdx.app.exit();

        setScreen(new GameScreen());
    }
}
