package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private final TextureAtlas atlas;
    private final Array<GameObject> gameObjectArray;
    private final Array<Hittable> hittableArray;
    private final Array<AnimatedCan> animatedCanArray;
    private float nextAnimatedCan;
    private float timeElapsed;
    private float nextGrass;
    private float nextPlant;
    private float nextAnimal;
    private boolean playerIsFiring;

    GameScreen(CoolSodaCan game) {
        this.game = game;
        timeElapsed = 0;
        playerIsFiring = false;

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
//        player = new Player(game.getGameHeight(), atlas, "blue_soda_small");
        player = new Player(game.getGameHeight(), atlas, "orange_soda_small");
//        player = new Player(game.getGameHeight(), atlas, "purple_soda_small");
//        player = new Player(game.getGameHeight(), atlas, "silver_soda_small");
//        player = new Player(game.getGameHeight(), atlas, "yellow_soda_small");

        // create game objects
        gameObjectArray = new Array<>();
        hittableArray = new Array<>();

        // Create grass
        int nGrass = MathUtils.round(MathUtils.randomTriangular(
                Constants.MIN_GRASS_START, Constants.MAX_GRASS_START));
        for (int i = 0; i < nGrass; i++) {
            gameObjectArray.add(new Grass(MathUtils.random(0, game.getGameHeight()), atlas));
        }
        nextGrass = MathUtils.randomTriangular(0, Constants.MAX_GRASS_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;

        // Create plants
        int nPlant = MathUtils.round(MathUtils.randomTriangular(
                Constants.MIN_PLANT_START, Constants.MAX_PLANT_START));
        for (int i = 0; i < nPlant; i++) {
            Plant plant = new Plant(MathUtils.random(0, game.getGameHeight()), atlas);
            gameObjectArray.add(plant);
            hittableArray.add(plant);
        }
        nextPlant = MathUtils.randomTriangular(0, Constants.MAX_PLANT_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;

        // Create animals
        int nAnimal = MathUtils.round(MathUtils.randomTriangular(
                Constants.MIN_ANIMAL_START, Constants.MAX_ANIMAL_START));
        for (int i = 0; i < nAnimal; i++) {
            Animal animal = new Animal(MathUtils.random(0, game.getGameHeight()), atlas);
            gameObjectArray.add(animal);
            hittableArray.add(animal);
        }
        nextAnimal = MathUtils.randomTriangular(0, Constants.MAX_ANIMAL_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;

        // Sort gameObjectArray so we can render in reverse Y order
        gameObjectArray.sort();

        // Create AnimatedCan array
        animatedCanArray = new Array<>();
        nextAnimatedCan = 0;

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

    private void throwCan() {
        animatedCanArray.add(new AnimatedCan(player, atlas));
        nextAnimatedCan = timeElapsed + Constants.ANIMATED_CAN_DISTANCE / Constants.ANIMATED_CAN_SPEED;
    }

    private void addAnimal() {
        Animal animal = new Animal(game.getGameHeight(), atlas);
        gameObjectArray.add(animal);
        hittableArray.add(animal);
        nextAnimal = timeElapsed + MathUtils.randomTriangular(0, Constants.MAX_ANIMAL_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;
    }

    private void addGrass() {
        gameObjectArray.add(new Grass(game.getGameHeight(), atlas));
        nextGrass = timeElapsed + MathUtils.randomTriangular(0, Constants.MAX_GRASS_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;
    }

    private void addPlant() {
        Plant plant = new Plant(game.getGameHeight(), atlas);
        gameObjectArray.add(plant);
        hittableArray.add(plant);
        nextPlant = timeElapsed + MathUtils.randomTriangular(0, Constants.MAX_PLANT_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;
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
        for (int i = 0; i < animatedCanArray.size; i++) {
            if (animatedCanArray.get(i).getY() > game.getGameHeight()) {
                animatedCanArray.removeIndex(i);
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

        // Add new cans if player firing
        if (playerIsFiring) {
            if (timeElapsed > nextAnimatedCan) {
                throwCan();
            }
        }

        // Check for can/hittable collisions
        for (AnimatedCan ac : animatedCanArray) {
            if (ac.isActive()) {
                for (Hittable h : hittableArray) {
                    if (ac.getHitBox().overlaps(h.getHitBox()) & h.isHittable()) {
                        h.hit();
                        ac.hit();
                    }
                }
            }
        }

        // update objects
        for (GameObject g : gameObjectArray) {
            g.update(delta);
        }
        for (AnimatedCan ac : animatedCanArray) {
            ac.update(delta);
        }
        player.update(delta);

        // draw sprites
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        // Game objects
        for (int i = gameObjectArray.size - 1; i >= 0; i--) {
            gameObjectArray.get(i).draw(game.batch);
        }
        // Animated Cans
        for (AnimatedCan ac : animatedCanArray) {
            ac.draw(game.batch);
        }
        // Player
        player.draw(game.batch);
        game.batch.end();

        // DEBUG hitboxes
//        game.shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
//        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        game.shapeRenderer.setColor(Color.RED);
//        for (AnimatedCan ac : animatedCanArray) {
//            game.shapeRenderer.rect(ac.getHitBox().x, ac.getHitBox().y, ac.getHitBox().width, ac.getHitBox().height);
//        }
//        game.shapeRenderer.setColor(Color.BLUE);
//        for (Hittable h : hittableArray) {
//            game.shapeRenderer.rect(h.getHitBox().x, h.getHitBox().y, h.getHitBox().width, h.getHitBox().height);
//        }
//        game.shapeRenderer.end();

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
        if (!playerIsFiring) playerIsFiring = true;
        player.setTargetXY(screenX, screenY, viewport);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        playerIsFiring = false;
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
