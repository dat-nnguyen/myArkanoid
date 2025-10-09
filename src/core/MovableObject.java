package core;

public abstract class MovableObject extends GameObject{

    private double deltaX = 0.0;
    private double deltaY = 0.0;

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

    public abstract void move(double deltaTime);

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