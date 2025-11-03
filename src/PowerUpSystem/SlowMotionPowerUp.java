package PowerUpSystem;

import core.GameLoop;

/**
 * Slows down everything in the game (time scale effect).
 * REFACTORED: Now manages its own effect via GameLoop.
 */
public class SlowMotionPowerUp extends TimedPowerUp {

    public SlowMotionPowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.SLOW_MOTION);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        GameLoop gameLoop = context.getGameLoop();

        if (gameLoop == null) {
            System.err.println("⏱️ Slow Motion failed: GameLoop not set in context!");
            return;
        }

        gameLoop.setTimeScale(PowerUpConfig.SLOW_MOTION_TIME_SCALE);
        System.out.println("⏱️ Slow Motion activated! Time scale: " +
                PowerUpConfig.NORMAL_TIME_SCALE + " → " + PowerUpConfig.SLOW_MOTION_TIME_SCALE);
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        GameLoop gameLoop = context.getGameLoop();

        if (gameLoop == null) {
            System.err.println("⏱️ Slow Motion removal failed: GameLoop not set!");
            return;
        }

        gameLoop.setTimeScale(PowerUpConfig.NORMAL_TIME_SCALE);
        System.out.println("⏱️ Slow Motion ended! Time scale: " +
                PowerUpConfig.SLOW_MOTION_TIME_SCALE + " → " + PowerUpConfig.NORMAL_TIME_SCALE);
    }
}