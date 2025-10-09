package entities;

import core.Constants;
import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Paddle extends MovableObject {

    private double speed = Constants.PADDLE_SPEED;
    private PowerUpType currentPowerUp = PowerUpType.NONE;
    private final boolean[] keyPressed = new boolean[256];

    public Paddle(int width, int height) {
        super(width, height);
        this.setDeltaX(speed);
    }

    public Paddle(double positionX, double positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.setDeltaX(speed);
    }

    /**
     * Chỉnh sự kiện bấm phím cho phím truyền vào.
     *
     * @param keycode phím được lắng nghe sự kiện.
     * @param check thao tác chỉnh tắt / bật cho phím được truyền vào.
     */
    public void setKeyPressed(KeyCode keycode, boolean check) {
        if (keycode.getCode() < 256) {
            keyPressed[keycode.getCode()] = check;
        }
    }

    @Override
    public void move(double deltaTime) {
        if (keyPressed[KeyCode.RIGHT.getCode()]) {
            double newPositionX = this.getPositionX() + this.getDeltaX() * deltaTime;
            if (newPositionX + this.getWidth() > Constants.SCREEN_WIDTH) {
                newPositionX = Constants.SCREEN_WIDTH - this.getWidth();
            }
            this.setPositionX(newPositionX);
        }
        if (keyPressed[KeyCode.LEFT.getCode()]) {
            double newPositionX = this.getPositionX() - this.getDeltaX() * deltaTime;
            if (newPositionX < 0) {
                newPositionX = 0;
            }
            this.setPositionX(newPositionX);
        }
        if (keyPressed[KeyCode.UP.getCode()]) {
            double newPositionY = this.getPositionY() - this.getDeltaY() * deltaTime;
            if (newPositionY < 0) {
                newPositionY = 0;
            }
            this.setPositionY(newPositionY);
        }
        if (keyPressed[KeyCode.DOWN.getCode()]) {
            double newPositionY = this.getPositionY() + this.getDeltaY() * deltaTime;
            if (newPositionY + this.getHeight() > Constants.SCREEN_HEIGHT) {
                newPositionY = Constants.SCREEN_HEIGHT - this.getHeight();
            }
            this.setPositionY(newPositionY);
        }
    }

    @Override
    public void update(double deltaTime) {
        move(deltaTime);
        // Update powerup sau...
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE); // Sau này thay bằng texture sau....
        gc.fillRect(this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());
    }

    public double getSpeed() { return speed; }

    public void setSpeed(double speed) { this.speed = speed; }

    public PowerUpType getCurrentPowerUp() { return currentPowerUp; }

    public void setCurrentPowerUp(PowerUpType currentPowerUp) { this.currentPowerUp = currentPowerUp; }
}
