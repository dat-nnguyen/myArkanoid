package UI.Tutorial;

import UI.SceneManager;
import javafx.fxml.FXML;

public class TutorialScene {

    @FXML
    public void exitToMenu(){
        SceneManager.getInstance().switchTo("Menu");
    }

}