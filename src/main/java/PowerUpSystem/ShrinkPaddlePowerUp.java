package PowerUpSystem;

import entities.Paddle;
import utils.Constants;

/**
 * Shrinks the paddle width temporarily.
 * FIXED: Doesn't stack - uses saved original width.
 */
public class ShrinkPaddlePowerUp extends PowerUp {

    private int originalWidth;

    public ShrinkPaddlePowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.SHRINK_PADDLE);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        Paddle paddle = context.getPaddle();

        // Save current width
        originalWidth = paddle.getWidth();

        // Calculate new width from ORIGINAL
        int baseWidth = Constants.PADDLE_WIDTH;
        int newWidth = (int)(baseWidth * 0.6);

        // Clamp to min
        int minWidth = 80;
        if (newWidth < minWidth) {
            newWidth = minWidth;
        }

        paddle.setWidth(newWidth);

        // Apply texture if available
        if (PowerUpType.SHRINK_PADDLE.hasEffectTexture()) {
            paddle.setPowerUpTexture(PowerUpType.SHRINK_PADDLE.getEffectTexturePath());
        }

        System.out.println("🔴 Paddle shrank: " + originalWidth + " → " + newWidth);
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        Paddle paddle = context.getPaddle();

        // Return to ORIGINAL width
        paddle.setWidth(Constants.PADDLE_WIDTH);
        paddle.resetTexture();

        System.out.println("🔴 Paddle returned to normal: " + Constants.PADDLE_WIDTH);
    }
}