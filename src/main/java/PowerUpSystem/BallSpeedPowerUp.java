package PowerUpSystem;

import entities.Ball;
import utils.Constants;
import java.util.ArrayList;

/**
 * Base class for ball speed power-ups (FastBall, SlowBall).
 * Uses Template Method pattern to eliminate code duplication.
 */
public abstract class BallSpeedPowerUp extends TimedPowerUp {

    public BallSpeedPowerUp(double positionX, double positionY, PowerUpType type) {
        super(positionX, positionY, type);
    }

    /**
     * Subclasses define the speed multiplier.
     * @return Multiplier to apply to base speed (e.g., 1.3 for fast, 0.7 for slow)
     */
    protected abstract double getSpeedMultiplier();

    /**
     * Get the emoji/icon for logging.
     */
    protected abstract String getLogIcon();

    @Override
    public void applyEffect(PowerUpContext context) {
        Ball mainBall = context.getBall();
        ArrayList<Ball> ballList = context.getBallList();

        // Calculate new speed from BASE speed
        double baseSpeed = Constants.BALL_SPEED;
        double newSpeed = baseSpeed * getSpeedMultiplier();

        // Clamp to minimum if needed (for slow ball)
        if (newSpeed < PowerUpConfig.MIN_BALL_SPEED) {
            newSpeed = PowerUpConfig.MIN_BALL_SPEED;
        }

        // Apply to main ball
        if (mainBall.getLives() > 0) {
            mainBall.setSpeed(newSpeed);
        }

        // Apply to ALL extra balls
        for (Ball extraBall : ballList) {
            extraBall.setSpeed(newSpeed);
        }

        System.out.println(getLogIcon() + " Ball speed changed: " + baseSpeed + " → " + newSpeed);
        System.out.println(getLogIcon() + " Applied to " +
                (mainBall.getLives() > 0 ? 1 : 0) + " main ball + " +
                ballList.size() + " extra balls");
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        Ball mainBall = context.getBall();
        ArrayList<Ball> ballList = context.getBallList();

        // Return to BASE speed
        double baseSpeed = Constants.BALL_SPEED;

        // Remove from main ball
        if (mainBall.getLives() > 0) {
            mainBall.setSpeed(baseSpeed);
        }

        // Remove from ALL extra balls
        for (Ball extraBall : ballList) {
            extraBall.setSpeed(baseSpeed);
        }

        System.out.println(getLogIcon() + " Ball speed returned to: " + baseSpeed);
    }
}