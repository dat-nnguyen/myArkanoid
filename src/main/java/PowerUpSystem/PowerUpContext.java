package PowerUpSystem;

import core.GameLoop;
import entities.Ball;
import entities.Paddle;
import audio.SoundManager;
import java.util.ArrayList;

/**
 * Context class that holds references to game objects.
 * UPDATED: Added GameLoop reference for score multiplier and slow motion.
 * UPDATED AGAIN: Added SoundManager for dependency injection.
 */
public class PowerUpContext {
    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Ball> ballList;
    private final SoundManager soundManager;
    private GameLoop gameLoop; // Added for score and time scale access

    public PowerUpContext(Ball ball, Paddle paddle, ArrayList<Ball> ballList, SoundManager soundManager) {
        this.ball = ball;
        this.paddle = paddle;
        this.ballList = ballList;
        this.soundManager = soundManager;
        this.gameLoop = null; // Will be set later
    }

    // Getters
    public Ball getBall() { return ball; }
    public Paddle getPaddle() { return paddle; }
    public ArrayList<Ball> getBallList() { return ballList; }
    public GameLoop getGameLoop() { return gameLoop; }
    public SoundManager getSoundManager() { return soundManager; }

    // Setter for GameLoop (called after PowerUpManager creation)
    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }
}