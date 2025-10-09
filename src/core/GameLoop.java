package core;

import entities.Ball;
import entities.Paddle;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

// game loop(time line/ animation)
public class GameLoop extends AnimationTimer {

    private final Ball ball;
    private final Paddle paddle;
    private final GraphicsContext gc;
    private final InputHandler input;
    private long lastTime;

    public GameLoop(Ball ball, Paddle paddle, GraphicsContext gc, InputHandler input) {
        this.ball = ball;
        this.paddle = paddle;
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
        CollisionManager.checkCollision_Ball_Paddle(ball, paddle);
    }

    private void render() {
        ball.render(gc);
        paddle.render(gc);
    }
}
