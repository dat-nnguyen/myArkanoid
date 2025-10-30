import UI.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author admin
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        SceneManager sm = SceneManager.getInstance();
        sm.setPrimaryStage(primaryStage);

        // tai menu (fxml)
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MenuScene.fxml"));
        Pane menuRoot = menuLoader.load();
        sm.addScene("Menu", new Scene(menuRoot));

        FXMLLoader tutorialLoader = new FXMLLoader(getClass().getResource("TutorialScene.fxml"));
        Pane tutorialRoot = tutorialLoader.load();
        sm.addScene("Tutorial", new Scene(tutorialRoot));

        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("GameOverScene.fxml"));
        Pane gameOverRoot = gameOverLoader.load();
        sm.addScene("GameOver", new Scene(gameOverRoot));

        // chinh ti'
        primaryStage.setTitle("Anh Jack oi em yeu anh nhieu lam ❤❤❤");
        primaryStage.setResizable(false);
        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.jpg")));
        primaryStage.getIcons().add(appIcon);

        // bat dau ne
        sm.switchTo("Menu");
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}