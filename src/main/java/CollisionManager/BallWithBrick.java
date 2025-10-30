package CollisionManager;

import UI.SceneManager;
import audio.SoundManager;
import entities.Ball;
import entities.Brick;
import entities.BrickType;
import utils.Constants;

public class BallWithBrick {

    public static void checkCollision(Ball ball, Brick brick) {
        if (brick.getIsDestroyed()) return;

        double ballCenterX = ball.getPositionX() + ball.getRadius();
        double ballCenterY = ball.getPositionY() + ball.getRadius();
        double brickCenterX = brick.getPositionX() + brick.getWidth() / 2.0;
        double brickCenterY = brick.getPositionY() + brick.getHeight() / 2.0;

        double vectorX = ballCenterX - brickCenterX;
        double vectorY = ballCenterY - brickCenterY;

        double lengthX = brick.getWidth() / 2.0 + ball.getRadius();
        double lengthY = brick.getHeight() / 2.0 + ball.getRadius();

        double overlapX = lengthX - Math.abs(vectorX);
        double overlapY = lengthY - Math.abs(vectorY);

        if (overlapX > 0 && overlapY > 0) {
            SceneManager.getInstance().getSoundManager().play("hit");
            if (Math.abs(overlapX - overlapY) <= Constants.EPSILON) {
                if (vectorX > 0) ball.setPositionX(ball.getPositionX() + overlapX);
                else ball.setPositionX(ball.getPositionX() - overlapX);
                ball.setDirectionX(-ball.getDirectionX());
                if (vectorY > 0) ball.setPositionY(ball.getPositionY() + overlapY);
                else ball.setPositionY(ball.getPositionY() - overlapY);
                ball.setDirectionY(-ball.getDirectionY());
            } else if (overlapY < overlapX) {
                if (vectorY > 0) ball.setPositionY(ball.getPositionY() + overlapY);
                else ball.setPositionY(ball.getPositionY() - overlapY);
                ball.setDirectionY(-ball.getDirectionY());
            } else {
                if (vectorX > 0) ball.setPositionX(ball.getPositionX() + overlapX);
                else ball.setPositionX(ball.getPositionX() - overlapX);
                ball.setDirectionX(-ball.getDirectionX());
            }
            if (!brick.getCurrentBrickType().isUnbreakable()) brick.setLives(brick.getLives() - 1);
        }
    }
}