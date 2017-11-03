package nz.co.canadia.gameoffjam2017;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOffJam extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private OrthographicCamera camera;
	private Viewport viewport;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		// create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false,
				Constants.GAME_WIDTH,
				Constants.GAME_HEIGHT);
		viewport = new ExtendViewport(Constants.GAME_WIDTH,
				Constants.GAME_HEIGHT, camera);

		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, 1);
		shapeRenderer.rect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
		shapeRenderer.end();

		batch.begin();
		batch.draw(img, 0, 0, Constants.GAME_WIDTH / 2,
				Constants.GAME_WIDTH / 2);
		batch.draw(img, Constants.GAME_WIDTH * 3 / 4,
				Constants.GAME_HEIGHT - Constants.GAME_WIDTH / 2,
				Constants.GAME_WIDTH / 2,
				Constants.GAME_WIDTH / 2);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}