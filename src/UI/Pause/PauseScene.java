package UI.Pause;

import UI.SceneManager;
import javafx.fxml.FXML;

public class PauseScene {

  @FXML
  private void onContinueClicked() {
      System.out.println("Continue clicked!");
      SceneManager.getInstance().getSoundManager().play("click");
      SceneManager.getInstance().switchTo("Game");
  }

  @FXML
  private void onQuitClicked() {
      SceneManager.getInstance().getGameSceneInstance().updateHighScore();
      System.out.println("Quit clicked!");
      SceneManager.getInstance().getSoundManager().play("click");
      SceneManager.getInstance().switchTo("Menu");
  }
}
