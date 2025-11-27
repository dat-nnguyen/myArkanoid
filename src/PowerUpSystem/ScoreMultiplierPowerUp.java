package PowerUpSystem;

import core.GameLoop;

/**
 * Multiplies current score by 2 (instant effect).
 * REFACTORED: Logic moved from PowerUpManager to this class.
 */
public class ScoreMultiplierPowerUp extends InstantPowerUp {

    public ScoreMultiplierPowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.SCORE_MULTIPLIER);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        GameLoop gameLoop = context.getGameLoop();

        if (gameLoop == null) {
            System.err.println("💰 Score Multiplier failed: GameLoop not set in context!");
            return;
        }

        int currentScore = gameLoop.getScore();
        int newScore = currentScore * PowerUpConfig.SCORE_MULTIPLIER_VALUE;
        gameLoop.setScore(newScore);

        System.out.println("💰 Score Multiplied: " + currentScore + " → " + newScore);
    }
}