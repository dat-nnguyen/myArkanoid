package utils;

public class Constants {

    public static final int SCREEN_WIDTH = 800; // Default window width.
    public static final int SCREEN_HEIGHT = 936; // Default window height.
    public static final double PADDLE_SPEED = 600.0; // Default paddle movement speed.
    public static final double BALL_SPEED = 720.0; // Default ball movement speed.
    public static final int BALL_RADIUS = 10; // Default ball radius.
    public static final int BALL_LIVES = 3; // Default ball lives.
    public static final double START_POSITION_X = (SCREEN_WIDTH - BALL_RADIUS * 2)/ 2.0; // Default ball spawn positionX.
    public static final double START_POSITION_Y = SCREEN_HEIGHT - (BALL_RADIUS * 2) - 80; // Default ball spawn positionY.
    public static final double EPSILON = 1e-9; // Calculating errors in real number operations.

}
