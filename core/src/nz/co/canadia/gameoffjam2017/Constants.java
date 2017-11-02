package nz.co.canadia.gameoffjam2017;

/**
 * Game constants
 */

public class Constants {
    public static final String GAME_NAME = "GameOff Jam 2017 by Ashley Noel Hinton";

    // display dimensions for Desktop
    public static final int DESKTOP_WIDTH = 960;
    public static final int DESKTOP_HEIGHT = 720;

    // display dimensions for html
    public static final int HTML_WIDTH = DESKTOP_WIDTH * 3 / 4;
    public static final int HTML_HEIGHT = DESKTOP_HEIGHT * 3 / 4;

    // canvas dimensions
    static final int CANVAS_WIDTH = 2560;
    static final int CANVAS_HEIGHT = 1920;

    // game area
    static final int GAME_WIDTH = CANVAS_HEIGHT * 9 / 16;
    static final int GAME_HEIGHT = CANVAS_HEIGHT;
    static final int GAME_LEFT = CANVAS_WIDTH / 2 - GAME_WIDTH / 2;
    static final int GAME_RIGHT = CANVAS_WIDTH / 2 + GAME_WIDTH / 2;
    static final int GAME_BOTTOM = 0;
    static final int GAME_TOP = GAME_HEIGHT;
}
