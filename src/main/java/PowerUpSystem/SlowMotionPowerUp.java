package PowerUpSystem;
import javafx.beans.property.DoubleProperty;

public class SlowMotionPowerUp extends TimedPowerUp {

    public SlowMotionPowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.SLOW_MOTION);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        DoubleProperty timeScale = context.getTimeScaleProperty();

        if (timeScale == null) {
            System.err.println("⏱️ Slow Motion failed: TimeScaleProperty not set!");
            return;
        }

        timeScale.set(PowerUpConfig.SLOW_MOTION_TIME_SCALE);
        System.out.println("⏱️ Slow Motion activated! Time scale: " +
                PowerUpConfig.NORMAL_TIME_SCALE + " → " + timeScale.get());
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        DoubleProperty timeScale = context.getTimeScaleProperty();

        if (timeScale == null) {
            System.err.println("⏱️ Slow Motion removal failed: TimeScaleProperty not set!");
            return;
        }

        timeScale.set(PowerUpConfig.NORMAL_TIME_SCALE);
        System.out.println("⏱️ Slow Motion ended! Time scale: " +
                PowerUpConfig.SLOW_MOTION_TIME_SCALE + " → " + timeScale.get());
    }
}