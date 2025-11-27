package UI.Game;

import PowerUpSystem.PowerUpType;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.collections.MapChangeListener;

public class ActivePowerUpListener implements MapChangeListener<PowerUpType, DoubleProperty> {

    private final GameScene gameScene;

    public ActivePowerUpListener(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public void onChanged(Change<? extends PowerUpType, ? extends DoubleProperty> change) {
        Platform.runLater(() -> {
            if (change.wasAdded()) {
                gameScene.createPowerUpUI(change.getKey(), change.getValueAdded());
            } else if (change.wasRemoved()) {
                gameScene.removePowerUpUI(change.getKey());
            }
        });
    }
}
