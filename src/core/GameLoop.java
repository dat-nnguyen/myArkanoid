package core;

import entities.Ball;
import entities.Brick;
import entities.Paddle;
import CollisionManager.BallWithPaddle;
import CollisionManager.BallWithBrick;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import utils.Constants;
import java.util.ArrayList;

// game loop(time line/ animation)
public class GameLoop extends AnimationTimer {

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private final GraphicsContext gc;
    private final InputHandler input;
    private long lastTime;

    public GameLoop(Ball ball, Paddle paddle, ArrayList<Brick> brickList, GraphicsContext gc, InputHandler input) {
        this.ball = ball;
        this.paddle = paddle;
        this.brickList = brickList;
        this.gc = gc;
        this.input = input;
        lastTime = 0;
    }

    @Override
    public void handle(long now) {
        gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        double secondStandard = 1000000000.0;
        double deltaTime = (now - lastTime) / secondStandard;
        lastTime = now;
        update(deltaTime);
        render();
    }

    private void update(double deltaTime) {
        paddle.setKeyPressed(KeyCode.LEFT, input.isLeftPressed());
        paddle.setKeyPressed(KeyCode.RIGHT, input.isRightPressed());
        paddle.update(deltaTime);
        ball.update(deltaTime);
        for (Brick brick : brickList) {
            brick.update(deltaTime);
        }
        checkCollision();
    }

    private void checkCollision() {
        BallWithPaddle.checkCollision(ball, paddle);
        for (Brick brick : brickList) {
            BallWithBrick.checkCollision(ball, brick);
        }
    }

    private void render() {
        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : brickList) {
            brick.render(gc);
        }
    }
}