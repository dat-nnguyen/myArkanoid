package PowerUpSystem;

/**
 * Factory class for creating power-up instances.
 * UPDATED: Added FastBall, ExtraLife, SuddenDeath
 */
public class PowerUpFactory {

    /**
     * Create a power-up of specified type at given position.
     */
    public static PowerUp createPowerUp(double x, double y, PowerUpType type) {
        switch (type) {
            case EXPAND_PADDLE:
                return new ExpandPaddlePowerUp(x, y);
            case SHRINK_PADDLE:
                return new ShrinkPaddlePowerUp(x, y);
            case SLOW_BALL:
                return new SlowBallPowerUp(x, y);
            case MULTI_BALL:
                return new MultiBallPowerUp(x, y);

            // === NEW POWER-UPS ===
            case FAST_BALL:
                return new FastBallPowerUp(x, y);
            case EXTRA_LIFE:
                return new ExtraLifePowerUp(x, y);
            case SUDDEN_DEATH:
                return new SuddenDeathPowerUp(x, y);
            case SCORE_MULTIPLIER:
                return new ScoreMultiplierPowerUp(x, y);
            case SLOW_MOTION:
                return new SlowMotionPowerUp(x, y);

            default:
                throw new IllegalArgumentException("Unknown power-up type: " + type);
        }
    }
}