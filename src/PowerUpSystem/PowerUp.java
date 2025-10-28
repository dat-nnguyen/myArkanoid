package PowerUpSystem;

import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import utils.Constants;


/**
 * Abstract base class for all power-ups.
 * Power-ups fall from destroyed bricks and apply effects when caught by paddle.
 */
public abstract class PowerUp extends MovableObject {

    private boolean isActive = false;
    private boolean isCollected = false;
    private final PowerUpType type;
    private double fallSpeed = Constants.POWERUP_FALL_SPEED;

    /**
     * Initialize power-up at given position.
     *
     * @param positionX X coordinate where brick was destroyed
     * @param positionY Y coordinate where brick was destroyed
     * @param type Type of this power-up
     */
    public PowerUp(double positionX, double positionY, PowerUpType type) {
        super(positionX, positionY, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, type.getTexturePath());
        this.type = type;
        this.setDeltaY(fallSpeed);
    }

    @Override
    public void move(double deltaTime) {
        double oldY = this.getPositionY();
        double newPositionY = oldY + fallSpeed * deltaTime;

        System.out.println("🎈 PowerUp moving: " + oldY + " → " + newPositionY + " (delta: " + (fallSpeed * deltaTime) + ")");

        // Check if power-up fell off screen
        if (newPositionY > Constants.SCREEN_HEIGHT) {
            isActive = false;
            System.out.println("❌ PowerUp fell off screen!");
        }

        this.setPositionY(newPositionY);
    }

    @Override
    public void update(double deltaTime) {
        if (isActive && !isCollected) {
            System.out.println("🔄 PowerUp update - Active: " + isActive + ", Collected: " + isCollected);
            move(deltaTime);
        } else {
            System.out.println("⏸️ PowerUp NOT updating - Active: " + isActive + ", Collected: " + isCollected);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isActive && !isCollected) {
            if (this.getTexture() != null) {
                // Vẽ texture nếu có
                gc.drawImage(this.getTexture(), this.getPositionX(), this.getPositionY(),
                        this.getWidth(), this.getHeight());
            } else {
                // Vẽ placeholder nếu không có texture
                // Màu sắc khác nhau cho từng loại power-up
                switch (type) {
                    case EXPAND_PADDLE:
                        gc.setFill(javafx.scene.paint.Color.BLUE);
                        break;
                    case SHRINK_PADDLE:
                        gc.setFill(javafx.scene.paint.Color.RED);
                        break;
                    case SLOW_BALL:
                        gc.setFill(javafx.scene.paint.Color.GREEN);
                        break;
                    case MULTI_BALL:
                        gc.setFill(javafx.scene.paint.Color.YELLOW);
                        break;
                    default:
                        gc.setFill(javafx.scene.paint.Color.GRAY);
                }

                // Vẽ hình vuông màu
                gc.fillRect(this.getPositionX(), this.getPositionY(),
                        this.getWidth(), this.getHeight());

                // Vẽ viền
                gc.setStroke(javafx.scene.paint.Color.WHITE);
                gc.setLineWidth(2);
                gc.strokeRect(this.getPositionX(), this.getPositionY(),
                        this.getWidth(), this.getHeight());
            }
        }
    }

    @Override
    public void reset() {
        isActive = false;
        isCollected = false;
    }

    /**
     * Apply power-up effect.
     * Must be implemented by specific power-up classes.
     *
     * @param context Game context containing ball, paddle, etc.
     */
    public abstract void applyEffect(PowerUpContext context);

    /**
     * Remove power-up effect.
     * Must be implemented by specific power-up classes.
     *
     * @param context Game context containing ball, paddle, etc.
     */
    public abstract void removeEffect(PowerUpContext context);

    // Getters and Setters
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isCollected() { return isCollected; }
    public void setCollected(boolean collected) { isCollected = collected; }

    public PowerUpType getType() { return type; }
    public double getFallSpeed() { return fallSpeed; }
    public void setFallSpeed(double fallSpeed) { this.fallSpeed = fallSpeed; }
}