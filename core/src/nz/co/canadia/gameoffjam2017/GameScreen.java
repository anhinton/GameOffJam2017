package nz.co.canadia.gameoffjam2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main game screen
 */

public class GameScreen implements Screen {

    private final GameOffJam game;
    private Player player;
    private OrthographicCamera camera;
    private Viewport viewport;
    private boolean debug;

    GameScreen(GameOffJam game) {
        this.game = game;

        // create player object
        player = new Player();

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                Constants.CANVAS_WIDTH,
                Constants.CANVAS_HEIGHT);
        viewport = new ExtendViewport(Constants.CANVAS_WIDTH,
                Constants.CANVAS_HEIGHT, camera);

        debug = true;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // update objects
        player.update(viewport);

        // draw background
        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 0, 0, 1);
        game.shapeRenderer.rect(0, 0, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        game.shapeRenderer.end();

        // draw sprites
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        if (debug) {
            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            // plot game area border
            game.shapeRenderer.setColor((float) (0.3), (float) (0.4), (float) (0.9), 1);
            game.shapeRenderer.rect(Constants.GAME_LEFT,
                    Constants.GAME_BOTTOM,
                    Constants.GAME_RIGHT - Constants.GAME_LEFT,
                    Constants.GAME_TOP - Constants.GAME_BOTTOM);

            game.shapeRenderer.end();
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        player.dispose();
    }
}
