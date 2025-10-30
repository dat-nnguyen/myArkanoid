package PowerUpSystem;


/**
 * Factory class for creating power-up instances.
 * Uses Factory design pattern for flexible power-up creation.
 */
public class PowerUpFactory {

    /**
     * Create a power-up of specified type at given position.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param type Type of power-up to create
     * @return New PowerUp instance
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
            default:
                throw new IllegalArgumentException("Unknown power-up type: " + type);
        }
    }
}