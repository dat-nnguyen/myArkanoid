package PowerUpSystem;

import core.GameLoop;
import entities.Ball;
import entities.Paddle;
import java.util.ArrayList;

/**
 * Context class that holds references to game objects.
 * UPDATED: Added GameLoop reference for score multiplier and slow motion.
 */
public class PowerUpContext {
    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Ball> ballList;
    private GameLoop gameLoop; // Added for score and time scale access

    public PowerUpContext(Ball ball, Paddle paddle, ArrayList<Ball> ballList) {
        this.ball = ball;
        this.paddle = paddle;
        this.ballList = ballList;
        this.gameLoop = null; // Will be set later
    }

    // Getters
    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public ArrayList<Ball> getBallList() { return ballList; }
    public GameLoop getGameLoop() { return gameLoop; }

    // Setter for GameLoop (called after PowerUpManager creation)
    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }
}