package UI.GameOver;

import UI.SceneManager;
import audio.SoundManager;
import javafx.fxml.FXML;

public class GameOverScene {

    @FXML
    private void ExitToMenu(){
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager.getInstance().switchTo("Menu");
    }

    @FXML
    private void restartGame(){
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager.getInstance().switchTo("Game");
    }

}