package entities;

import core.InputHandler;
import core.MovableObject;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.Constants;

public class Paddle extends MovableObject {

    private final Rectangle paddle;

    public Paddle(float x, float y, int width, int height) {
        super(x, y, width, height);

        paddle = new Rectangle(x, y, width, height);
        paddle.setFill(Color.BLANCHEDALMOND);
        paddle.setArcWidth(20);
        paddle.setArcHeight(20);
    }

    /**
     * Update paddle status base on keyboard input and deltaTime
     * @param deltaTime .
     * @param inputHandler responsible for input
     */
    public void update(float deltaTime, InputHandler inputHandler) {
        // reset velocity x to 0, it will be able to stop paddle after released the key
        this.velocity.x = 0;

        if (inputHandler.isLeftPressed()) {
            this.velocity.x = -Constants.PADDLE_SPEED;
        }
        if (inputHandler.isRightPressed()) {
            this.velocity.x = Constants.PADDLE_SPEED;
        }
        //update new position
        //newPosition = oldPosition + velocity * deltaTime
        super.update(deltaTime);

        // clamps checking
        if (this.position.x < 0) {
            this.position.x = 0;
        }
        if (this.position.x + this.width > Constants.WIDTH) {
            this.position.x = Constants.WIDTH - this.width;
        }

        // update position for animation
        this.paddle.setX(this.position.x);
    }

    @Override
    public Node getNode() {
        return this.paddle;
    }

    @Override
    public void update(float deltaTime) {
        // we use update(deltaTime, inputHandler) so don't need to override here
    }
}