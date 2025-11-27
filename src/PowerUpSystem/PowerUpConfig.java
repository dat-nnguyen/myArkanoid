package PowerUpSystem;

import utils.Constants;

/**
 * Configuration constants for all power-ups.
 * Centralizes multipliers, limits, and other magic numbers.
 */
public class PowerUpConfig {

    // === PADDLE SIZE MULTIPLIERS ===
    public static final double EXPAND_PADDLE_MULTIPLIER = 1.5;
    public static final double SHRINK_PADDLE_MULTIPLIER = 0.6;

    // === BALL SPEED MULTIPLIERS ===
    public static final double FAST_BALL_MULTIPLIER = 1.3;
    public static final double SLOW_BALL_MULTIPLIER = 0.7;

    // === TIME SCALE ===
    public static final double SLOW_MOTION_TIME_SCALE = 0.5;
    public static final double NORMAL_TIME_SCALE = 1.0;

    // === LIMITS ===
    public static final int MIN_PADDLE_WIDTH = 80;
    public static final int MAX_PADDLE_WIDTH = Constants.PLAY_SCREEN_WIDTH / 2;
    public static final double MIN_BALL_SPEED = 200.0;

    // === SCORE ===
    public static final int SCORE_MULTIPLIER_VALUE = 2;

    // === MULTI-BALL ===
    public static final int MULTI_BALL_COUNT = 2;
    public static final double MULTI_BALL_ANGLE_OFFSET = Math.PI / 6; // 30 degrees

}