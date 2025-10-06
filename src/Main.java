import core.Constants;
import entities.Paddle;
import javafx.animation.AnimationTimer;
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

        int paddleWidth = 150;
        int paddleHeight = 20;
        int posX = (Constants.screenWidth - paddleWidth) / 2;
        int posY = Constants.screenHeight - paddleHeight - 50;
        Paddle playerPaddle = new Paddle(posX, posY, paddleWidth, paddleHeight);

        Pane root = new Pane();
        Scene scene = new Scene(root, Constants.screenWidth, Constants.screenHeight);

        root.getChildren().add(playerPaddle.render());

        final boolean[] keyPressed = new boolean[256];

        scene.setOnKeyPressed(event -> {
            if (event.getCode().getCode() < 256) {
                keyPressed[event.getCode().getCode()] = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().getCode() < 256) {
                keyPressed[event.getCode().getCode()] = false;
            }
        });

        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate > 0) {
                    double deltaTime = (now - lastUpdate) / 1000000000.0;

                    // Update
                    playerPaddle.update(deltaTime, keyPressed);
                }
                lastUpdate = now;
            }
        }.start();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Akanoi");
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
