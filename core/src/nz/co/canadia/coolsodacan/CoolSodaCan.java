package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class CoolSodaCan extends Game {
	SpriteBatch batch;
	private int gameHeight;
	public AssetManager manager;

	@Override
	public void create () {
		int gameWidth = Constants.GAME_WIDTH;
		gameHeight = Constants.GAME_HEIGHT;
		float gameRatio = (float) gameWidth / gameHeight;
		float screenRatio = (float) Gdx.graphics.getBackBufferWidth() / Gdx.graphics.getBackBufferHeight();
		if (screenRatio < gameRatio) {
			gameHeight = MathUtils.round(gameWidth / screenRatio);
		}

		manager = new AssetManager();

		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this));
	}

	public int getGameHeight() {
		return gameHeight;
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