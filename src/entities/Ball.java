package entities;

import core.Constants;
import core.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class Ball extends MovableObject {

    private double speed = Constants.BALL_SPEED;
    private int radius = Constants.BALL_RADIUS;
    private double directionX;
    private double directionY;
    private boolean isOver = false;
    private int lives = Constants.BALL_LIVES;
    private final Random rd = new Random();

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

    public Ball() {
        super(Constants.START_POSITION_X, Constants.START_POSITION_Y, Constants.BALL_RADIUS * 2, Constants.BALL_RADIUS * 2);
        boolean isLeft = (rd.nextInt(2) == 0);
        setRandomDirection(isLeft);
        this.setDeltaX(speed * directionX);
        this.setDeltaY(speed * directionY);
        System.out.println("Init: " + this.getPositionX() + " " + this.getPositionY());
    }

    @Override
    public void move(double deltaTime) {
        double newPositionX = this.getPositionX() + this.getDeltaX()*deltaTime;
        double newPositionY = this.getPositionY() + this.getDeltaY()*deltaTime;

        //Check bound screen
        if (newPositionX + this.getWidth() > Constants.SCREEN_WIDTH) {
            newPositionX = Constants.SCREEN_WIDTH - this.getWidth();
            directionX *= -1;
            this.setDeltaX(speed * directionX);
        } else if (newPositionX <= 0) {
            newPositionX = this.getWidth();
            directionX *= -1;
            this.setDeltaX(speed * directionX);
        }
        if (newPositionY >= Constants.SCREEN_HEIGHT + 10) {
            // Để thêm 10 tạo 1 khoảng rơi rồi mới update mạng
            lives -= 1;
            System.out.println("Lives Remaining: " + lives);
            if (lives <= 0) {
                isOver = true;
                System.out.println("Game Over !");
                return;
            }
            newPositionX = Constants.START_POSITION_X;
            newPositionY = Constants.START_POSITION_Y;
            System.out.println("Reset: " + newPositionX + " " + newPositionY);
            boolean isLeft = (rd.nextInt(2) == 0);
            setRandomDirection(isLeft);
            this.setDeltaX(speed * directionX);
            this.setDeltaY(speed * directionY);
        } else if (newPositionY <= 0) {
            newPositionY = this.getHeight();
            directionY *= -1;
            this.setDeltaY(speed * directionY);
        }
        this.setPositionX(newPositionX);
        this.setPositionY(newPositionY);
    }

    @Override
    public void update(double deltaTime) {
        if (!isOver) move(deltaTime);
        //Update power sau...
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(this.getPositionX(), this.getPositionY(), radius * 2, radius * 2);
    }

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
