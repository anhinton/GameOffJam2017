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
    public static final int BANNER_WIDTH = 1280;
    public static final int BANNER_HEIGHT = 720;

    // app dimensions for Desktop
    public static final int DESKTOP_WIDTH = 1280;
    public static final int DESKTOP_HEIGHT = 720;

    // app dimensions for Web
    public static final int HTML_WIDTH = 1280;
    public static final int HTML_HEIGHT = 720;

    // Game constants
    public static final float PLAYER_SPEED = 2560f;
    public static final float WORLD_MOVEMENT_SPEED = 160f;
    public static final float CURSOR_START_X = 1 / 2f;
    public static final float CURSOR_START_Y = 1 / 2f;

    // Player constants
    public enum PlayerName { BLUE, ORANGE, PURPLE, SILVER, YELLOW }
    public static final String BLUE_NAME = "blue_soda_small";
    public static final String ORANGE_NAME = "orange_soda_small";
    public static final String PURPLE_NAME = "purple_soda_small";
    public static final String SILVER_NAME = "silver_soda_small";
    public static final String YELLOW_NAME = "yellow_soda_small";
    public static final String BLUE_ANIM_NAME = "blue_anim";
    public static final String ORANGE_ANIM_NAME = "orange_anim";
    public static final String PURPLE_ANIM_NAME = "purple_anim";
    public static final String SILVER_ANIM_NAME = "silver_anim";
    public static final String YELLOW_ANIM_NAME = "yellow_anim";

    // Particle effect constants
    public static final Color BLUE_EXPLOSION = new Color(0.165f, 0.427f, 0.925f, 1);
    public static final Color SILVER_EXPLOSION = new Color(0.808f, 0.808f,0.745f, 1);
    public static final Color ORANGE_EXPLOSION = new Color(0.965f, 0.482f,0.122f, 1);
    public static final Color PURPLE_EXPLOSION = new Color(0.780f, 0, 0.6f, 1);
    public static final Color YELLOW_EXPLOSION = new Color(0.965f, 0.949f, 0.121f, 1);

    // Spawn constants
    public static final int MIN_GRASS_START = 5;
    public static final int MAX_GRASS_START = 20;
    public static final int MAX_GRASS_DISTANCE = MathUtils.round((float) GAME_HEIGHT / MIN_GRASS_START);
    public static final int MIN_PLANT_START = 2;
    public static final int MAX_PLANT_START = 5;
    public static final int MAX_PLANT_DISTANCE = MathUtils.round((float) GAME_HEIGHT / MIN_PLANT_START);
    public static final int MIN_ANIMAL_START = 3;
    public static final int MAX_ANIMAL_START = 6;
    public static final int MAX_ANIMAL_DISTANCE = MathUtils.round((float) GAME_HEIGHT / MIN_ANIMAL_START);

    // Hittable constants
    public enum HittableState { NORMAL, HIT, SUPER_HIT }
    public enum AnimatedCanState { ACTIVE, INACTIVE }

    // Animal constants
    public static final float DEGREES_PER_SECOND = 10.0f;
    public static final float SHAKE_AMPLITUDE_IN_DEGREES = 5.0f;

    // Animation constants
    public static final float CAN_FRAME_DURATION = 0.066f;
    public static final float ANIMATED_CAN_SPEED = GAME_HEIGHT / 4f;
    public static final float ANIMATED_CAN_DISTANCE = GAME_HEIGHT / 4f;
    // My player cans have drop shadows, meaning the "centre" of the can is not in the middle.
    // As at 2021-03-01 I have 80px wide cans with a 10px drop shadow
    public static final float PLAY_CENTRE_OFFSET = 4 / 9f;
}