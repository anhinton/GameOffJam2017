package nz.co.canadia.gameoffjam2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main game screen
 */

public class GameScreen implements Screen {

    private final GameOffJam game;
    private TextureRegion region;
    private Texture img;
    private Sprite gpig;
    private Texture texture;
    private OrthographicCamera camera;
    private Viewport viewport;

    GameScreen(GameOffJam game) {
        this.game = game;
        img = new Texture("badlogic.jpg");
        texture = new Texture(Gdx.files.internal("gpig.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        region = new TextureRegion(texture, 0,0, texture.getWidth(), texture.getHeight());
        gpig = new Sprite(region);
        gpig.setOrigin(gpig.getWidth() / 2, gpig.getHeight() / 2);
        gpig.setPosition(0, Constants.GAME_HEIGHT / 2);
        gpig.setSize(1, 1);

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                Constants.GAME_WIDTH,
                Constants.GAME_HEIGHT);
        viewport = new ExtendViewport(Constants.GAME_WIDTH,
                Constants.GAME_HEIGHT, camera);
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

        game.shapeRenderer.setProjectionMatrix(camera.combined);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0, 0, 0, 1);
        game.shapeRenderer.rect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        game.shapeRenderer.end();

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(1, 1, 1, 1);
        // plot unit grid
        for (int i = 1; i < Constants.GAME_HEIGHT; i++) {
            game.shapeRenderer.line(0, i, Constants.GAME_WIDTH, i);
        }
        for (int i = 1; i < Constants.GAME_WIDTH; i++) {
            game.shapeRenderer.line(i, 0, i, Constants.GAME_HEIGHT);
        }
        game.shapeRenderer.end();

        game.batch.begin();
        game.batch.draw(img, 0, 0, Constants.GAME_WIDTH / 2,
                Constants.GAME_WIDTH / 2);
        game.batch.draw(img, Constants.GAME_WIDTH * 3 / 4,
                Constants.GAME_HEIGHT - Constants.GAME_WIDTH / 2,
                Constants.GAME_WIDTH / 2,
                Constants.GAME_WIDTH / 2);
        gpig.draw(game.batch);
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
        img.dispose();
    }
}
