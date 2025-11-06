package PowerUpSystem;

import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import utils.Constants;

/**
 * Abstract base class for all power-ups.
 * UPDATED: Added colors for FastBall, ExtraLife, SuddenDeath
 */
public abstract class PowerUp extends MovableObject {

    private boolean isActive = false;
    private boolean isCollected = false;
    private final PowerUpType type;
    private double fallSpeed = Constants.POWERUP_FALL_SPEED;

    public PowerUp(double positionX, double positionY, PowerUpType type) {
        super(positionX, positionY, Constants.POWERUP_WIDTH, Constants.POWERUP_HEIGHT, type.getTexturePath());
        this.type = type;
        this.setDeltaY(fallSpeed);
    }

    @Override
    public void move(double deltaTime) {
        double newPositionY = this.getPositionY() + fallSpeed * deltaTime;

        // Check if power-up fell off screen
        if (newPositionY > Constants.SCREEN_HEIGHT) {
            isActive = false;
        }

        this.setPositionY(newPositionY);
    }

    @Override
    public void update(double deltaTime) {
        if (isActive && !isCollected) {
            move(deltaTime);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isActive && !isCollected) {
            if (this.getTexture() != null) {
                // Draw texture if available
                gc.drawImage(this.getTexture(), this.getPositionX(), this.getPositionY(),
                        this.getWidth(), this.getHeight());
            } else {
                // Draw placeholder if no texture
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
                    case FAST_BALL:
                        gc.setFill(javafx.scene.paint.Color.ORANGE);
                        break;
                    case EXTRA_LIFE:
                        gc.setFill(javafx.scene.paint.Color.PINK);
                        break;
                    case SUDDEN_DEATH:
                        gc.setFill(javafx.scene.paint.Color.BLACK);
                        break;
                    case SCORE_MULTIPLIER:
                        gc.setFill(javafx.scene.paint.Color.GOLD);
                        break;
                    case SLOW_MOTION:
                        gc.setFill(javafx.scene.paint.Color.CYAN);
                        break;
                    default:
                        gc.setFill(javafx.scene.paint.Color.GRAY);
                }

                // Draw colored rectangle
                gc.fillRect(this.getPositionX(), this.getPositionY(),
                        this.getWidth(), this.getHeight());

                // Draw border
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

    public abstract void applyEffect(PowerUpContext context);
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