package entities;

import utils.Constants;
import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/**
 * Paddle management.
 *
 */
public class Paddle extends MovableObject {

    private double speed = Constants.PADDLE_SPEED; // Default paddle movement speed.
    private PowerUpType currentPowerUp = PowerUpType.NONE; // Current paddle power-up.
    private final boolean[] keyPressed = new boolean[256]; // Handle keyboard events.

    /**
     * Initialize default object.
     *
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public Paddle(int width, int height) {
        super(width, height);
        this.setDeltaX(speed);
    }

    /**
     * Initialize object.
     *
     * @param positionX Upper-left x-coordinate.
     * @param positionY Upper-left y-coordinate.
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public Paddle(double positionX, double positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.setDeltaX(speed);
    }

    /**
     * Handle the key press event for the passed-in key.
     *
     * @param keycode Key Being Listened To.
     * @param isTurnOn Enable/Disable Passed-in Key.
     */
    public void setKeyPressed(KeyCode keycode, boolean isTurnOn) {
        if (keycode.getCode() < 256) {
            keyPressed[keycode.getCode()] = isTurnOn;
        }
    }

    @Override
    public void move(double deltaTime) {

        double deltaX = 0;

        if (keyPressed[KeyCode.RIGHT.getCode()]) {
            deltaX += speed * deltaTime;
        }
        if (keyPressed[KeyCode.LEFT.getCode()]) {
            deltaX += speed * deltaTime * (-1);
        }

        this.setDeltaX(deltaX);

        double newPositionX = this.getPositionX() + this.getDeltaX();

        if (newPositionX + this.getWidth() > Constants.SCREEN_WIDTH) {
            newPositionX = Constants.SCREEN_WIDTH - this.getWidth();
            this.setDeltaX(0);
        } else if (newPositionX < 0) {
            newPositionX = 0;
            this.setDeltaX(0);
        }

        this.setPositionX(newPositionX);
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        // Update power-up sau...
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED); // Sau này thay bằng texture sau
        gc.fillRect(this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());
    }

    // Getter and Setter method.

    public double getSpeed() { return speed; }

    public void setSpeed(double speed) { this.speed = speed; }

    public PowerUpType getCurrentPowerUp() { return currentPowerUp; }

    public void setCurrentPowerUp(PowerUpType currentPowerUp) { this.currentPowerUp = currentPowerUp; }
}