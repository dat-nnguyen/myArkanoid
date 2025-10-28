package UI.GameOver;

import UI.SceneManager;
import javafx.fxml.FXML;

public class GameOverScene {
    @FXML
    public void ExitToMenu(){
        SceneManager.getInstance().switchTo("Menu");
    }

    @FXML
    public void restartGame(){
        SceneManager.getInstance().switchTo("Game");
    }
}