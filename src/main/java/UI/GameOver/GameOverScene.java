package UI.GameOver;

import UI.SceneManager;
import audio.SoundManager;
import javafx.fxml.FXML;

public class GameOverScene {

    private final SoundManager soundManager = SceneManager.getInstance().getSoundManager();

    @FXML
    public void ExitToMenu(){
        soundManager.play("click");
        SceneManager.getInstance().switchTo("Menu");
    }

    @FXML
    public void restartGame(){
        soundManager.play("click");
        SceneManager.getInstance().switchTo("Game");
    }

}