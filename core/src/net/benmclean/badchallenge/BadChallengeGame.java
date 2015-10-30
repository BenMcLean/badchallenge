package net.benmclean.badchallenge;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import net.benmclean.badchallenge.model.GameWorld;
import net.benmclean.badchallenge.model.TileEnum;

public class BadChallengeGame extends ApplicationAdapter {
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
	private int posX=0;
	private int posY=0;

	public static final long SEED = new Random().nextInt(); //new Random().nextInt(); 
	public static final int VIRTUAL_WIDTH=640;
	public static final int VIRTUAL_HEIGHT=480;
	public static Random random = new Random(SEED);


	private GameWorld world = new GameWorld(SEED);

	@Override
	public void create () {
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
	}

	@Override
	public void render () {
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && TileEnum.canStep(world.eval(posX, posY+1))) posY++;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && TileEnum.canStep(world.eval(posX+1, posY))) posX++;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && TileEnum.canStep(world.eval(posX, posY-1))) posY--;
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && TileEnum.canStep(world.eval(posX-1, posY))) posX--;
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {posX=0; posY=0;}

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

		font.draw(batch, "(" + Integer.toString(posX) + ", " + Integer.toString(posY) + ") is chunk " + world.toChunkName(posX, posY), 0, 16);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}