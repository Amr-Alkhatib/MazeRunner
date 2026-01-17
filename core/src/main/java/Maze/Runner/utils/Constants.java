package Maze.Runner.utils;

/**
 * Global game constants: sizes, speeds, limits.
 */
public class Constants {

    // World / tiles
    public static final float TILE_SIZE = 32f;
    public static final float CELL_SIZE = TILE_SIZE;

    // Player
    public static final float PLAYER_WIDTH = TILE_SIZE;
    public static final float PLAYER_HEIGHT = TILE_SIZE;
    public static final int STARTING_LIVES = 3;
    public static final int MAX_LIVES = 5;
    public static final float MOVEMENT_SPEED = 3f;
    public static final float RUN_MULTIPLIER = 1.8f;
    public static final float SPEED_BOOST_MULTIPLIER = 1.5f;

    // Stamina
    public static final float STAMINA_REGEN = 10f;
    public static final float STAMINA_DRAIN = 20f;

    // Damage / invulnerability
    public static final float DAMAGE_FLASH_DURATION = 0.25f;
    public static final float INVULNERABILITY_DURATION = 1.0f;

    // Camera
    public static final float CAMERA_LERP = 5f;
    public static final float MIN_ZOOM = 0.5f;
    public static final float MAX_ZOOM = 2.5f;

    // Scoring / XP
    public static final int SCORE_PER_KEY = 100;
    public static final int SCORE_PER_ENEMY = 50;
    public static final int SCORE_PER_LEVEL = 500;
    public static final int XP_PER_LEVEL = 100;

    // Game modes
    public static final int MAX_SURVIVAL_LEVELS = 5;
    public static final int MIN_ENDLESS_GRID_SIZE = 5;
    public static final int MAX_ENDLESS_GRID_SIZE = 10;

    // Power-up durations
    public static final float SPEED_BOOST_DURATION = 5.0f;
    public static final float SHIELD_DURATION = 3.0f;
    public static final float GHOST_MODE_DURATION = 4.0f;
}
