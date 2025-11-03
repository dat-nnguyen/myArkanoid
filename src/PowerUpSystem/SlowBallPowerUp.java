package PowerUpSystem;

/**
 * Decreases speed of ALL balls (main + extra) temporarily.
 * FIXED: Now applies to extra balls too!
 */
public class SlowBallPowerUp extends BallSpeedPowerUp {

    public SlowBallPowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.SLOW_BALL);
    }

    @Override
    protected double getSpeedMultiplier() {
        return PowerUpConfig.SLOW_BALL_MULTIPLIER;
    }

    @Override
    protected String getLogIcon() {
        return "🟢";
    }
}