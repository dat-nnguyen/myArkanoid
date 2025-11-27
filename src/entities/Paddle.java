package entities;

import utils.Constants;
import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Objects;

import java.util.Arrays;

import PowerUpSystem.PowerUpType;

/**
 * Paddle management.
 *
 */
public class Paddle extends MovableObject {

    private double speed = Constants.PADDLE_SPEED; // Default paddle movement speed.
    private PowerUpType currentPowerUp = PowerUpType.NONE; // Current paddle power-up.
    private final boolean[] keyPressed = new boolean[256]; // Handle keyboard events.

    /**
     * Set paddle texture for power-up effect.
     */
    public void setPowerUpTexture(String texturePath) {
        if (texturePath != null) {
            try {
                String textureUrl = Objects.requireNonNull(getClass().getResource(texturePath)).toExternalForm();
                this.setTexture(new javafx.scene.image.Image(textureUrl));
            } catch (NullPointerException e) {
                System.err.println("Power-up texture loading failed: " + texturePath);
            }
        }
    }

    /**
     * Reset paddle texture to original.
     */
    public void resetTexture() {
        try {
            String originalTexturePath = Constants.PADDLE_TEXTURE_PATH;
            String textureUrl = Objects.requireNonNull(getClass().getResource(originalTexturePath)).toExternalForm();
            this.setTexture(new javafx.scene.image.Image(textureUrl));
        } catch (NullPointerException e) {
            System.err.println("Original texture loading failed!");
        }
    }

    /**
     * Initialize object.
     *
     */
    public Paddle() {
        super(Constants.PADDLE_START_POSITION_X, Constants.PADDLE_START_POSITION_Y, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_TEXTURE_PATH);
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

        double xMin = Constants.MARGIN_WIDTH;
        double xMax = xMin + Constants.PLAY_SCREEN_WIDTH;
        double deltaX = 0;

        if (keyPressed[KeyCode.RIGHT.getCode()]) {
            deltaX += speed * deltaTime;
        }
        if (keyPressed[KeyCode.LEFT.getCode()]) {
            deltaX += speed * deltaTime * (-1);
        }

        this.setDeltaX(deltaX);

        double newPositionX = this.getPositionX() + this.getDeltaX();

        if (newPositionX + this.getWidth() > xMax) {
            newPositionX = xMax - this.getWidth();
            this.setDeltaX(0);
        } else if (newPositionX < xMin) {
            newPositionX = xMin;
            this.setDeltaX(0);
        }

        this.setPositionX(newPositionX);
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.getTexture(), this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void reset() {
        speed = Constants.PADDLE_SPEED;
        PowerUpType currentPowerUp = PowerUpType.NONE;
        Arrays.fill(keyPressed, false);
        this.setDeltaX(speed);
        this.setPositionX(Constants.PADDLE_START_POSITION_X);
        this.setPositionY(Constants.PADDLE_START_POSITION_Y);
        this.setWidth(Constants.PADDLE_WIDTH);
        this.setHeight(Constants.PADDLE_HEIGHT);
    }

    // Getter and Setter method.

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public PowerUpType getCurrentPowerUp() {
        return currentPowerUp;
    }

    public void setCurrentPowerUp(PowerUpType currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }
}