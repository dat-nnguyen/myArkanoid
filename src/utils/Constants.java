package utils;

public class Constants {

    public static final int SCREEN_WIDTH = 1280; // Default window width.
    public static final int SCREEN_HEIGHT = 930; // Default window height.
    public static final double PADDLE_SPEED = 800.0; // Default paddle movement speed.
    public static final int PADDLE_WIDTH = 200;
    public static final int PADDLE_HEIGHT = 22;
    public static final double PADDLE_START_POSITION_X = (SCREEN_WIDTH - PADDLE_WIDTH) / 2.0;
    public static final double PADDLE_START_POSITION_Y = SCREEN_HEIGHT - PADDLE_HEIGHT - 30.0;
    public static final double BALL_SPEED = 500.0; // Default ball movement speed.
    public static final int BALL_RADIUS = 10; // Default ball radius.
    public static final int BALL_LIVES = 3; // Default ball lives.
    public static final double BALL_START_POSITION_X = (SCREEN_WIDTH - BALL_RADIUS * 2) / 2.0; // Default ball spawn positionX.
    public static final double BALL_START_POSITION_Y = SCREEN_HEIGHT - (BALL_RADIUS * 2) - 300; // Default ball spawn positionY.
    public static final double EPSILON = 1e-18; // Calculating errors in real number operations.
    public static final int MARGIN_WIDTH = 200;
    public static  final int MARGIN_HEIGHT = 80;
    public static final int PLAY_SCREEN_WIDTH = SCREEN_WIDTH - 2 * MARGIN_WIDTH;
    public static final int PLAY_SCREEN_HEIGHT = SCREEN_HEIGHT - MARGIN_HEIGHT;
    public static final int BRICK_WIDTH = 69;
    public static final int BRICK_HEIGHT = 37;
    public static final int BRICK_SPACING = 4;
    public static final String BALL_TEXTURE_PATH = "/images/gameImages/ball.png";
    public static final String PADDLE_TEXTURE_PATH = "/images/gameImages/paddle.png";

    public static final int POWERUP_WIDTH = 30;
    public static final int POWERUP_HEIGHT = 30;
    public static final double POWERUP_FALL_SPEED = 200.0;

}