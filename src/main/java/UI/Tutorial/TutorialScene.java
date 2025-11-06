package UI.Tutorial;

import UI.SceneManager;
import audio.SoundManager;
import javafx.fxml.FXML;

public class TutorialScene {

    private final SoundManager soundManager = SceneManager.getInstance().getSoundManager();

    @FXML
    public void exitToMenu(){
        soundManager.play("click");
        SceneManager.getInstance().switchTo("Menu");
    }


}