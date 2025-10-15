import utils.Constants;
import core.GameManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Akarnoid");
        Pane pane = new Pane();
        Scene scene = new Scene(pane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        new GameManager(scene);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}