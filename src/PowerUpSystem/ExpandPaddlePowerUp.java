package PowerUpSystem;

/**
 * Expands the paddle width temporarily.
 */
public class ExpandPaddlePowerUp extends PaddleSizePowerUp {

    public ExpandPaddlePowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.EXPAND_PADDLE);
    }

    @Override
    protected double getSizeMultiplier() {
        return PowerUpConfig.EXPAND_PADDLE_MULTIPLIER;
    }

    @Override
    protected String getLogIcon() {
        return "🔵";
    }
}