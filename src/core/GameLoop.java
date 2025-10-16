package core;

import entities.Ball;
import entities.Brick;
import entities.BrickType;
import entities.Paddle;
import CollisionManager.BallWithPaddle;
import CollisionManager.BallWithBrick;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import utils.Constants;
import java.util.ArrayList;

public class GameLoop extends AnimationTimer {

    private final ArrayList<GameObject> gameObjects;
    private Ball ball;
    private Paddle paddle;
    private final GraphicsContext gc;
    private final InputHandler input;
    private long lastTime;

    public GameLoop(ArrayList<GameObject> gameObjects, GraphicsContext gc, InputHandler input) {
        this.gameObjects = gameObjects;
        this.gc = gc;
        this.input = input;

        for (GameObject obj : gameObjects) {
            if (obj instanceof Ball) {
                this.ball = (Ball) obj; // Nếu là Ball, lưu vào biến ball
            } else if (obj instanceof Paddle) {
                this.paddle = (Paddle) obj; // Nếu là Paddle, lưu vào biến paddle
            }
        }

        if (this.ball == null || this.paddle == null) {
            throw new IllegalStateException("GameLoop requires one Ball and one Paddle");
        }

        this.lastTime = 0;
    }

    @Override
    public void handle(long now) {
        if (lastTime == 0) {
            lastTime = now;
            return;
        }

        gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        double deltaTime = (now - lastTime) / 1e9;
        lastTime = now;

        update(deltaTime);
        render();
    }

    private void update(double deltaTime) {
        // 1. Xử lý input cho Paddle
        paddle.setKeyPressed(KeyCode.LEFT, input.isLeftPressed());
        paddle.setKeyPressed(KeyCode.RIGHT, input.isRightPressed());

        for (GameObject obj : gameObjects) {
            obj.update(deltaTime);
        }

        checkCollision();
        lostChecking();
        winChecking();
    }

    private void checkCollision(){
        BallWithPaddle.checkCollision(ball, paddle);
        for (GameObject o : gameObjects) {
            if (o instanceof Brick && !((Brick) o).getIsDestroyed()) {
                BallWithBrick.checkCollision(ball, (Brick) o);
            }
        }
    }

    private void lostChecking(){
        if (ball.getLives() <= 0) {
            System.out.println("\uD83D\uDC80 Game Over !");
            this.stop();

            Platform.exit();
        }
    }

    private void winChecking(){
        for(Object o : gameObjects){
            if(o instanceof Brick){
                Brick brick = (Brick) o;
                if(brick.getCurrentBrickType() != BrickType.IMPOSSIBLE
                && !brick.getIsDestroyed()){
                    return;
                }
            }
        }
        System.out.println("You WIN");
        this.stop();
        Platform.exit();
    }

    private void render() {
        for (GameObject obj : gameObjects) {
            obj.render(gc);
        }
    }
}