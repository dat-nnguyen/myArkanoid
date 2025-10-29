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
 *
 */
public class Ball extends MovableObject {

    private double speed = Constants.BALL_SPEED; // Default ball movement speed.
    private int radius = Constants.BALL_RADIUS; // Default ball radius.
    private double directionX = 0.0; // x-axis ball direction.
    private double directionY = 1.0; // y-axis ball direction.
    private int lives = Constants.BALL_LIVES; // Remaining lives.
    public boolean play = false; // Want to play ?

    /**
     * Set ball direction (30 to 150 degrees).
     *
     * @param ballCenterX PositionX of the center of the ball.
     * @param paddleCenterX PositionX of the center of the paddle.
     * @param paddleWidth The height of the paddle.
     */
    public void setDirection(double ballCenterX, double paddleCenterX, double paddleWidth) {
        // Determine the ball's position relative to the paddle (left, middle, or right).
        double relativeIntersectX = ballCenterX - paddleCenterX; // left ( < 0), middle ( = 0), right (> 0).
        // Scale the ball's relative position to a value between -1.0 and 1.0.
        double normalize = relativeIntersectX / (paddleWidth / 2.0);
        if (normalize > 1.0) normalize = 1.0;
        else if (normalize < -1.0) normalize = -1.0;
        double baseAngle = Math.PI / 2;
        double maxAngleOffset = Math.PI / 3;
        double bounceAngle = baseAngle - (normalize * maxAngleOffset); // (30 - 150 degree).
        directionX = Math.cos(bounceAngle);
        directionY = -Math.sin(bounceAngle);
    }


    /**
     * Initialize object.
     *
     */
    public Ball() {
        super(Constants.BALL_START_POSITION_X, Constants.BALL_START_POSITION_Y, Constants.BALL_RADIUS * 2, Constants.BALL_RADIUS * 2, Constants.BALL_TEXTURE_PATH);
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
            // Ball rơi xuống dưới
            SceneManager.getInstance().getSoundManager().play("dead");
            lives -= 1;
            play = false;
            System.out.println("⚽ Remaining lives: " + lives);

            if (lives <= 0) {
                System.out.println("💀 Ball died!");
                // KHÔNG reset position, để GameLoop xử lý
                return;
            }

            // KHÔNG TỰ ĐỘNG RESET - GameLoop sẽ xử lý
            // Giữ nguyên vị trí để GameLoop biết ball đã chết
            return;
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
            gc.drawImage(this.getTexture(), this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void reset() {
        speed = Constants.BALL_SPEED; // Default ball movement speed.
        radius = Constants.BALL_RADIUS; // Default ball radius.
        directionX = 0.0; // x-axis ball direction.
        directionY = 1.0; // y-axis ball direction.
        lives = Constants.BALL_LIVES; // Remaining lives.
        play = false;
        this.setPositionX(Constants.BALL_START_POSITION_X);
        this.setPositionY(Constants.BALL_START_POSITION_Y);
        this.setWidth(Constants.BALL_RADIUS * 2);
        this.setHeight(Constants.BALL_RADIUS * 2);
    }

    //Getter and Setter method.

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

}