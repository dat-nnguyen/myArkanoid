import UI.SceneManager;
import UI.Shop.SkinManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 *
 * @author admin
 */
public class Main extends Application {

    private SceneManager sm = SceneManager.getInstance();

    @Override
    public void start(Stage primaryStage) throws IOException {

        sm.setPrimaryStage(primaryStage);

        // tai menu (fxml)
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/UI/Menu/MenuScene.fxml"));
        Pane menuRoot = menuLoader.load();
        sm.addScene("Menu", new Scene(menuRoot));

        FXMLLoader tutorialLoader = new FXMLLoader(getClass().getResource("/UI/Tutorial/TutorialScene.fxml"));
        Pane tutorialRoot = tutorialLoader.load();
        sm.addScene("Tutorial", new Scene(tutorialRoot));

        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("/UI/GameOver/GameOverScene.fxml"));
        Pane gameOverRoot = gameOverLoader.load();
        sm.addScene("GameOver", new Scene(gameOverRoot));

        FXMLLoader pauseLoader = new FXMLLoader(getClass().getResource("/UI/Pause/PauseScene.fxml"));
        Pane pauseRoot = pauseLoader.load();
        sm.addScene("Pause", new Scene(pauseRoot));

        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("/UI/Shop/ShopScene.fxml"));
        Pane shopRoot = shopLoader.load();
        sm.addScene("Shop", new Scene(shopRoot));

        FXMLLoader winLoader = new FXMLLoader(getClass().getResource("/UI/Win/WinScene.fxml"));
        Pane winRoot = winLoader.load();
        sm.addScene("Win", new Scene(winRoot));

        // bat dau ne
        sm.switchTo("Menu");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}