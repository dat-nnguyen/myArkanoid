package PowerUpSystem;

import entities.Ball;
import utils.Constants;

/**
 * Slows down the ball temporarily.
 * FIXED: Doesn't stack - uses saved original speed.
 */
public class SlowBallPowerUp extends PowerUp {

    private double originalSpeed;

    public SlowBallPowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.SLOW_BALL);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        Ball ball = context.getBall();

        // Save current speed
        originalSpeed = ball.getSpeed();

        // Calculate new speed from ORIGINAL (Constants)
        double baseSpeed = Constants.BALL_SPEED;
        double newSpeed = baseSpeed * 0.7;

        // Clamp to min
        double minSpeed = 200.0;
        if (newSpeed < minSpeed) {
            newSpeed = minSpeed;
        }

        ball.setSpeed(newSpeed);

        System.out.println("🟢 Ball slowed: " + originalSpeed + " → " + newSpeed);
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        Ball ball = context.getBall();

        // Return to ORIGINAL speed
        ball.setSpeed(Constants.BALL_SPEED);

        System.out.println("🟢 Ball returned to normal speed: " + Constants.BALL_SPEED);
    }
}