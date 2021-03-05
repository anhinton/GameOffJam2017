package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import java.io.StringBufferInputStream;

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
    public static final int HTML_WIDTH = 720;
    public static final int HTML_HEIGHT = 720;

    // Game constants
    public static final float PLAYER_SPEED = 2560f;
    public static final float WORLD_MOVEMENT_SPEED = 160f;
    public static final float CURSOR_START_X = 1 / 2f;
    public static final float CURSOR_START_Y = 1 / 2f;

    // Font constants
    public static final Color FONT_COLOR = Color.WHITE;
    public static final Color FONT_SHADOW_COLOR = Color.BLACK;
    public static final int GAMEUI_FONT_SIZE = 32;
    public static final int FONT_SHADOW_OFFSET = 2;
    public static final int UI_PADDING = 10;

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
    // Particle size is set to min of sprite(width/height), scaled by the following
    public static final float PLANT_PARTICLE_SCALE = 1;
    public static final float ANIMAL_PARTICLE_SCALE = 0.5f;

    // Animal constants
    public enum AnimalName { COCO, HORSE01, HORSE02 }
    public static final String COCO_TEXTURE = "coco";
    public static final String HORSE01_TEXTURE = "horse01";
    public static final String HORSE02_TEXTURE = "horse02";
    public static final String COCO_SUPERHIT_TEXTURE = "coco_superhit";
    public static final String HORSE01_SUPERHIT_TEXTURE = "horse01_superhit";
    public static final String HORSE02_SUPERHIT_TEXTURE = "horse02_superhit";
    public static final int ANIMAL_SODAS_DRUNK = 1;
    public static final int ANIMAL_BASE_SCORE = 6;
    public static final int ANIMAL_HIGH_SCORE = 666;

    // Plant constants
    public enum PlantName { FERN01, FLOWER01, TREE01, TREE02}
    public static final String FERN01_TEXTURE = "fern01";
    public static final String FLOWER01_TEXTURE = "flower01";
    public static final String TREE01_TEXTURE = "tree01";
    public static final String TREE02_TEXTURE = "tree02";
    public static final String FERN01_HIT_TEXTURE = "fern01_hit";
    public static final String FLOWER01_HIT_TEXTURE = "flower01_hit";
    public static final String TREE01_HIT_TEXTURE = "tree01_hit";
    public static final String TREE02_HIT_TEXTURE = "tree02_hit";
    public static final int PLANT_SODAS_DRUNK = 0;
    public static final int PLANT_BASE_SCORE = 0;
    public static final int PLANT_HIGH_SCORE = ANIMAL_BASE_SCORE;

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