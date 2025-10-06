package core;

import javafx.scene.input.KeyCode;

public abstract class MovableObject extends GameObject{

    protected double deltaX = 0.0;
    protected double deltaY = 0.0;

    public MovableObject(int width, int height) {
        super(width, height);
    }

    public MovableObject(double positionX, double positionY, int width, int height) {
        super(positionX, positionY, width, height);
    }

    public MovableObject(int width, int height, double deltaX, double deltaY) {
        super(width, height);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public MovableObject(double positionX, double positionY, int width, int height, double deltaX, double deltaY) {
        super(positionX, positionY, width, height);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public void move(double deltaTime, boolean[] keyPressed) {
        if (keyPressed[KeyCode.RIGHT.getCode()]) {
            double newPositionX = positionX + deltaX * deltaTime;
            if (newPositionX + width > Constants.screenWidth) {
                newPositionX = Constants.screenWidth - this.width;
            }
            setPositionX(newPositionX);
        }
        if (keyPressed[KeyCode.LEFT.getCode()]) {
            double newPositionX = this.positionX - deltaX * deltaTime;
            if (newPositionX < 0) {
                newPositionX = 0;
            }
            setPositionX(newPositionX);
        }
        if (keyPressed[KeyCode.UP.getCode()]) {
            double newPositionY = positionY - deltaY * deltaTime;
            if (newPositionY < 0) {
                newPositionY = 0;
            }
            setPositionY(newPositionY);
        }
        if (keyPressed[KeyCode.DOWN.getCode()]) {
            double newPositionY = this.positionY + deltaY * deltaTime;
            if (newPositionY + height > Constants.screenHeight) {
                newPositionY = Constants.screenHeight - height;
            }
            setPositionY(newPositionY);
        }
    }

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

}