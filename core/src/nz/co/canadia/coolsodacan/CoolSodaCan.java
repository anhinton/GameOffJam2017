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
import com.badlogic.gdx.utils.I18NBundle;

import java.text.MessageFormat;

public class CoolSodaCan extends Game {
	private int gameHeight;
	private int gameUiWidth;
	AssetManager manager;
	FontLoader fontLoader;
	I18NBundle bundle;
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;

	public CoolSodaCan(FontLoader fontLoader) {
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		I18NBundle.setSimpleFormatter(true);

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
		manager.load("il8n/Bundle", I18NBundle.class);
		manager.load("graphics/graphics.atlas", TextureAtlas.class);
		TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
		param.minFilter = Texture.TextureFilter.Linear;
		param.magFilter = Texture.TextureFilter.Linear;
		manager.load("banner/banner_left.jpg", Texture.class, param);
		manager.load("banner/banner_right.jpg", Texture.class, param);
		fontLoader.loadGameUiFont(manager);
		manager.finishLoading();

		batch = new SpriteBatch();
		bundle = manager.get("il8n/Bundle", I18NBundle.class);
		// DEBUG hitboxes
		shapeRenderer = new ShapeRenderer();
		this.setScreen(new GameScreen(this));
	}

	int getGameHeight() {
		return gameHeight;
	}

	int getGameUiWidth() {
		return gameUiWidth;
	}

	String printScore(int score) {
		return Integer.toString(score);
	}

	String zeroPad(int i) {
		String in = Integer.toString(i);
		StringBuilder out = new StringBuilder();
		if (in.length() == 1) {
			out.append("0");
		}
		out.append(in);
		return out.toString();
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