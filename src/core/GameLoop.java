package core;

import UI.Game.GameScene;
import UI.SceneManager;
import audio.SoundManager;
import entities.Ball;
import entities.Brick;
import entities.BrickType;
import entities.Paddle;
import CollisionManager.BallWithPaddle;
import CollisionManager.BallWithBrick;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import utils.Constants;
import java.util.ArrayList;
import PowerUpSystem.PowerUpManager;
import java.util.Iterator;

/**
 * Game loop with FIXED multi-ball lives logic.
 */
public class GameLoop extends AnimationTimer {

    private final SceneManager sceneManager;
    private final GameScene gameScene;

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private final GraphicsContext gc;
    private final InputHandler input;
    private long lastTime = 0;
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    private final PowerUpManager powerUpManager;
    private final ArrayList<Ball> ballList;

    public GameLoop(Ball ball, Paddle paddle, ArrayList<Brick> brickList, GraphicsContext gc,
                    InputHandler input, SceneManager sceneManager, GameScene gameScene,
                    PowerUpManager powerUpManager, ArrayList<Ball> ballList) {
        this.sceneManager = sceneManager;
        this.gameScene = gameScene;
        this.ball = ball;
        this.paddle = paddle;
        this.brickList = brickList;
        this.gc = gc;
        this.input = input;
        this.ballList = ballList;
        this.powerUpManager = powerUpManager;
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

        gameScene.updateLivesUI();

        // GAME OVER: Main ball out of lives AND no extra balls
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

        // SPACE launches main ball if it has lives and not playing
        if (input.isSpacePressed() && ball.getLives() > 0 && !ball.play) {
            ball.play = true;
        }

        paddle.update(deltaTime);

        // Update bricks and spawn power-ups
        for (Brick brick : brickList) {
            if (!brick.getIsDestroyed()) {
                brick.update(deltaTime);
                if (brick.getIsDestroyed()) {
                    int newScore = score.get() + 1;
                    if (brick.getCurrentBrickType() == BrickType.MEDIUM) {
                        newScore += 2;
                    } else if (brick.getCurrentBrickType() == BrickType.HARD) {
                        newScore += 4;
                    }
                    score.set(newScore);
                }
            }

            if (brick.shouldSpawnPowerUp()) {
                powerUpManager.trySpawnPowerUp(brick);
            }
        }

        // Update main ball (if has lives)
        if (ball.getLives() > 0) {
            ball.update(deltaTime);
        }

        // Update extra balls and remove dead ones
        Iterator<Ball> ballIterator = ballList.iterator();
        while (ballIterator.hasNext()) {
            Ball extraBall = ballIterator.next();
            extraBall.update(deltaTime);

            // Remove extra ball when it dies
            if (extraBall.getLives() <= 0) {
                System.out.println("⚽ Extra ball removed from game");
                ballIterator.remove();
            }
        }

        // === KEY CHANGE: NEW LIVES LOGIC ===
        // Check if ALL balls have fallen (main + extras)
        boolean mainBallFell = ball.getLives() > 0 && !ball.play &&
                ball.getPositionY() > Constants.SCREEN_HEIGHT - 200;
        boolean noExtraBalls = ballList.isEmpty();

        if (mainBallFell && noExtraBalls) {
            // ALL balls fell → Decrement player lives
            ball.setLives(ball.getLives() - 1);
            System.out.println("💔 All balls fell! Lives remaining: " + ball.getLives());

            if (ball.getLives() > 0) {
                // Respawn main ball
                ball.setPositionX(Constants.BALL_START_POSITION_X);
                ball.setPositionY(Constants.BALL_START_POSITION_Y);
                ball.setDirectionX(0.0);
                ball.setDirectionY(1.0);
                System.out.println("⚽ Ball respawned! Lives remaining: " + ball.getLives());
            } else {
                System.out.println("💀 Game Over!");
            }
        }

        // Update power-ups
        powerUpManager.update(deltaTime);

        checkCollision();
    }

    private void checkCollision() {
        // Main ball collisions (only if has lives)
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
        // Render main ball (only if has lives)
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

    public void setScore(int value) {
        score.set(value);
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty getScoreProperty() {
        return score;
    }

    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }
}