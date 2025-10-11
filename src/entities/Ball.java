package entities;

import utils.Constants;
import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Ball management.
 *
 */
public class Ball extends MovableObject {

    private double speed = Constants.BALL_SPEED; // Default ball movement speed.
    private int radius = Constants.BALL_RADIUS; // Default ball radius.
    private double directionX; // x-axis ball direction.
    private double directionY; // y-axis ball direction.
    private boolean isOver = false; // Check if game is over.
    private int lives = Constants.BALL_LIVES; // Remaining lives.
    private final Random rd = new Random(); // Ball direction randomizer.

    /**
     * Set ball direction to a random angle (30 to 150 degrees).
     *
     * @param isLeft Check if the ball's collision point with the paddle heavily biased to the left.
     */
    public void setRandomDirection(boolean isLeft) {
        double min, max;
        if (isLeft) {
            min = Math.toRadians(90);
            max = Math.toRadians(150);
        } else {
            min = Math.toRadians(30);
            max = Math.toRadians(90);
        }
        double res = min + (max - min) * rd.nextDouble();
        directionX = Math.cos(res);
        directionY = Math.sin(res) * (-1);
    }

    /**
     * Initialize object.
     *
     */
    public Ball() {
        super(Constants.START_POSITION_X, Constants.START_POSITION_Y, Constants.BALL_RADIUS * 2, Constants.BALL_RADIUS * 2);
        boolean isLeft = (rd.nextInt(2) == 0);
        setRandomDirection(isLeft);
        this.setDeltaX(speed * directionX);
        this.setDeltaY(speed * directionY);
        System.out.println("⚽ Ball Init Success !");
        System.out.println("⚽ Remaining lives: " + lives);
    }

    @Override
    public void move(double deltaTime) {

        this.setDeltaX(directionX * speed * deltaTime);
        this.setDeltaY(directionY * speed * deltaTime);
        double newPositionX = this.getPositionX() + this.getDeltaX();
        double newPositionY = this.getPositionY() + this.getDeltaY();

        if (newPositionX + this.getWidth() > Constants.SCREEN_WIDTH) {
            newPositionX = Constants.SCREEN_WIDTH - this.getWidth();
            directionX *= -1;
        } else if (newPositionX < 0) {
            newPositionX = 0;
            directionX *= -1;
        }

        if (newPositionY < 0) {
            newPositionY = 0;
            directionY *= -1;
        } else if (newPositionY > Constants.SCREEN_HEIGHT + 10) {
            lives -= 1;
            System.out.println("⚽ Remaining lives: " + lives);
            if (lives <= 0) {
                isOver = true;
                System.out.println("\uD83D\uDC80 Game Over !");
                return;
            }
            newPositionX = Constants.START_POSITION_X;
            newPositionY = Constants.START_POSITION_Y;
            boolean isLeft = (rd.nextInt(2) == 0);
            setRandomDirection(isLeft);
        }

        this.setPositionX(newPositionX);
        this.setPositionY(newPositionY);
    }

    @Override
    public void update(double deltaTime) {
        if (!isOver) move(deltaTime);
        //Update power-up sau...
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(this.getPositionX(), this.getPositionY(), radius * 2, radius * 2);
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

    public boolean isOver() { return isOver; }

    public void setOver(boolean isOver) { this.isOver = isOver; }

    public int getLives() { return lives; }

    public void setLives(int lives) { this.lives = lives; }
}
