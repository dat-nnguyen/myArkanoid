package core;

import entities.Ball;
import entities.Brick;
import entities.Paddle;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import utils.Constants;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final Pane root;
    private final Scene scene;

    // Initialize object
    private Paddle paddle;
    private Ball ball;
    private final List<Brick> bricks;

    // Initialize core
    private final InputHandler inputHandler;
    private final GameLoop gameLoop;
    // update collision


    public GameManager(Pane root, Scene scene) {
        this.root = root;
        this.scene = scene;
        this.bricks = new ArrayList<>();
        this.inputHandler = new InputHandler();
        this.gameLoop = new GameLoop(this);
        // update collision
    }

    /**
     * Game start
     */
    public void startGame() {
        inputHandler.getKey(scene);
        createPaddle();
        createBall();
        createBricks();
        gameLoop.start();
    }

    /**
     * the method will be called by GameLoop
     * @param deltaTime .
     */
    public void update(float deltaTime) {
        paddle.update(deltaTime, inputHandler);
        ball.update(deltaTime);
        // update collision
    }

    private void createPaddle() {
        float x = (Constants.WIDTH - Constants.PADDLE_WIDTH) / 2.0f;
        float y = Constants.HEIGHT - 60; // set position for paddle
        paddle = new Paddle(x, y, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT);
        root.getChildren().add(paddle.getNode()); // display paddle
    }

    private void createBall() {
        float x = Constants.WIDTH / 2.0f;
        float y = Constants.HEIGHT / 2.0f;
        ball = new Ball(x, y, Constants.BALL_SIZE / 2.0f);
        root.getChildren().add(ball.getNode()); // display ball
    }

    private void createBricks() {
        //bricks.clear();
        int brickWidth = 75;
        int brickHeight = 25;
        int rows = 6;
        int horizontalPadding = 25; // gap of left/right window with brick
        int verticalPadding = 25; // gap of top window with brick
        int spacing = 5;
        int cols = (Constants.WIDTH - (2 * horizontalPadding)) / (brickWidth + spacing);

        /**
         * why use this formula
         * imagine you have to set books into bookshelf
         * (2 * horizontalPadding) is total of left and right padding
         * So WIDTH - (2 * horizontalPadding) is the real space for set books
         * brickWidth is width of a book, spacing is gap between books
         * we have to divide two quantities to return number of column can use for set the book
         */

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float x = horizontalPadding + j * (brickWidth + spacing);
                float y = verticalPadding + i * (brickHeight + spacing); // use to set position for brick
                Color color;
                if (i % 2 == 0) { // if even
                    color = Color.ORANGE;
                } else { // if odd
                    color = Color.GOLD;
                }
                Brick brick = new Brick(x, y, brickWidth, brickHeight, color, 1);
                bricks.add(brick);
                root.getChildren().add(brick.getNode()); // display brick
            }
        }
    }
}