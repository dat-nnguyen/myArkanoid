package entities;

import core.Constants;
import core.MovableObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends MovableObject {

    private double speed = Constants.paddleSpeed;
    private PowerUpType currentPowerUp;
    private Rectangle Node;

    public Paddle(int width, int height) {
        super(width, height);
        setDeltaX(speed);
        currentPowerUp = PowerUpType.NONE;
    }

    public Paddle(double positionX, double positionY, int width, int height) {
        super(positionX, positionY, width, height);
        setDeltaX(speed);
        Node = new Rectangle(positionX, positionY, width, height);
        Node.setFill(Color.BLACK);
    }

    @Override
    public void update(double deltaTime, boolean[] keyPressed) {
        move(deltaTime, keyPressed);
        Node.setX(this.positionX);
        Node.setY(this.positionY);
        // Update powerup sau...
    }

    @Override
    public Rectangle render() {
        return Node;
    }

    public void applyPowerUp(PowerUpType power) {
        currentPowerUp = power;
    }

}
