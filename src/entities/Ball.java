package entities;

import UI.SceneManager;
import audio.SoundManager;
import javafx.scene.image.Image;
import utils.Constants;
import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Ball management.
 * FIXED: Main ball doesn't auto-decrement lives when falling.
 */
public class Ball extends MovableObject {

    private double speed = Constants.BALL_SPEED;
    private int radius = Constants.BALL_RADIUS;
    private double directionX = 0.0;
    private double directionY = 1.0;
    private int lives = Constants.BALL_LIVES;
    public boolean play = false;

    // NEW: Flag to distinguish main ball from extra balls
    private boolean isMainBall = true;

    /**
     * Set ball direction (30 to 150 degrees).
     */
    public void setDirection(double ballCenterX, double paddleCenterX, double paddleWidth) {
        double relativeIntersectX = ballCenterX - paddleCenterX;
        double normalize = relativeIntersectX / (paddleWidth / 2.0);
        if (normalize > 1.0) normalize = 1.0;
        else if (normalize < -1.0) normalize = -1.0;
        double baseAngle = Math.PI / 2;
        double maxAngleOffset = Math.PI / 3;
        double bounceAngle = baseAngle - (normalize * maxAngleOffset);
        directionX = Math.cos(bounceAngle);
        directionY = -Math.sin(bounceAngle);
    }

    /**
     * Initialize main ball.
     */
    public Ball() {
        super(Constants.BALL_START_POSITION_X, Constants.BALL_START_POSITION_Y,
                Constants.BALL_RADIUS * 2, Constants.BALL_RADIUS * 2, Constants.BALL_TEXTURE_PATH);
        this.isMainBall = true;
        System.out.println("⚽ Ball Init Success !");
        System.out.println("⚽ Remaining lives: " + lives);
    }

    @Override
    public void move(double deltaTime) {
        double xMin = Constants.MARGIN_WIDTH;
        double xMax = xMin + Constants.PLAY_SCREEN_WIDTH;
        double yMin = Constants.MARGIN_HEIGHT;
        double yMax = yMin + Constants.PLAY_SCREEN_HEIGHT;

        this.setDeltaX(directionX * speed * deltaTime);
        this.setDeltaY(directionY * speed * deltaTime);

        double newPositionX = this.getPositionX() + this.getDeltaX();
        double newPositionY = this.getPositionY() + this.getDeltaY();

        // Wall collisions
        if (newPositionX + this.getWidth() > xMax) {
            SceneManager.getInstance().getSoundManager().play("hit");
            newPositionX = xMax - this.getWidth();
            directionX *= -1;
        } else if (newPositionX < xMin) {
            SceneManager.getInstance().getSoundManager().play("hit");
            newPositionX = xMin;
            directionX *= -1;
        }

        if (newPositionY < yMin) {
            SceneManager.getInstance().getSoundManager().play("hit");
            newPositionY = yMin;
            directionY *= -1;
        } else if (newPositionY > yMax + 20) {
            // Ball fell below screen
            SceneManager.getInstance().getSoundManager().play("dead");

            // === KEY CHANGE ===
            if (isMainBall) {
                // Main ball: just stop playing, DON'T decrement lives
                play = false;
                System.out.println("⚽ Main ball fell! Waiting for all balls to fall...");
            } else {
                // Extra ball: decrement its own life (will be removed by GameLoop)
                lives -= 1;
                play = false;
                System.out.println("⚽ Extra ball fell! Lives: " + lives);
            }

            return; // Don't update position
        }

        this.setPositionX(newPositionX);
        this.setPositionY(newPositionY);
    }

    @Override
    public void update(double deltaTime) {
        if (lives > 0 && play) move(deltaTime);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (lives > 0) {
            gc.drawImage(this.getTexture(), this.getPositionX(), this.getPositionY(),
                    this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void reset() {
        speed = Constants.BALL_SPEED;
        radius = Constants.BALL_RADIUS;
        directionX = 0.0;
        directionY = 1.0;
        lives = Constants.BALL_LIVES;
        play = false;
        this.setPositionX(Constants.BALL_START_POSITION_X);
        this.setPositionY(Constants.BALL_START_POSITION_Y);
        this.setWidth(Constants.BALL_RADIUS * 2);
        this.setHeight(Constants.BALL_RADIUS * 2);
    }

    // Getters and Setters
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public int getRadius() { return radius; }
    public void setRadius(int radius) { this.radius = radius; }
    public double getDirectionX() { return directionX; }
    public void setDirectionX(double directionX) { this.directionX = directionX; }
    public double getDirectionY() { return directionY; }
    public void setDirectionY(double directionY) { this.directionY = directionY; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }

    // NEW: Getter/Setter for main ball flag
    public boolean isMainBall() { return isMainBall; }
    public void setMainBall(boolean mainBall) { isMainBall = mainBall; }
}