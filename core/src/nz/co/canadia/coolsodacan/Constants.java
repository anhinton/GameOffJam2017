package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * Game constants
 */

public class Constants {
    public static final String GAME_NAME = "Cool Soda Can by Ashley Noel Hinton";

    public static final Color BACKGROUND_COLOUR = new Color(0f / 255, 205f / 255, 111f / 255, 1);
    public static final int GAME_WIDTH = 720;
    public static final int GAME_HEIGHT = 1280;

    // app dimensions for Desktop
    public static final int DESKTOP_WIDTH = MathUtils.round(GAME_WIDTH * 2f / 3);
    public static final int DESKTOP_HEIGHT = MathUtils.round(GAME_HEIGHT * 2f / 3);

    public static final float PLAYER_SPEED = 640f;
    static final float PLAYER_MOVEMENT_THRESHOLD = 80f;
}