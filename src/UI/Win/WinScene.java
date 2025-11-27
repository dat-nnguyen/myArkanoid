package UI.Win;

import UI.SceneManager;
import javafx.fxml.FXML;

public class WinScene {

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
