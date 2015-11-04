package net.benmclean.badchallenge.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.benmclean.badchallenge.model.GameWorld;
import net.benmclean.badchallenge.model.TileEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameScreen implements Screen, InputProcessor {
    OrthographicCamera cam;
    private SpriteBatch batch;
    private Texture img;
    private Texture charSheet;
    private TextureRegion water;
    private TextureRegion land;
    private TextureRegion sand;
    private TextureRegion tree;
    private TextureRegion character;
    private BitmapFont font;
    private int posX=32;
    private int posY=32;

    public static final long SEED = 42; //new Random().nextInt();
    public static final int VIRTUAL_WIDTH=640;
    public static final int VIRTUAL_HEIGHT=480;
    public static Random random = new Random(SEED);

    public static final double REPEAT_RATE = 0.15;
    private double timeSinceRepeat = 0;

    private GameWorld world = new GameWorld(SEED);

    public final static Set<Integer> TRACKED_KEYS = Collections.unmodifiableSet(
            new HashSet<Integer>(Arrays.asList(
                    Input.Keys.UP,
                    Input.Keys.DOWN,
                    Input.Keys.LEFT,
                    Input.Keys.RIGHT,
                    Input.Keys.SPACE,
                    Input.Keys.ESCAPE,
                    Input.Keys.ENTER,
                    Input.Keys.ALT_LEFT,
                    Input.Keys.ALT_RIGHT
                    )));

    public HashMap<Integer, Boolean> keyPressed = new HashMap<Integer, Boolean>();

    @Override
    public void show() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);

        img = new Texture("roguelikeSheet_transparent.png");
        charSheet = new Texture("roguelikeChar_transparent.png");
        water = new TextureRegion(img, 0, 0, 16, 16);
        land = new TextureRegion(img, 85, 17, 16, 16);
        sand = new TextureRegion(img, 102, 17, 16, 16);
        tree = new TextureRegion(img, 391, 153, 16, 16);
        character = new TextureRegion(charSheet, 0*17, 10*17, 16, 16);
        font = new BitmapFont();

        for (int key : TRACKED_KEYS)
            keyPressed.put(key, false);
        Gdx.input.setInputProcessor(this);
    }

    public void moveFromInput () {
        timeSinceRepeat = 0;
        if (keyPressed.get(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (keyPressed.get(Input.Keys.UP) && TileEnum.canStep(world.eval(posX, posY + 1)))
            posY++;
        if (keyPressed.get(Input.Keys.RIGHT) && TileEnum.canStep(world.eval(posX + 1, posY)))
            posX++;
        if (keyPressed.get(Input.Keys.DOWN) && TileEnum.canStep(world.eval(posX, posY - 1)))
            posY--;
        if (keyPressed.get(Input.Keys.LEFT) && TileEnum.canStep(world.eval(posX - 1, posY)))
            posX--;
        if (keyPressed.get(Input.Keys.SPACE)) {
            posX = 0;
            posY = 0;
        }
    }

    @Override
    public void render (float delta) {

        timeSinceRepeat += delta;

        if (timeSinceRepeat >= REPEAT_RATE) moveFromInput();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        for (int x=0; x<40; x++)
            for (int y=0; y<30; y++) {
                switch (world.eval(posX - 20 + x, posY - 15 + y)) {
                    case LAND: batch.draw(land, 16*x, 16*y); break;
                    case BOX: batch.draw(land, 16*x, 16*y);
                        batch.draw(tree, 16*x, 16*y); break;
                    case MUD: batch.draw(sand, 16*x, 16*y); break;
                    case WATER: batch.draw(water, 16*x, 16*y); break;
                    default: break;
                }
            }

        batch.draw(character, 16*20, 16*15);

        font.draw(batch, "(" + Integer.toString(posX) + ", " + Integer.toString(posY) + ") is chunk " + world.chunkName(posX, posY), 0, 16);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (TRACKED_KEYS.contains(keycode)) keyPressed.put(keycode, true);
        moveFromInput();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (TRACKED_KEYS.contains(keycode)) keyPressed.put(keycode, false);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
