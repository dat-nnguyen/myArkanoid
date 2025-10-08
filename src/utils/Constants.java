package utils;

public class Constants {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final float EPSILON = 1e-6f;
    /**
     * EPSILON is floating point-tolerance
     * when you compare (0.1f + 0.2f) to 0.3f, it will return false
     * In this case, we have to compare absolute value ((0,1f + 0.2f) - 0.3f) < EPSILON
     * The smaller the EPSILON, the more precise the comparison
     */
    public static int BALL_SIZE = 16;
    public static int PADDLE_WIDTH = 100;
    public static int PADDLE_HEIGHT = 20;

    public static float BALL_SPEED = 300f;
    public static float PADDLE_SPEED = 450.0f;

}
