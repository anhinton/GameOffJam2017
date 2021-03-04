package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private final Stage uiStage;
    private final TextureAtlas atlas;
    private final Array<GameObject> gameObjectArray;
    private final Array<Hittable> hittableArray;
    private final Array<AnimatedCan> animatedCanArray;
    private final Label.LabelStyle uiLabelStyle;
    private final Table uiTable;
    private final TextButton.TextButtonStyle uiButtonStyle;
    private float nextAnimatedCan;
    private float timeElapsed;
    private float nextGrass;
    private float nextPlant;
    private float nextAnimal;
    private boolean playerIsFiring;
    private int cansThrown;
    private int cansDelivered;
    private int score;
    private Label cansThrownLabel;
    private Label cansDeliveredLabel;
    private Label scoreLabel;
    private Label timeLabel;

    GameScreen(CoolSodaCan game) {
        this.game = game;
        timeElapsed = 0;
        playerIsFiring = false;
        cansThrown = 0;
        cansDelivered = 0;
        score = 0;

        BitmapFont gameUiFont = game.fontLoader.getGameUiFont(game.manager);

        atlas = game.manager.get("graphics/graphics.atlas", TextureAtlas.class);

        // create player object
        Constants.PlayerName playerName = Constants.PlayerName.values()[MathUtils.random(Constants.PlayerName.values().length - 1)];
        player = new Player(game.getGameHeight(), atlas, playerName);

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
        Viewport bannerViewport = new FillViewport(Constants.BANNER_WIDTH, Constants.BANNER_HEIGHT);
        bannerStage = new Stage(bannerViewport);
        Texture bannerLeftTexture = game.manager.get("banner/banner_left.jpg", Texture.class);
        Texture bannerRightTexture = game.manager.get("banner/banner_right.jpg", Texture.class);
        Image bannerLeftImage = new Image(bannerLeftTexture);
        bannerLeftImage.setPosition(0, 0);
        bannerStage.addActor(bannerLeftImage);
        Image bannerRightImage = new Image(bannerRightTexture);
        bannerRightImage.setPosition(bannerStage.getWidth() - bannerRightTexture.getWidth(), 0);
        bannerStage.addActor(bannerRightImage);

        // Create the UI
        Viewport uiViewport = new FitViewport(game.getGameUiWidth(), Gdx.graphics.getBackBufferHeight());
        uiStage = new Stage(uiViewport);
        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);
        uiLabelStyle = new Label.LabelStyle(gameUiFont, Color.WHITE);
        uiButtonStyle = new TextButton.TextButtonStyle(game.skin.get("default", TextButton.TextButtonStyle.class));

        showGameUi();

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(
                MathUtils.round(Gdx.graphics.getBackBufferWidth() * Constants.CURSOR_START_X),
                MathUtils.round(Gdx.graphics.getBackBufferHeight() * Constants.CURSOR_START_Y));
    }

    private void showGameUi() {
        uiTable.clear();
        uiTable.top().left();

        cansThrownLabel = new Label("", uiLabelStyle);
        incrementThrown(0);
        uiTable.add(cansThrownLabel);
        uiTable.row();

        cansDeliveredLabel = new Label("", uiLabelStyle);
        incrementDelivered(0);
        uiTable.add(cansDeliveredLabel);
        uiTable.row();

        scoreLabel = new Label("", uiLabelStyle);
        incrementScore(0);
        uiTable.add(scoreLabel);
        uiTable.row();

        timeLabel = new Label("00:00", uiLabelStyle);
        uiTable.add(timeLabel);
        uiTable.row();

        Label exitLabel = new Label(game.bundle.get("gameUiDesktopExitLabel"), uiLabelStyle);
        uiTable.add(exitLabel);
        uiTable.row();

        TextButton testButton = new TextButton("Good one", game.skin, "default");
        uiTable.add(testButton);
    }

    private void incrementDelivered(int nCans) {
        cansDelivered += nCans;
        cansDeliveredLabel.setText(game.bundle.get("gameUiDeliveredLabel") + ": " + cansDelivered);
    }

    private void incrementScore(int pointsScored) {
        score += pointsScored;
        scoreLabel.setText(
                game.bundle.get("gameUiScoreLabel") + ": "
                        + game.formatter.printScore(score));
    }

    private void incrementThrown(int nThrown) {
        cansThrown += nThrown;
        cansThrownLabel.setText(game.bundle.get("gameUiThrownLabel") + ": " + cansThrown);
    }

    private void spawnAnimal() {
        Animal animal = new Animal(game.getGameHeight(), atlas);
        gameObjectArray.add(animal);
        hittableArray.add(animal);
        nextAnimal = timeElapsed + MathUtils.randomTriangular(0, Constants.MAX_ANIMAL_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;
    }

    private void spawnGrass() {
        gameObjectArray.add(new Grass(game.getGameHeight(), atlas));
        nextGrass = timeElapsed + MathUtils.randomTriangular(0, Constants.MAX_GRASS_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;
    }

    private void spawnPlant() {
        Plant plant = new Plant(game.getGameHeight(), atlas);
        gameObjectArray.add(plant);
        hittableArray.add(plant);
        nextPlant = timeElapsed + MathUtils.randomTriangular(0, Constants.MAX_PLANT_DISTANCE) / Constants.WORLD_MOVEMENT_SPEED;
    }

    private void throwCan() {
        animatedCanArray.add(new AnimatedCan(player, atlas));
        nextAnimatedCan = timeElapsed + Constants.ANIMATED_CAN_DISTANCE / Constants.ANIMATED_CAN_SPEED;
        incrementThrown(1);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timeElapsed += delta;
        int minutes = (int) (timeElapsed / 60);
        int seconds = (int) (timeElapsed % 60);
        timeLabel.setText(game.formatter.zeroPadTime(minutes, game.bundle.getLocale()) + ":" + game.formatter.zeroPadTime(seconds, game.bundle.getLocale()));

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
            spawnAnimal();
        }
        if (timeElapsed > nextGrass) {
            spawnGrass();
        }
        if (timeElapsed > nextPlant) {
            spawnPlant();
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
                        incrementDelivered(h.getSodasDrunk());
                        incrementScore(h.getScore());
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

        // Draw side banners
        bannerStage.getViewport().apply();
        bannerStage.draw();

        // Draw game UI
        uiStage.getViewport().apply();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        bannerStage.getViewport().update(width, height);
        uiStage.getViewport().update(width, height);
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
        uiStage.dispose();
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
