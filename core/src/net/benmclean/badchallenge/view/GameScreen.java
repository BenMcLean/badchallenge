package net.benmclean.badchallenge.view;

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

import net.benmclean.badchallenge.controller.GameWorldController;
import net.benmclean.badchallenge.model.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private TextureRegion mud;
    private TextureRegion box;
    private TextureRegion character;
    private BitmapFont font;

    public static final long SEED = 42; //new Random().nextInt();
    public static final int VIRTUAL_WIDTH=640;
    public static final int VIRTUAL_HEIGHT=480;
    public static Random random = new Random(SEED);

    public static final double REPEAT_RATE = 0.12;
    private double timeSinceRepeat = 0;

    private GameWorldController world = new GameWorldController(SEED);

    public final static ArrayList<Integer> TRACKED_KEYS_ARRAY = new ArrayList<Integer>(Arrays.asList(
            Input.Keys.UP,
            Input.Keys.DOWN,
            Input.Keys.LEFT,
            Input.Keys.RIGHT,
            Input.Keys.SPACE,
            Input.Keys.ESCAPE,
            Input.Keys.ENTER,
            Input.Keys.ALT_LEFT,
            Input.Keys.ALT_RIGHT
    ));
    public final static Set<Integer> TRACKED_KEYS = Collections.unmodifiableSet(
            new HashSet<Integer>(TRACKED_KEYS_ARRAY));
    public boolean[] keyPressed = new boolean[TRACKED_KEYS_ARRAY.size()];

    public static int keyInt(int keycode) {
        for (int x = 0; x < TRACKED_KEYS_ARRAY.size(); x++)
            if (TRACKED_KEYS_ARRAY.get(x) == keycode)
                return x;
        throw new ArrayIndexOutOfBoundsException();  // This keycode is not contained in TRACKED_KEYS_ARRAY
    }

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
        mud = new TextureRegion(img, 102, 17, 16, 16);
        //tree = new TextureRegion(img, 391, 153, 16, 16);
        box = new TextureRegion(img, 28*17, 2*17, 16, 16);
        character = new TextureRegion(charSheet, 0*17, 10*17, 16, 16);
        font = new BitmapFont();

        for (int key : TRACKED_KEYS_ARRAY)
            keyPressed[keyInt(key)] = false;
        Gdx.input.setInputProcessor(this);
    }

    public void moveFromInput () {
        for (int key = 0; key < TRACKED_KEYS_ARRAY.size(); key++)
            if (keyPressed[key])
                moveFromInput(TRACKED_KEYS_ARRAY.get(key));
    }

    public void moveFromInput (int keycode) {
        switch (keycode) {
            case Input.Keys.ESCAPE: Gdx.app.exit();
            case Input.Keys.UP: world.move(Direction.NORTH); break;
            case Input.Keys.RIGHT: world.move(Direction.EAST); break;
            case Input.Keys.DOWN: world.move(Direction.SOUTH); break;
            case Input.Keys.LEFT: world.move(Direction.WEST); break;
            case Input.Keys.SPACE:
                world.setX(0);
                world.setY(0);
                break;
        }
    }

    @Override
    public void render (float delta) {

        timeSinceRepeat += delta;

        if (timeSinceRepeat >= REPEAT_RATE) {
            timeSinceRepeat = 0;
            moveFromInput();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        for (int x=0; x<40; x++)
            for (int y=0; y<30; y++) {
                switch (world.eval(world.getX() - 20 + x, world.getY() - 15 + y)) {
                    case LAND: batch.draw(land, 16*x, 16*y); break;
                    case BOX: batch.draw(land, 16*x, 16*y);
                        batch.draw(box, 16*x, 16*y); break;
                    case MUD: batch.draw(mud, 16*x, 16*y); break;
                    case WATER: batch.draw(water, 16*x, 16*y); break;
                    default: break;
                }
            }

        batch.draw(character, 16*20, 16*15);

//        font.draw(batch, "(" + Integer.toString(posX) + ", " + Integer.toString(posY) + ") is chunk " + world.chunkName(posX, posY), 0, 16);
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
        if (TRACKED_KEYS.contains(keycode)) keyPressed[keyInt(keycode)] = true;

        if (keycode == Input.Keys.ENTER && (keyPressed[keyInt(Input.Keys.ALT_LEFT)] || keyPressed[keyInt(Input.Keys.ALT_RIGHT)]))
            toggleFullscreen();
        timeSinceRepeat = 0;
        moveFromInput(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (TRACKED_KEYS.contains(keycode)) keyPressed[keyInt(keycode)] = false;
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

    public void toggleFullscreen() {
        Gdx.graphics.setDisplayMode(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, !Gdx.graphics.isFullscreen());
    }
}
