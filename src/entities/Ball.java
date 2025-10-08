package entities;

import core.MovableObject;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utils.Constants;
import utils.Vector2D;

public class Ball extends MovableObject {

    private final float radius;
    private final Circle node;

    /**
     * When we initialize GameObject position, the rectangle of object will be laid on
     * top-left corner of rectangle
     * So that we have to subtract for radius to put vector into center of the ball
     */
    public Ball(float x, float y, float radius) {
        super(x - radius, y - radius, (int)radius * 2, (int)radius * 2);
        this.radius = radius;

        // First velocity: random
        float initialVx;
        double randomValue = Math.random(); // [0.0, 1.0)

        if (randomValue > 0.5) {
            initialVx = 1f; // 50%
        } else {
            initialVx = -1f; // 50% khả năng còn lại
        }
        this.velocity = new Vector2D(initialVx, -1f).normalized().multiply(Constants.BALL_SPEED);

        this.node = new Circle(x, y, radius);
        this.node.setFill(Color.CRIMSON);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //Check collision with window, exclude bottom

        // Left or right window
        if (this.getX() < 0 || this.getX() + this.width > Constants.WIDTH) {
            this.reverseVelocityX();
            // Clamp position to prevent sticking to walls
            this.position.x = Math.max(0, Math.min(this.position.x, Constants.WIDTH - this.width));
        }

        // top window
        if (this.getY() < 0) {
            this.reverseVelocityY();
            this.position.y = 0; // Clamp
        }

        // update position of ball
        this.node.setCenterX(this.getX() + radius);
        this.node.setCenterY(this.getY() + radius);
    }

    /**
     * Flip ball speed on X. For hit left or right.
     */
    public void reverseVelocityX() {
        this.velocity.x *= -1;
    }

    /**
     * Flip ball speed on Y. For hit top or bottom.
     */
    public void reverseVelocityY() {
        this.velocity.y *= -1;
    }

    @Override
    public Node getNode() {
        return this.node;
    }
}