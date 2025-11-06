package PowerUpSystem;

import javafx.beans.property.IntegerProperty;

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
        IntegerProperty scoreProp = context.getScoreProperty();

        if (scoreProp == null) {
            System.err.println("💰 Score Multiplier failed: ScoreProperty not set!");
            return;
        }

        int currentScore = scoreProp.get();
        int newScore = currentScore * PowerUpConfig.SCORE_MULTIPLIER_VALUE;
        scoreProp.set(newScore);

        System.out.println("💰 Score Multiplied: " + currentScore + " → " + newScore);
    }
}