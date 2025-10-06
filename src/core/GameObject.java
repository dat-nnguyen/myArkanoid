package core;

import javafx.scene.shape.Rectangle;

public abstract class GameObject {

    protected double positionX;
    protected double positionY;
    protected int width;
    protected int height;

    public GameObject(int width, int height) {
        this.positionX = 0.0;
        this.positionY = 0.0;
        this.width = width;
        this.height = height;
    }

    public GameObject(double positionX, double positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void update(double deltaTime, boolean[] keyPressed);

    public abstract Rectangle render();
}
