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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

public class CoolSodaCan extends Game {
	private int gameHeight;
	private int gameUiWidth;

	private int uiPadding;
	SpriteBatch batch;
	I18NBundle bundle;
	FontLoader fontLoader;
	Formatter formatter;
	AssetManager manager;
	Skin skin;
	ShapeRenderer shapeRenderer;

	public CoolSodaCan(FontLoader fontLoader, Formatter formatter) {
		this.formatter = formatter;
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
		uiPadding = MathUtils.round((float) Constants.UI_PADDING / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());

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
		manager.load("skin/uiskin.atlas", TextureAtlas.class);
		manager.finishLoading();

		// Prepare skin
		skin = new Skin();
		// Manually load fonts with dynamic sizes
		skin.add("default-font", fontLoader.getGameUiFont(manager), BitmapFont.class);
		// Load Texture atlas and skinFile
		skin.addRegions(manager.get("skin/uiskin.atlas", TextureAtlas.class));
		// Note skinFile does *not* contain a BitmapFont `default-font`, this must be loaded previously
		skin.load(Gdx.files.internal("skin/uiskin.json"));

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

	public int getUiPadding() {
		return uiPadding;
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
		// NB I am not doing skin.dispose() because its TextureAtlas and BitmapFont *should*
		// be disposed in manager.dispose()
	}
}