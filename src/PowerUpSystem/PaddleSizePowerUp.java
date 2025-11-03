package PowerUpSystem;

import entities.Paddle;
import utils.Constants;

/**
 * Base class for paddle size power-ups (ExpandPaddle, ShrinkPaddle).
 * Uses Template Method pattern to eliminate code duplication.
 */
public abstract class PaddleSizePowerUp extends TimedPowerUp {

    public PaddleSizePowerUp(double positionX, double positionY, PowerUpType type) {
        super(positionX, positionY, type);
    }

    /**
     * Subclasses define the size multiplier.
     * @return Multiplier to apply to base width (e.g., 1.5 for expand, 0.6 for shrink)
     */
    protected abstract double getSizeMultiplier();

    /**
     * Get the emoji/icon for logging.
     */
    protected abstract String getLogIcon();

    @Override
    public void applyEffect(PowerUpContext context) {
        Paddle paddle = context.getPaddle();

        // Calculate new width from BASE width
        int baseWidth = Constants.PADDLE_WIDTH;
        int newWidth = (int)(baseWidth * getSizeMultiplier());

        // Clamp to valid range
        newWidth = Math.max(PowerUpConfig.MIN_PADDLE_WIDTH, newWidth);
        newWidth = Math.min(PowerUpConfig.MAX_PADDLE_WIDTH, newWidth);

        paddle.setWidth(newWidth);

        // Apply texture if available
        if (getType().hasEffectTexture()) {
            paddle.setPowerUpTexture(getType().getEffectTexturePath());
        }

        System.out.println(getLogIcon() + " Paddle resized: " + baseWidth + " → " + newWidth);
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        Paddle paddle = context.getPaddle();

        // Return to BASE width
        paddle.setWidth(Constants.PADDLE_WIDTH);
        paddle.resetTexture();

        System.out.println(getLogIcon() + " Paddle returned to normal: " + Constants.PADDLE_WIDTH);
    }
}