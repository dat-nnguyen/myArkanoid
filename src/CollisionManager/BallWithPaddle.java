package CollisionManager;

import UI.SceneManager;
import audio.SoundManager;
import entities.Ball;
import entities.BrickType;
import entities.Paddle;
import utils.Constants;

public class BallWithPaddle {

    /**
     * Check collision between ball and paddle.
     *
     * @param ball the ball you want to check.
     * @param paddle the paddle you want to check.
     */
    public static void checkCollision(Ball ball, Paddle paddle) {

        double ballCenterX = ball.getPositionX() + ball.getRadius();
        double ballCenterY = ball.getPositionY() + ball.getRadius();
        double paddleCenterX = paddle.getPositionX() + paddle.getWidth() / 2.0;
        double paddleCenterY = paddle.getPositionY() + paddle.getHeight() / 2.0;

        double distanceX = ballCenterX - paddleCenterX;
        double distanceY = ballCenterY - paddleCenterY;

        double lengthX = paddle.getWidth() / 2.0 + ball.getRadius();
        double lengthY = paddle.getHeight() / 2.0 + ball.getRadius();

        double overlapX = lengthX - Math.abs(distanceX);
        double overlapY = lengthY - Math.abs(distanceY);

        if (overlapX > 0 && overlapY > 0) {
            SceneManager.getInstance().getSoundManager().play("hit");
            if (Math.abs(overlapX - overlapY) < Constants.EPSILON) {
                if (distanceX > 0) {
                    ball.setPositionX(ball.getPositionX() + overlapX);
                }
                else {
                    ball.setPositionX(ball.getPositionX() - overlapX);
                }
                if (distanceY > 0) {
                    ball.setPositionY(ball.getPositionY() + overlapY);
                }
                else {
                    ball.setPositionY(ball.getPositionY() - overlapY);
                }
                ball.setDirection(ballCenterX, paddleCenterX, paddle.getWidth());
            } else if (overlapY < overlapX) {
                if (distanceY > 0) {
                    ball.setPositionY(ball.getPositionY() + overlapY);
                }
                else {
                    ball.setPositionY(ball.getPositionY() - overlapY);
                }
                if (ball.getDirectionY() > 0 && distanceY < 0) {
                    ball.setDirection(ballCenterX, paddleCenterX, paddle.getWidth());
                }
                else {
                    ball.setDirectionY(-ball.getDirectionY());
                }
            } else {
                if (distanceX > 0) {
                    ball.setPositionX(ball.getPositionX() + overlapX);
                }
                else {
                    ball.setPositionX(ball.getPositionX() - overlapX);
                }
                ball.setDirectionX(-ball.getDirectionX());
            }
        }
    }
}