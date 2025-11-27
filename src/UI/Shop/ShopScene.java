package UI.Shop;

import UI.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;

public class ShopScene implements Initializable  {
  @FXML
  private ScrollPane paddleScrollPane;
  @FXML
  private ScrollPane ballScrollPane;

  @FXML
  private ToggleButton paddleButton;
  @FXML
  private ToggleButton ballButton;
  @FXML
  private ToggleGroup shopButtons;
  @FXML
  private Button exitButton;

  @FXML
  private TilePane paddleSkinContain;
  @FXML
  private TilePane ballSkinContain;

  @FXML
  private Label shopCreditsLabel;

  private final CreditsManager creditsManager = CreditsManager.getInstance();
  private final SkinManager skinManager = SkinManager.getInstance();


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //Binding credits label
    shopCreditsLabel.textProperty().bind(Bindings.concat("$", creditsManager.getCreditsProperty().asString()));

    // Create listener for shop button
    shopButtons.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
      if (newToggle == null){
        if(oldToggle != null){
          oldToggle.setSelected(true);
        }
        return;
      }

      // Pick current button
      ToggleButton selectedButton = (ToggleButton) newToggle;

      // Check button.
      if(selectedButton == paddleButton){
          SceneManager.getInstance().getSoundManager().play("click");
          paddleScrollPane.setVisible(true);
           ballScrollPane.setVisible(false);
      }
      else if(selectedButton == ballButton){
          SceneManager.getInstance().getSoundManager().play("click");
          paddleScrollPane.setVisible(false);
           ballScrollPane.setVisible(true);
      }
    });

    exitButton.setOnAction(e -> {
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager.getInstance().switchTo("Menu");
    });

    loadPaddleSkins();
    loadBallSkins();
  }

  private void loadPaddleSkins(){
    //Clear
    paddleSkinContain.getChildren().clear();

    for(SkinData skin : skinManager.getPaddleSkins()){
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Shop/Items.fxml"));
        Node itemNode = loader.load();
        ItemController controller = loader.getController();

        controller.setData(skin, true, creditsManager, skinManager, this::refreshPaddleList);

        paddleSkinContain.getChildren().add(itemNode);
      } catch(Exception e){
          System.err.println(e.getMessage());;
      }
    }
  }


  private void loadBallSkins(){
    //Clear
    ballSkinContain.getChildren().clear();

    for(SkinData skin : skinManager.getBallSkins()){
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Shop/Items.fxml"));
        Node itemNode = loader.load();
        ItemController controller = loader.getController();

        controller.setData(skin, false, creditsManager, skinManager, this::refreshBallList);

        ballSkinContain.getChildren().add(itemNode);
      } catch(Exception e){
          System.err.println(e.getMessage());
      }
    }
  }

  private void refreshPaddleList(){
    loadPaddleSkins();
  }

  private void refreshBallList(){
    loadBallSkins();
  }

}
