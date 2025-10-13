package CollisionManager;

import entities.Ball;
import entities.Paddle;
import utils.Constants;

public class BallWithPaddle {

    public static void checkCollision(Ball ball, Paddle paddle) {

        double ballCenterX = ball.getPositionX() + ball.getRadius();
        double ballCenterY = ball.getPositionY() + ball.getRadius();
        double ballRadius = ball.getRadius();
        double paddleCenterX = paddle.getPositionX() + paddle.getWidth() / 2.0;

        double checkPointX;
        double checkPointY;

        // Compute bound x-axis
        if (ballCenterX < paddle.getPositionX()) {
            checkPointX = paddle.getPositionX();
        } else if (ballCenterX > paddle.getPositionX() + paddle.getWidth()) {
            checkPointX = paddle.getPositionX() + paddle.getWidth();
        } else {
            checkPointX = ballCenterX;
        }

        // Compute bound y-axis
        if (ballCenterY < paddle.getPositionY()) {
            checkPointY = paddle.getPositionY();
        } else if (ballCenterY > paddle.getPositionY() + paddle.getHeight()) {
            checkPointY = paddle.getPositionY() + paddle.getHeight();
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
            if (distance > Constants.EPSILON) {
                double newPositionX = ball.getPositionX() - (diffX / distance) * overlap;
                double newPositionY = ball.getPositionY() - (diffY / distance) * overlap;
                ball.setPositionX(newPositionX);
                ball.setPositionY(newPositionY);
            }
            if (checkPointY == paddle.getPositionY()) {
                boolean isLeft = ballCenterX < paddleCenterX;
                ball.setRandomDirection(isLeft);
            } else if (checkPointX == paddle.getPositionX()
                    || checkPointX == (paddle.getPositionX() + paddle.getWidth())) {
                ball.setDirectionX(ball.getDirectionX() * (-1));
            } else {
                ball.setDirectionX(ball.getDirectionX() * (-1));
                ball.setDirectionY(ball.getDirectionY() * (-1));
            }
        }
    }
}
