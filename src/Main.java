import core.GameManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.Constants;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane(); // Pane is a layout that allow us to set up position for objects
        Scene scene = new Scene(root, Constants.WIDTH, Constants.HEIGHT);
        scene.setFill(Color.CHOCOLATE);

        GameManager gameManager = new GameManager(root, scene);
        gameManager.startGame();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Arkanoid Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}