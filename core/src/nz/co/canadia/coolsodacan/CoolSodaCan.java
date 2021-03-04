package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class CoolSodaCan extends Game {
	private int gameHeight;
	private int gameUiWidth;
	AssetManager manager;
	FontLoader fontLoader;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;

	public CoolSodaCan(FontLoader fontLoader) {
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		int gameWidth = Constants.GAME_WIDTH;
		gameHeight = Constants.GAME_HEIGHT;
		float gameRatio = (float) gameWidth / gameHeight;
		float screenRatio = (float) Gdx.graphics.getBackBufferWidth() / Gdx.graphics.getBackBufferHeight();
		gameUiWidth = Gdx.graphics.getBackBufferWidth();
		int gameUiHeight = Gdx.graphics.getBackBufferHeight();
		if (screenRatio < gameRatio) {
			gameHeight = MathUtils.round(gameWidth / screenRatio);
		} else {
			gameUiWidth = MathUtils.round(gameUiHeight * gameRatio);
		}

		// Load assets
		manager = new AssetManager();
		manager.load("graphics/graphics.atlas", TextureAtlas.class);
		TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
		param.minFilter = Texture.TextureFilter.Linear;
		param.magFilter = Texture.TextureFilter.Linear;
		manager.load("banner/banner_left.jpg", Texture.class, param);
		manager.load("banner/banner_right.jpg", Texture.class, param);
		fontLoader.loadGameUiFont(manager);
		manager.finishLoading();

		batch = new SpriteBatch();
		// DEBUG hitboxes
		shapeRenderer = new ShapeRenderer();
		this.setScreen(new GameScreen(this));
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public int getGameUiWidth() {
		return gameUiWidth;
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}
}