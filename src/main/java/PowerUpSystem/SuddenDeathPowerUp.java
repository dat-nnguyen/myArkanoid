package PowerUpSystem;

import entities.Ball;
import java.util.ArrayList;

/**
 * INSTANT GAME OVER (bad power-up).
 * Sets all balls' lives to 0.
 */
public class SuddenDeathPowerUp extends InstantPowerUp {

    public SuddenDeathPowerUp(double positionX, double positionY) {
        super(positionX, positionY, PowerUpType.SUDDEN_DEATH);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        Ball mainBall = context.getBall();
        ArrayList<Ball> ballList = context.getBallList();

        // Kill main ball
        mainBall.setLives(0);
        mainBall.play = false;

        // Kill all extra balls
        for (Ball extraBall : ballList) {
            extraBall.setLives(0);
            extraBall.play = false;
        }

        System.out.println("💀 SUDDEN DEATH! Game Over...");
    }
}