package core;

import entities.Ball;
import entities.Brick;
import entities.Paddle;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.Constants;
import utils.LevelLoader;

import java.util.ArrayList;

public class GameManager {

    public GameManager(Scene scene) {
        Stage stage = null;
        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        InputHandler input = new InputHandler();
        input.getKey(scene);

        Ball ball = new Ball();
        Paddle paddle = new Paddle((Constants.SCREEN_WIDTH - 200) / 2.0,
                Constants.SCREEN_HEIGHT - 50,
                200,
                20);

        ArrayList<Brick> brickList = LevelLoader.loadLevel(1);

        ArrayList<GameObject> gameObjects = new ArrayList<>();
        gameObjects.add(ball);
        gameObjects.add(paddle);
        gameObjects.addAll(brickList);

        Pane root = new Pane(canvas);
        scene.setRoot(root);

        
        GameLoop gameloop = new GameLoop(gameObjects, gc, input);
        gameloop.start();
    }
}