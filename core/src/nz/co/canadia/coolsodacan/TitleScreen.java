package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class TitleScreen implements Screen, InputProcessor {
    private final CoolSodaCan game;
    private final Stage stage;
    private final Table table;
    private final int padding;
    private final float buttonHeight;
    private final float buttonWidth;
    private CurrentMenu currentMenu;

    private enum CurrentMenu { MAIN, STATISTICS, RESET_STATISTICS, SETTINGS, CREDITS}

    public TitleScreen(CoolSodaCan game) {
        this.game = game;
        padding = game.getMenuUiPadding();
        buttonWidth = game.getUiWidth() * Constants.TITLEMENU_BUTTON_WIDTH;
        buttonHeight = buttonWidth * Constants.TITLEMENU_BUTTON_RELATIVE_HEIGHT;

        FitViewport uiViewport = new FitViewport(game.getUiWidth(), Gdx.graphics.getBackBufferHeight());
        stage = new Stage(uiViewport);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        showMainMenu();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void showMainMenu() {
        currentMenu = CurrentMenu.MAIN;
        table.clearChildren();
        table.center();
        table.pad(padding);

        Label titleLabel = new Label(Constants.GAME_NAME, game.skin, "titlemenu");

        TextButton startButton = new TextButton(game.bundle.get("titlescreenStartButton"), game.skin, "titlemenu");
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton statsButton = new TextButton(game.bundle.get("titlescreenStatisticsButton"), game.skin, "titlemenu");
        statsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showStatistics();
            }
        });

        TextButton settingsButton = new TextButton(game.bundle.get("titlescreenSettingsButton"), game.skin, "titlemenu");

        table.add(titleLabel).space(padding);
        table.row();
        table.add(startButton).space(padding)
                .prefSize(buttonWidth,
                        buttonHeight);
        table.row();
        table.add(statsButton).space(padding)
                .prefSize(buttonWidth,
                        buttonHeight);
        table.row();
        table.add(settingsButton).space(padding)
                .prefSize(buttonWidth,
                        buttonHeight);

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            TextButton quitButton = new TextButton(game.bundle.get("titlescreenQuitButton"), game.skin, "titlemenu");
            quitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    quit();
                }
            });
            table.row();
            table.add(quitButton).space(padding)
                    .prefSize(buttonWidth,
                            buttonHeight);
        }
    }

    private void showSettingsMenu() {
    }

    private void showStatistics() {
        currentMenu = CurrentMenu.STATISTICS;
        table.clearChildren();
        table.pad(padding);

        Label headingLabel = new Label(game.bundle.get("titlescreenStatisticsButton"), game.skin, "titlemenu");

        String bp = game.bundle.get("bulletPoint") + " ";
        String nl = "\n";
        String statisticsString = bp + game.bundle.get("statisticsThrown") + ": " + game.statistics.getTotalCansThrown() + nl +
                bp + game.bundle.get("statisticsDrunk") + ": " + game.statistics.getTotalCansDelivered() + nl
                + bp + game.bundle.get("statisticsHighScore") + ": " + game.formatter.printScore(game.statistics.getHighScore()) + nl
                + bp + game.bundle.get("statisticsPoints") + ": " + game.formatter.printScore(game.statistics.getTotalPointsScored()) + nl
                + bp + game.bundle.get("statisticsAnimalsQuenched") + ": " + game.statistics.getAnimalsSuperhit() + nl
                + bp + game.bundle.get("statisticsPlantsDestroyed") + ": " + game.statistics.getPlantsSuperHit() + nl
                + bp + game.bundle.get("statisticsLongestSession") + ": " + game.displayTime(game.statistics.getLongestSession()) + nl
                + bp + game.bundle.get("statisticsTime") + ": " + game.displayTime(game.statistics.getTotalTimePlayed()) + nl
                + bp + game.bundle.get("statisticsUnlocked") + ": ";
        Label statisticsLabel = new Label(statisticsString, game.skin, "statistics");
        statisticsLabel.setWrap(true);
        statisticsLabel.setAlignment(Align.top);

        TextButton backButton = new TextButton(game.bundle.get("backButton"), game.skin, "titlemenu");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBack();
            }
        });

        TextButton resetStatisticsButton = new TextButton(game.bundle.get("statisticsResetButton"), game.skin, "titlemenu");
        resetStatisticsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showResetStatistics();
            }
        });

        table.add(headingLabel).space(padding);
        table.row();
        table.add(statisticsLabel)
                .prefWidth(Gdx.graphics.getBackBufferWidth())
                .top();
        table.row();
        table.add(backButton).space(padding).prefSize(buttonWidth, buttonHeight);
        table.row();
        table.add(resetStatisticsButton).space(padding).prefSize(buttonWidth, buttonHeight);
    }

    private void showResetStatistics() {
        currentMenu = CurrentMenu.RESET_STATISTICS;
        table.clearChildren();
        table.pad(padding);

        Label resetHeadingLabel = new Label(game.bundle.get("statisticsResetButton"), game.skin, "titlemenu");

        Label resetQuestionLabel = new Label(game.bundle.get("statisticsResetQuestion"), game.skin, "statistics");
        resetQuestionLabel.setWrap(true);
        resetQuestionLabel.setAlignment(Align.center);

        TextButton resetNoButton = new TextButton(game.bundle.get("statisticsResetNo"), game.skin, "titlemenu");
        resetNoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goBack();
            }
        });

        TextButton resetYesButton = new TextButton(game.bundle.get("statisticsResetYes"), game.skin, "titlemenu");
        resetYesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.statistics.clear();
                game.statistics.save();
                game.statistics.load();
                showStatistics();
            }
        });

        table.add(resetHeadingLabel).space(padding);
        table.row();
        table.add(resetQuestionLabel).space(padding).prefWidth(Gdx.graphics.getBackBufferWidth());
        table.row();
        table.add(resetNoButton).space(padding).prefSize(buttonWidth, buttonHeight);
        table.row();
        table.add(resetYesButton).space(padding).prefSize(buttonWidth, buttonHeight);
    }

    private void goBack() {
        switch (currentMenu) {
            case MAIN:
                quit();
                break;
            case SETTINGS:
                showMainMenu();
                break;
            case STATISTICS:
                showMainMenu();
                break;
            case RESET_STATISTICS:
                showStatistics();
                break;
            case CREDITS:
                showSettingsMenu();
                break;
        }
    }

    private void quit() {
        Gdx.app.exit();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Constants.BACKGROUND_COLOUR);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        switch (keycode) {
            case Input.Keys.BACK:
            case Input.Keys.ESCAPE:
                goBack();
                break;
            case Input.Keys.F:
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(Constants.DESKTOP_WIDTH, Constants.DESKTOP_HEIGHT);
                } else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
                showMainMenu();
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
