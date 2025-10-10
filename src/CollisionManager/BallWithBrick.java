package CollisionManager;

import entities.Ball;
import entities.Brick;
import entities.BrickType;

public class BallWithBrick {

    public static void checkCollision(Ball ball, Brick brick) {
        double ballCenterX = ball.getPositionX() + ball.getRadius();
        double ballCenterY = ball.getPositionY() + ball.getRadius();
        double ballRadius = ball.getRadius();

        double checkPointX;
        double checkPointY;

        // Compute bound x-axis
        if (ballCenterX < brick.getPositionX()) {
            checkPointX = brick.getPositionX();
        } else if (ballCenterX > brick.getPositionX() + brick.getWidth()) {
            checkPointX = brick.getPositionX() + brick.getWidth();
        } else {
            checkPointX = ballCenterX;
        }

        // Compute bound y-axis
        if (ballCenterY < brick.getPositionY()) {
            checkPointY = brick.getPositionY();
        } else if (ballCenterY > brick.getPositionY() + brick.getHeight()) {
            checkPointY = brick.getPositionY() + brick.getHeight();
        } else {
            checkPointY = ballCenterY;
        }

        // Compute distance to check collision
        double diffX = checkPointX - ballCenterX;
        double diffY = checkPointY - ballCenterY;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        //Check distance
        if (distance <= ballRadius) {
            double overlap = ballRadius - distance;
            if (distance != 0) {
                double newPositionX = ball.getPositionX() - (diffX / distance) * overlap;
                double newPositionY = ball.getPositionY() - (diffY / distance) * overlap;
                ball.setPositionX(newPositionX);
                ball.setPositionY(newPositionY);
            }
            if (Math.abs(diffX) > Math.abs(diffY)) {
                ball.setDirectionX(ball.getDirectionX() * (-1));
                ball.setDeltaX(ball.getDirectionX() * ball.getSpeed());
            } else {
                ball.setDirectionY(ball.getDirectionY() * (-1));
                ball.setDeltaY(ball.getDirectionY() * ball.getSpeed());
            }
            if (brick.getCurrentBrickType() != BrickType.IMPOSSIBLE) brick.setLives(brick.getLives() - 1);
        }
    }
}
