package PowerUpSystem;

/**
 * Shrinks the paddle width temporarily.
 */
public class ShrinkPaddlePowerUp extends PaddleSizePowerUp {

    public ShrinkPaddlePowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.SHRINK_PADDLE);
    }

    @Override
    protected double getSizeMultiplier() {
        return PowerUpConfig.SHRINK_PADDLE_MULTIPLIER;
    }

    @Override
    protected String getLogIcon() {
        return "🔴";
    }
}