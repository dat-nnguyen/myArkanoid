package PowerUpSystem;

import entities.Ball;
import utils.Constants;

/**
 * Adds 1 extra life to player (instant effect).
 * Maximum lives capped at 3.
 */
public class ExtraLifePowerUp extends InstantPowerUp {

    public ExtraLifePowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.EXTRA_LIFE);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        Ball mainBall = context.getBall();

        int currentLives = mainBall.getLives();
        int maxLives = Constants.BALL_LIVES; // 3

        // Only add life if not already at max
        if (currentLives < maxLives) {
            mainBall.setLives(currentLives + 1);
            System.out.println("❤️ Extra Life! Lives: " + currentLives + " → " + mainBall.getLives());
        } else {
            System.out.println("❤️ Already at max lives (" + maxLives + "), no effect");
        }
    }
}