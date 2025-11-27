package PowerUpSystem;

/**
 * Increases speed of ALL balls (main + extra) temporarily.
 */
public class FastBallPowerUp extends BallSpeedPowerUp {

    public FastBallPowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.FAST_BALL);
    }

    @Override
    protected double getSpeedMultiplier() {
        return PowerUpConfig.FAST_BALL_MULTIPLIER;
    }

    @Override
    protected String getLogIcon() {
        return "⚡";
    }
}