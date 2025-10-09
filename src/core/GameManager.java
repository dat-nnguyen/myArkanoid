package core;

import entities.Ball;
import entities.Paddle;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

// to manage game, scene
public class GameManager {

    private Canvas canvas;
    private Ball ball;
    private Paddle paddle;
    private GraphicsContext gc;
    private GameLoop gameloop;
    private InputHandler input;

    public GameManager(Scene scene) {
        canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        ball = new Ball(Constants.START_POSITION_X, Constants.START_POSITION_Y);
        System.out.println("Lives: " + ball.getLives());
        paddle = new Paddle((Constants.SCREEN_WIDTH - 200) / 2.0, Constants.SCREEN_HEIGHT - 50, 200, 20);
        input = new InputHandler();
        input.getKey(scene);
        Pane root = new Pane(canvas);
        scene.setRoot(root);
        gameloop = new GameLoop(ball, paddle, gc, input);
        gameloop.start();
    }
}
