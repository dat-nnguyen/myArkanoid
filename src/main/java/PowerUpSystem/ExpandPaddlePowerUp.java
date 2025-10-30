package PowerUpSystem;

import entities.Paddle;
import utils.Constants;

/**
 * Expands the paddle width temporarily.
 * FIXED: Doesn't stack - uses saved original width.
 */
public class ExpandPaddlePowerUp extends PowerUp {

    private int originalWidth;

    public ExpandPaddlePowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.EXPAND_PADDLE);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        Paddle paddle = context.getPaddle();

        // Save CURRENT width as original (might already be modified)
        originalWidth = paddle.getWidth();

        // Calculate new width from ORIGINAL, not current
        int baseWidth = Constants.PADDLE_WIDTH;
        int newWidth = (int)(baseWidth * 1.5);

        // Clamp to max
        int maxWidth = Constants.PLAY_SCREEN_WIDTH / 2;
        if (newWidth > maxWidth) {
            newWidth = maxWidth;
        }

        paddle.setWidth(newWidth);

        // Apply texture if available
        if (PowerUpType.EXPAND_PADDLE.hasEffectTexture()) {
            paddle.setPowerUpTexture(PowerUpType.EXPAND_PADDLE.getEffectTexturePath());
        }

        System.out.println("🔵 Paddle expanded: " + originalWidth + " → " + newWidth);
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        Paddle paddle = context.getPaddle();

        // Return to ORIGINAL width
        paddle.setWidth(Constants.PADDLE_WIDTH);
        paddle.resetTexture();

        System.out.println("🔵 Paddle returned to normal: " + Constants.PADDLE_WIDTH);
    }
}