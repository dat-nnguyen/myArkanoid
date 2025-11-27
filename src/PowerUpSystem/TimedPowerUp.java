package PowerUpSystem;

/**
 * Base class for timed power-ups (with duration, need removal).
 * Examples: ExpandPaddle, ShrinkPaddle, FastBall, SlowBall, SlowMotion
 */
public abstract class TimedPowerUp extends PowerUp {

    public TimedPowerUp(double positionX, double positionY, PowerUpType type) {
        super(positionX, positionY, type);

        // Validation: timed power-ups must have duration > 0
        if (type.getDuration() <= 0) {
            throw new IllegalArgumentException(
                    "TimedPowerUp must have duration > 0, but got: " + type.getDuration()
            );
        }
    }

    /**
     * Subclasses must implement how to remove the effect.
     * This is called when the timer expires.
     */
    @Override
    public abstract void removeEffect(PowerUpContext context);
}