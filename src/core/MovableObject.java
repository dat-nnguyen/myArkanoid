package core;

import utils.Vector2D;

public abstract class MovableObject extends GameObject {
    protected Vector2D velocity;
    protected Vector2D previousPosition;

    public MovableObject(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.velocity = new Vector2D(0, 0);
        this.previousPosition = new Vector2D(x, y);
    }

    @Override
    public void update(float deltaTime) {
        // Save previous position
        previousPosition.setVector(position.x, position.y);
        // update new position base on velocity
        position.add(velocity.multiply(deltaTime));

    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(float vx, float vy) {
        velocity.x = vx;
        velocity.y = vy;
    }
}
