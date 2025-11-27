package CollisionManager;

import UI.SceneManager;
import audio.SoundManager;
import entities.Ball;
import entities.Brick;
import entities.BrickType;
import utils.Constants;

public class BallWithBrick {

    /**
     * Check collision between ball and brick.
     *
     * @param ball the ball you want to check.
     * @param brick the brick you want to check.
     */
    public static void checkCollision(Ball ball, Brick brick) {
        if (brick.getIsDestroyed()) {
            return;
        }

        double ballCenterX = ball.getPositionX() + ball.getRadius();
        double ballCenterY = ball.getPositionY() + ball.getRadius();
        double brickCenterX = brick.getPositionX() + brick.getWidth() / 2.0;
        double brickCenterY = brick.getPositionY() + brick.getHeight() / 2.0;

        double distanceX = ballCenterX - brickCenterX;
        double distanceY = ballCenterY - brickCenterY;

        double lengthX = brick.getWidth() / 2.0 + ball.getRadius();
        double lengthY = brick.getHeight() / 2.0 + ball.getRadius();

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
                ball.setDirectionX(-ball.getDirectionX());
                if (distanceY > 0) {
                    ball.setPositionY(ball.getPositionY() + overlapY);
                }
                else {
                    ball.setPositionY(ball.getPositionY() - overlapY);
                }
                ball.setDirectionY(-ball.getDirectionY());
            } else if (overlapY < overlapX) {
                if (distanceY > 0) {
                    ball.setPositionY(ball.getPositionY() + overlapY);
                }
                else {
                    ball.setPositionY(ball.getPositionY() - overlapY);
                }
                ball.setDirectionY(-ball.getDirectionY());
            } else {
                if (distanceX > 0) {
                    ball.setPositionX(ball.getPositionX() + overlapX);
                }
                else {
                    ball.setPositionX(ball.getPositionX() - overlapX);
                }
                ball.setDirectionX(-ball.getDirectionX());
            }
            if (!brick.getCurrentBrickType().isUnbreakable()) {
                brick.setLives(brick.getLives() - 1);
            }
        }
    }
}