package CollisionManager;

import audio.SoundManager;
import entities.Ball;
import entities.Paddle;
import utils.Constants;

public class BallWithPaddle {

    public static void checkCollision(Ball ball, Paddle paddle, SoundManager soundManager) {

        double ballCenterX = ball.getPositionX() + ball.getRadius();
        double ballCenterY = ball.getPositionY() + ball.getRadius();
        double paddleCenterX = paddle.getPositionX() + paddle.getWidth() / 2.0;
        double paddleCenterY = paddle.getPositionY() + paddle.getHeight() / 2.0;

        double vectorX = ballCenterX - paddleCenterX;
        double vectorY = ballCenterY - paddleCenterY;

        double lengthX = paddle.getWidth() / 2.0 + ball.getRadius();
        double lengthY = paddle.getHeight() / 2.0 + ball.getRadius();

        double overlapX = lengthX - Math.abs(vectorX);
        double overlapY = lengthY - Math.abs(vectorY);

        if (overlapX > 0 && overlapY > 0) {
            soundManager.play("hit");
            if (Math.abs(overlapX - overlapY) <= Constants.EPSILON) {
                if (vectorX > 0) ball.setPositionX(ball.getPositionX() + overlapX);
                else ball.setPositionX(ball.getPositionX() - overlapX);
                if (vectorY > 0) ball.setPositionY(ball.getPositionY() + overlapY);
                else ball.setPositionY(ball.getPositionY() - overlapY);
                ball.setDirection(ballCenterX, paddleCenterX, paddle.getWidth());
            } else if (overlapY < overlapX) {
                if (vectorY > 0) ball.setPositionY(ball.getPositionY() + overlapY);
                else ball.setPositionY(ball.getPositionY() - overlapY);
                if (ball.getDirectionY() > 0 && vectorY < 0) ball.setDirection(ballCenterX, paddleCenterX, paddle.getWidth());
                else ball.setDirectionY(-ball.getDirectionY());
            } else {
                if (vectorX > 0) ball.setPositionX(ball.getPositionX() + overlapX);
                else ball.setPositionX(ball.getPositionX() - overlapX);
                ball.setDirectionX(-ball.getDirectionX());
            }
        }
    }
}