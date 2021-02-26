package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main game screen
 */

public class GameScreen implements Screen, InputProcessor {

    private final CoolSodaCan game;
    private final Player player;
    private final Viewport viewport;
    private final Stage bannerStage;
    private final Array<GameObject> gameObjectArray;
    private final TextureAtlas atlas;
    private float timeElapsed;
    private float nextGrass;
    private float nextPlant;
    private float nextAnimal;

    GameScreen(CoolSodaCan game) {
        this.game = game;
        timeElapsed = 0;

        // Load assets
        game.manager.load("graphics/graphics.atlas", TextureAtlas.class);
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;
        param.magFilter = Texture.TextureFilter.Linear;
        game.manager.load("banner/banner_left.jpg", Texture.class, param);
        game.manager.load("banner/banner_right.jpg", Texture.class, param);
        game.manager.finishLoading();

        atlas = game.manager.get("graphics/graphics.atlas", TextureAtlas.class);

        // create player object
        player = new Player(game.getGameHeight(), atlas);

        // create game objects
        gameObjectArray = new Array<>();

        // Create grass
        int nGrass = MathUtils.round(MathUtils.randomTriangular(5, 20));
        for (int i = 0; i < nGrass; i++) {
            gameObjectArray.add(new Grass(MathUtils.random(0, game.getGameHeight()), atlas));
        }
        nextGrass = MathUtils.randomTriangular(0, 256) / Constants.WORLD_MOVEMENT_SPEED;

        // Create plants
        int nPlant = MathUtils.round(MathUtils.randomTriangular(2, 5));
        for (int i = 0; i < nPlant; i++) {
            gameObjectArray.add(new Plant(MathUtils.random(0, game.getGameHeight()), atlas));
        }
        nextPlant = MathUtils.randomTriangular(0, 640) / Constants.WORLD_MOVEMENT_SPEED;

        // Create animals
        int nAnimal = MathUtils.round(MathUtils.randomTriangular(3, 6));
        for (int i = 0; i < nAnimal; i++) {
            gameObjectArray.add(new Animal(MathUtils.random(0, game.getGameHeight()), atlas));
        }
        nextAnimal = MathUtils.randomTriangular(0, 420) / Constants.WORLD_MOVEMENT_SPEED;

        // Sort gameObjectArray so we can render in reverse Y order
        gameObjectArray.sort();

        // create the game viewport
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH, game.getGameHeight());
        viewport = new FitViewport(Constants.GAME_WIDTH,
                game.getGameHeight(), camera);

        // Create the side banners.
        // These are in a different Viewport to game objects so they can be wholly or partially "off-screen"
        FillViewport bannerViewport = new FillViewport(Constants.BANNER_WIDTH, Constants.BANNER_HEIGHT);
        bannerStage = new Stage(bannerViewport);
        Texture bannerLeftTexture = game.manager.get("banner/banner_left.jpg", Texture.class);
        Texture bannerRightTexture = game.manager.get("banner/banner_right.jpg", Texture.class);
        Image bannerLeftImage = new Image(bannerLeftTexture);
        bannerLeftImage.setPosition(0, 0);
        bannerStage.addActor(bannerLeftImage);
        Image bannerRightImage = new Image(bannerRightTexture);
        bannerRightImage.setPosition(bannerStage.getWidth() - bannerRightTexture.getWidth(), 0);
        bannerStage.addActor(bannerRightImage);

        Gdx.input.setInputProcessor(this);

        Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(
                MathUtils.round(Gdx.graphics.getBackBufferWidth() * Constants.CURSOR_START_X),
                MathUtils.round(Gdx.graphics.getBackBufferHeight() * Constants.CURSOR_START_Y));
    }

    private void addAnimal() {
        gameObjectArray.add(new Animal(game.getGameHeight(), atlas));
        nextAnimal = timeElapsed+ MathUtils.randomTriangular(0, 420) / Constants.WORLD_MOVEMENT_SPEED;
    }

    private void addGrass() {
        gameObjectArray.add(new Grass(game.getGameHeight(), atlas));
        nextGrass = timeElapsed + MathUtils.randomTriangular(0, 256) / Constants.WORLD_MOVEMENT_SPEED;
    }

    private void addPlant() {
        gameObjectArray.add(new Plant(game.getGameHeight(), atlas));
        nextPlant = timeElapsed + MathUtils.randomTriangular(0, 640) / Constants.WORLD_MOVEMENT_SPEED;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timeElapsed += delta;

        ScreenUtils.clear(Constants.BACKGROUND_COLOUR);
        viewport.getCamera().update();

        // Remove old objects
        for (int i = 0; i < gameObjectArray.size; i++) {
            if (gameObjectArray.get(i).getTopY() < 0) {
                gameObjectArray.removeIndex(i);
            }
        }

        // Add new objects to top of screen
        if (timeElapsed > nextAnimal) {
            addAnimal();
        }
        if (timeElapsed > nextGrass) {
            addGrass();
        }
        if (timeElapsed > nextPlant) {
            addPlant();
        }

        // update objects
        for (GameObject g : gameObjectArray) {
            g.update(delta);
        }
        player.update(delta);

        // draw sprites
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        for (int i = gameObjectArray.size - 1; i >= 0; i--) {
            gameObjectArray.get(i).draw(game.batch);
        }
        player.draw(game.batch);
        game.batch.end();

        // Draw side banners
        bannerStage.getViewport().apply();
        bannerStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        bannerStage.getViewport().update(width, height);
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
        Gdx.input.setCursorCatched(false);
        bannerStage.dispose();
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
            case Input.Keys.C:
                Gdx.input.setCursorCatched(!Gdx.input.isCursorCatched());
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
