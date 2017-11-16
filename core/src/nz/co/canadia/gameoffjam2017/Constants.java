package nz.co.canadia.gameoffjam2017;

/**
 * Game constants
 */

public class Constants {
    public static final String GAME_NAME = "GameOff Jam 2017 by Ashley Noel Hinton";

    // app dimensions for Desktop
    public static final int DESKTOP_WIDTH = 800;
    public static final int DESKTOP_HEIGHT = 600;

    // app dimensions for html
    public static final int HTML_WIDTH = DESKTOP_WIDTH;
    public static final int HTML_HEIGHT = DESKTOP_HEIGHT;

    // main display canvas dimensions
    static final float CANVAS_WIDTH = (float) (9.0 / 2);
    static final float CANVAS_HEIGHT = 16 / 2;

    // game area boundaries
    private static final float GAME_MARGIN = CANVAS_WIDTH / 20;
    static final float GAME_LEFT = GAME_MARGIN;
    static final float GAME_RIGHT = CANVAS_WIDTH - GAME_MARGIN;
    static final float GAME_BOTTOM = GAME_MARGIN;
    static final float GAME_TOP = CANVAS_HEIGHT - GAME_MARGIN * 2;

    // Player constants
    static final float PLAYER_WIDTH = 1;
    static final float PLAYER_HEIGHT = 1;
    static final float PLAYER_SPEED = 8;
    static final float PLAYER_MOVEMENT_THRESHOLD = PLAYER_WIDTH / 15;
}