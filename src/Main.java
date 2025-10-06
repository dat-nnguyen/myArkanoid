import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.DARKBLUE);

        stage.setScene(scene);
        stage.setTitle("ARKANOID");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
