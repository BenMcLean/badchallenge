package net.benmclean.badchallenge;

import com.badlogic.gdx.Game;
import net.benmclean.badchallenge.screen.GameScreen;

public class BadChallengeGame extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
