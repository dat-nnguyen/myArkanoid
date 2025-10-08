package core;

import utils.Vector2D;

public abstract class MovableObject extends GameObject {
    protected Vector2D velocity;

    public MovableObject(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.velocity = new Vector2D(0, 0);
    }

    public abstract void update(double deltaTime);
}
