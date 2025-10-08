import core.GameManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.Constants;

/**
 * Entry point for the Arkanoid game.
 *
 * <p>This class only creates the JavaFX window and delegates the game logic
 * to {@link GameManager}.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);

        // Create and initialize game manager
        GameManager gameManager = new GameManager(root, scene);
        gameManager.startGame();

        primaryStage.setResizable(false);
        primaryStage.setTitle("ARKANOID");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
