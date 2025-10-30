package PowerUpSystem;

import audio.SoundManager;
import entities.Ball;
import java.util.ArrayList;

/**
 * Creates additional balls (no duration - instant effect).
 * FIXED: Extra balls are marked as non-main balls.
 */
public class MultiBallPowerUp extends PowerUp {

    public MultiBallPowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.MULTI_BALL);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        Ball originalBall = context.getBall();
        ArrayList<Ball> ballList = context.getBallList();

        // Find active ball
        Ball activeBall = null;

        // Priority: Main ball if playing
        if (originalBall.getLives() > 0 && originalBall.play) {
            activeBall = originalBall;
        }
        // Otherwise: First extra ball
        else if (!ballList.isEmpty()) {
            activeBall = ballList.get(0);
        }

        // No active ball → Cancel multi-ball
        if (activeBall == null) {
            System.out.println("⚠️ Multi-ball cancelled: No active balls!");
            return;
        }

        // Create 2 additional balls with different angles
        for (int i = 0; i < 2; i++) {
            Ball newBall = new Ball();

            // Copy position and speed from active ball
            newBall.setPositionX(activeBall.getPositionX());
            newBall.setPositionY(activeBall.getPositionY());
            newBall.setSpeed(activeBall.getSpeed());

            // === KEY CHANGE ===
            // Mark as extra ball (NOT main ball)
            newBall.setMainBall(false);

            // Extra balls have ONLY 1 life
            newBall.setLives(1);

            // Make it active immediately
            newBall.play = true;

            // Set different direction (-30° and +30° from vertical)
            double angle = Math.PI / 2 + (i == 0 ? -Math.PI / 6 : Math.PI / 6);
            newBall.setDirectionX(Math.cos(angle));
            newBall.setDirectionY(-Math.sin(angle));

            ballList.add(newBall);

            System.out.println("⚽ Extra ball created with 1 life at angle: " + Math.toDegrees(angle) + "°");
        }

        System.out.println("🟡 Multi-ball activated! Total balls: " + (ballList.size() + 1));
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        // Multi-ball has no removal effect (instant permanent)
    }
}