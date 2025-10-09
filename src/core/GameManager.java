package core;

import entities.Ball;
import entities.Paddle;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

// to manage game, scene
public class GameManager {

    public GameManager(Scene scene) {
        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Ball ball = new Ball();
        System.out.println("Lives: " + ball.getLives());
        Paddle paddle = new Paddle((Constants.SCREEN_WIDTH - 200) / 2.0, Constants.SCREEN_HEIGHT - 50, 200, 20);
        InputHandler input = new InputHandler();
        input.getKey(scene);
        Pane root = new Pane(canvas);
        scene.setRoot(root);
        GameLoop gameloop = new GameLoop(ball, paddle, gc, input);
        gameloop.start();
    }
}
