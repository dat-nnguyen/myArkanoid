package core;

import entities.Ball;
import entities.Brick;
import entities.BrickType;
import entities.Paddle;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import utils.Constants;

import java.util.ArrayList;

// to manage game, scene
public class GameManager {

    public GameManager(Scene scene) {
        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Ball ball = new Ball();
        System.out.println("Lives: " + ball.getLives());
        Paddle paddle = new Paddle((Constants.SCREEN_WIDTH - 200) / 2.0, Constants.SCREEN_HEIGHT - 50, 200, 20);
        ArrayList<Brick> brickList = new ArrayList<Brick>();
        Brick test1 = new Brick(100, 100, 100, 100, BrickType.HARD);
        Brick test2 = new Brick(200, 200, 100, 100, BrickType.IMPOSSIBLE);
        Brick test3 = new Brick(300, 300, 100, 100, BrickType.MEDIUM);
        Brick test4 = new Brick(400, 400, 100, 100, BrickType.NORMAL);
        brickList.add(test1);
        brickList.add(test2);
        brickList.add(test3);
        brickList.add(test4);
        InputHandler input = new InputHandler();
        input.getKey(scene);
        Pane root = new Pane(canvas);
        scene.setRoot(root);
        GameLoop gameloop = new GameLoop(ball, paddle, brickList, gc, input);
        gameloop.start();
    }
}
