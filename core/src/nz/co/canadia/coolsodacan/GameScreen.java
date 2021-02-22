package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main game screen
 */

public class GameScreen implements Screen, InputProcessor {

    private final CoolSodaCan game;
    private Player player;
    private OrthographicCamera camera;
    private Viewport viewport;
    private boolean debug;

    GameScreen(CoolSodaCan game) {
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        this.game = game;

        // create player object
        player = new Player();

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        viewport = new ExtendViewport(Constants.GAME_WIDTH,
                Constants.GAME_HEIGHT, camera);

        Gdx.input.setInputProcessor(this);

        debug = true;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOUR.r,
                Constants.BACKGROUND_COLOUR.g,
                Constants.BACKGROUND_COLOUR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // update objects
        player.update(viewport);

        game.shapeRenderer.setProjectionMatrix(camera.combined);

        // draw sprites
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.setTargetXY(screenX, screenY, viewport);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        player.setTargetXY(screenX, screenY, viewport);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        player.setTargetXY(screenX, screenY, viewport);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
