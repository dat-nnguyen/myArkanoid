package UI.Menu;

import UI.SceneManager;
import UI.Game.GameScene;

import UI.Shop.CreditsManager;
import UI.Shop.SkinManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class MenuScene {

    private boolean isGameInitialized = false;

    @FXML
    private void onStartClicked(MouseEvent e) {
        System.out.println("Start clicked!");
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager sm = SceneManager.getInstance();
        System.out.println("Start Clicked");

        if (!isGameInitialized) {
            System.out.println("Đang khởi tạo Game...");

            GameScene gameInstance = new GameScene();

            sm.addGameScene("Game", gameInstance);

            isGameInitialized = true;
            System.out.println("Game đã sẵn sàng!");

        } else {
            System.out.println("Game đã được tạo !");
        }
        sm.switchTo("Game");
    }

    @FXML
    private void onTutorialClicked(MouseEvent e) {
        System.out.println("Tutorial clicked!");
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager.getInstance().switchTo("Tutorial");
    }

    @FXML
    private void onShopClicked(MouseEvent e) {
        System.out.println("Shop clicked!");
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager.getInstance().switchTo("Shop");
    }

    @FXML
    private void onExitClicked(MouseEvent e) {
        CreditsManager.getInstance().saveCredits();
        SkinManager.getInstance().saveSkins();
        System.out.println("Exit clicked!");
        SceneManager.getInstance().getSoundManager().play("click");
        System.exit(0);
    }
}
