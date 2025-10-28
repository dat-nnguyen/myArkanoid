package PowerUpSystem;

import entities.Ball;
import entities.Paddle;
import java.util.ArrayList;

/**
 * Context class that holds references to game objects.
 * Used to pass game state to power-ups for applying effects.
 */
public class PowerUpContext {
    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Ball> ballList;

    public PowerUpContext(Ball ball, Paddle paddle, ArrayList<Ball> ballList) {
        this.ball = ball;
        this.paddle = paddle;
        this.ballList = ballList;
    }

    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public ArrayList<Ball> getBallList() { return ballList; }
}
