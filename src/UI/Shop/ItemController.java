package UI.Shop;

import java.io.InputStream;

import UI.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ItemController{
  @FXML
  private ImageView skinImage;
  @FXML
  private Label priceLabel;
  @FXML
  private Button buyButton;
  @FXML
  private Button equipButton;

  private SkinData skin;
  private boolean isPaddle;
  private CreditsManager creditsManager;
  private SkinManager skinManager;
  private Runnable refreshUI;

  public void setData(SkinData skin, boolean isPaddle, CreditsManager creditsManager,
      SkinManager skinManager, Runnable refreshUI) {
    this.skin = skin;
    this.isPaddle = isPaddle;
    this.creditsManager = creditsManager;
    this.skinManager = skinManager;
    this.refreshUI = refreshUI;

    // Set image
    try {
      InputStream imgStream = getClass().getResourceAsStream(skin.getImagePath());
      if (imgStream != null) {
        Image img = new Image(imgStream);
        skinImage.setImage(img);
        if(isPaddle){
          skinImage.setFitWidth(200);
          skinImage.setFitHeight(22);
        } else {
          skinImage.setFitWidth(27);
          skinImage.setFitHeight(27);
        }
      } else {
        System.err.println("Could not find image at path: " + skin.getImagePath());
      }
    } catch (Exception e) {
      System.out.println("Error loading image: " + e.getMessage());
    }

    priceLabel.setText("$" + skin.getPrice());
    if(skin.getId() == 1){
      priceLabel.setText("Free");
    }

    updateButtons();

    buyButton.setOnAction(e -> {
        SceneManager.getInstance().getSoundManager().play("click");
        handleBuy();
    });
    equipButton.setOnAction(e -> {
        SceneManager.getInstance().getSoundManager().play("click");
        handleEquip();
    });
  }

  private void updateButtons(){
    //Buy Button
    if(skin.isOwned()){
      buyButton.setText("Owned");
      buyButton.setDisable(true);
    }
    else {
      buyButton.setText("Buy");
      buyButton.setDisable(false);
    }

    //Equip Button
    boolean isEquipped = (isPaddle) ? (skinManager.getSelectedPaddle().getId() == skin.getId()) :
        (skinManager.getSelectedBall().getId() == skin.getId());

    if(!skin.isOwned()){
      equipButton.setText("Equip");
      equipButton.setDisable(true);
    } else if(isEquipped){
      equipButton.setText("Equipped");
      equipButton.setDisable(true);
    }
    else{
      equipButton.setText("Equip");
      equipButton.setDisable(false);
    }
  }

  private void handleBuy(){
    if(!skin.isOwned()){
      if(creditsManager.spendCredits(skin.getPrice())){
        boolean bought = isPaddle ? skinManager.buyPaddle(skin.getId()) : skinManager.buyBall(skin.getId());
        if(bought && refreshUI != null) refreshUI.run();
      } else {
        showAlert();
      }
    }
  }

  private void handleEquip(){
    if(skin.isOwned()){
      if(isPaddle){
        skinManager.selectPaddle(skin.getId());
      } else {
        skinManager.selectBall(skin.getId());
      }
      if(refreshUI != null) refreshUI.run();
    }
  }

  private void showAlert(){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Shop");
    alert.setHeaderText(null);
    alert.setContentText("Not enough credits to buy this skin.");
    alert.showAndWait();
  }
}




