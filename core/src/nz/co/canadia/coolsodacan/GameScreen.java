package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main game screen
 */

public class GameScreen implements Screen, InputProcessor {

    private final CoolSodaCan game;
    private final Sprite bannerLeftSprite;
    private final Sprite bannerRightSprite;
    private final Player player;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    GameScreen(CoolSodaCan game) {
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        this.game = game;

        game.manager.load("graphics/graphics.atlas", TextureAtlas.class);
        game.manager.load("banner/banner_left.jpg", Texture.class);
        game.manager.load("banner/banner_right.jpg", Texture.class);
        game.manager.finishLoading();

        TextureAtlas atlas = game.manager.get("graphics/graphics.atlas", TextureAtlas.class);

        // create player object
        player = new Player(game.getGameHeight(), atlas);

        int a = Gdx.graphics.getSafeInsetTop();
        int b = Gdx.graphics.getSafeInsetBottom();
        int c = Gdx.graphics.getSafeInsetLeft();
        int d = Gdx.graphics.getSafeInsetRight();

        Texture bannerLeftTexture = game.manager.get("banner/banner_left.jpg", Texture.class);
        bannerLeftTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bannerLeftSprite = new Sprite(bannerLeftTexture);
        bannerLeftSprite.setPosition(-778, 0);
        bannerLeftSprite.setSize(778, game.getGameHeight());
        Texture bannerRightTexture = game.manager.get("banner/banner_right.jpg", Texture.class);
        bannerRightTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bannerRightSprite = new Sprite(bannerRightTexture);
        bannerRightSprite.setPosition(Constants.GAME_WIDTH, 0);
        bannerRightSprite.setSize(778, game.getGameHeight());

        // create the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH, game.getGameHeight());
        viewport = new ExtendViewport(Constants.GAME_WIDTH,
                game.getGameHeight(), camera);

        Gdx.input.setInputProcessor(this);

        boolean debug = true;
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
        bannerLeftSprite.draw(game.batch);
        bannerRightSprite.draw(game.batch);
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

    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.ESCAPE:
            case Input.Keys.BACK:
                Gdx.app.exit();
                break;
            case Input.Keys.F:
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(Constants.DESKTOP_WIDTH, Constants.DESKTOP_HEIGHT);
                } else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
                break;
        }
        return true;
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
