package PowerUpSystem;

/**
 * Base class for instant power-ups (no duration, permanent effect).
 * Examples: MultiBall, ExtraLife, ScoreMultiplier, SuddenDeath
 */
public abstract class InstantPowerUp extends PowerUp {

    public InstantPowerUp(double positionX, double positionY, PowerUpType type) {
        super(positionX, positionY, type);

        // Validation: instant power-ups must have duration = 0
        if (type.getDuration() != 0) {
            throw new IllegalArgumentException(
                    "InstantPowerUp must have duration = 0, but got: " + type.getDuration()
            );
        }
    }

    /**
     * Instant power-ups have no removal effect.
     * Their effects are permanent (MultiBall, ExtraLife) or immediate (ScoreMultiplier).
     */
    @Override
    public final void removeEffect(PowerUpContext context) {
        // Empty - instant power-ups cannot be removed
    }
}