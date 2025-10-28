package core;

import UI.Game.GameScene;
import UI.SceneManager;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import CollisionManager.BallWithPaddle;
import CollisionManager.BallWithBrick;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import utils.Constants;
import java.util.ArrayList;

import PowerUpSystem.PowerUpManager;
import java.util.ArrayList;
import java.util.Iterator;

// game loop(time line/ animation)
public class GameLoop extends AnimationTimer {

    private final SceneManager sceneManager;
    private final GameScene gameScene;

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private final GraphicsContext gc;
    private final InputHandler input;
    private long lastTime;
    private int score = 0;

    private final PowerUpManager powerUpManager;
    private final ArrayList<Ball> ballList; // Store multiple balls

    public GameLoop(Ball ball, Paddle paddle, ArrayList<Brick> brickList, GraphicsContext gc,
                    InputHandler input, SceneManager sceneManager, GameScene gameScene) {
        this.sceneManager = sceneManager;
        this.gameScene = gameScene;
        this.ball = ball;
        this.paddle = paddle;
        this.brickList = brickList;
        this.gc = gc;
        this.input = input;
        this.ballList = new ArrayList<>(); // Initialize ball list
        this.powerUpManager = new PowerUpManager(ball, paddle, ballList);
        lastTime = 0;
    }

    @Override
    public void handle(long now) {
        gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        double secondStandard = 1000000000.0;
        double deltaTime = (now - lastTime) / secondStandard;
        lastTime = now;
        update(deltaTime);

        gc.setStroke(Color.CRIMSON);
        gc.setLineWidth(2);
        gc.strokeRect(Constants.MARGIN_WIDTH, Constants.MARGIN_HEIGHT,
                Constants.PLAY_SCREEN_WIDTH, Constants.PLAY_SCREEN_HEIGHT);

        gameScene.updateLivesUI(ball.getLives());

        // GAME OVER: Ball chính hết mạng VÀ không còn extra balls
        if (ball.getLives() <= 0 && ballList.isEmpty()) {
            sceneManager.switchTo("GameOver");
            this.stop();
            return;
        }

        // Check win condition
        boolean nextLevel = true;
        for (Brick brick : brickList) {
            if (!brick.getIsDestroyed()) {
                nextLevel = false;
                break;
            }
        }
        if (nextLevel) gameScene.goToNextLevel();

        render();
    }

    private void update(double deltaTime) {
        paddle.setKeyPressed(KeyCode.LEFT, input.isLeftPressed());
        paddle.setKeyPressed(KeyCode.RIGHT, input.isRightPressed());

        // SPACE chỉ launch ball chính nếu nó còn sống
        if (input.isSpacePressed() && ball.getLives() > 0 && !ball.play) {
            ball.play = true;
        }

        paddle.update(deltaTime);

        // Update bricks and spawn power-ups
        for (Brick brick : brickList) {
            if (!brick.getIsDestroyed()) {
                brick.update(deltaTime);
                if (brick.getIsDestroyed()) {
                    score++;
                    System.out.println("🎯 Score: " + score);
                }
            }

            if (brick.shouldSpawnPowerUp()) {
                powerUpManager.trySpawnPowerUp(brick);
            }
        }

        // Update main ball (CHỈ KHI còn lives)
        if (ball.getLives() > 0) {
            ball.update(deltaTime);
        }

        // Update extra balls và xóa những ball hết mạng
        Iterator<Ball> ballIterator = ballList.iterator();
        while (ballIterator.hasNext()) {
            Ball extraBall = ballIterator.next();
            extraBall.update(deltaTime);

            // Remove extra ball khi hết mạng
            if (extraBall.getLives() <= 0) {
                System.out.println("⚽ Extra ball removed from game");
                ballIterator.remove();
            }
        }

        // Chỉ respawn ball chính khi:
        // 1. Ball chính còn lives (lives > 0)
        // 2. Ball chính không đang play (đã rơi xuống)
        // 3. KHÔNG còn extra balls nào
        // 4. Ball đã ở dưới màn hình
        if (ball.getLives() > 0 && !ball.play && ballList.isEmpty()) {
            // Check if ball is below screen (đã rơi xuống)
            if (ball.getPositionY() > Constants.SCREEN_HEIGHT - 200) {
                // Reset ball về vị trí spawn
                ball.setPositionX(Constants.BALL_START_POSITION_X);
                ball.setPositionY(Constants.BALL_START_POSITION_Y);
                ball.setDirectionX(0.0);
                ball.setDirectionY(1.0);
                System.out.println("⚽ Ball respawned! Lives remaining: " + ball.getLives());
            }
        }

        // Update power-ups
        powerUpManager.update(deltaTime);

        checkCollision();
    }

    private void checkCollision() {
        // Main ball collisions (CHỈ KHI còn lives)
        if (ball.getLives() > 0) {
            BallWithPaddle.checkCollision(ball, paddle);
            for (Brick brick : brickList) {
                BallWithBrick.checkCollision(ball, brick);
            }
        }

        // Extra balls collisions
        for (Ball extraBall : ballList) {
            BallWithPaddle.checkCollision(extraBall, paddle);
            for (Brick brick : brickList) {
                BallWithBrick.checkCollision(extraBall, brick);
            }
        }

        // Power-up collision with paddle
        powerUpManager.checkCollision(paddle);
    }

    private void render() {
        // Render main ball (CHỈ KHI còn lives)
        if (ball.getLives() > 0) {
            ball.render(gc);
        }

        paddle.render(gc);

        for (Brick brick : brickList) {
            brick.render(gc);
        }

        // Render extra balls
        for (Ball extraBall : ballList) {
            extraBall.render(gc);
        }

        // Render power-ups
        powerUpManager.render(gc);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }

}