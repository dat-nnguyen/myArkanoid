package UI.Menu;

import UI.SceneManager;
import UI.Game.GameScene;
import audio.SoundManager;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import utils.Constants;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuScene implements Initializable {

    private boolean isGameInitialized = false;

    @FXML
    private AnchorPane rootPane;

    private final String[] LAYER_IMAGES = {
            "/images/menuImages/background/1.png",
            "/images/menuImages/background/2.png",
            "/images/menuImages/background/3.png",
            "/images/menuImages/background/4.png",
            "/images/menuImages/background/5.png",
            "/images/menuImages/background/6.png",
            "/images/menuImages/background/7.png",
            "/images/menuImages/background/8.png"
    };

    private final double[] LAYER_SPEEDS = {
            80.0,  // Lớp 1 (còn 80 giây)
            65.0,
            50.0,
            40.0,
            30.0,
            20.0,
            10.0,
            5.0
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int i = 0; i < LAYER_IMAGES.length; i++) {
            String imagePath = LAYER_IMAGES[i];
            double speed = LAYER_SPEEDS[i];
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView imgViewA = new ImageView(img);
            ImageView imgViewB = new ImageView(img);

            imgViewA.setFitWidth(Constants.SCREEN_WIDTH);
            imgViewA.setFitHeight(Constants.SCREEN_HEIGHT);
            imgViewB.setFitWidth(Constants.SCREEN_WIDTH);
            imgViewB.setFitHeight(Constants.SCREEN_HEIGHT);

            HBox layerHBox = new HBox(imgViewA, imgViewB);
            rootPane.getChildren().add(i, layerHBox);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(speed), layerHBox);
            tt.setFromX(0);
            tt.setToX(-Constants.SCREEN_WIDTH);
            tt.setInterpolator(Interpolator.LINEAR);
            tt.setCycleCount(Animation.INDEFINITE);
            tt.play();
        }
    }

    @FXML
    public void startClicked() {
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

    public void tutorialClicked() {
        SceneManager.getInstance().getSoundManager().play("click");
        SceneManager sm = SceneManager.getInstance();
        sm.switchTo("Tutorial");
    }

    public void shopClicked() {
        SceneManager.getInstance().getSoundManager().play("click");
        // (code...)
    }

}