package core;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    private final GameManager gameManager;
    private long lastUpdate = 0;

    public GameLoop(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void handle(long now) {
        // skip first frame to initialize lastUpdate
        if (lastUpdate == 0) {
            lastUpdate = now;
            return;
        }

        // calculate time between two frame using second
        float deltaTime = (float) ((now - lastUpdate) / 1e9);
        lastUpdate = now;

        gameManager.update(deltaTime);
    }
}