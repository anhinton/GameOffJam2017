package nz.co.canadia.gameoffjam2017;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameOffJam extends Game {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}
}