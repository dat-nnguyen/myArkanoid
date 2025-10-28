package UI.Game;

import RenderManager.RenderMap;
import UI.SceneManager;
import core.GameLoop;
import core.InputHandler;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

public class GameScene {

    private final SceneManager sceneManager;

    private Scene scene;
    private final GameLoop gameLoop;

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private GraphicsContext gc;
    private int currentLevel = 1;

    @FXML
    private ImageView pauseButton;
    @FXML
    private ImageView exitButton;
    @FXML
    private ImageView heart1;
    @FXML
    private ImageView heart2;
    @FXML
    private ImageView heart3;

    private ArrayList<ImageView> heartIcons;

    public GameScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        RenderMap renderMap = new RenderMap();
        brickList = new ArrayList<>();
        paddle = new Paddle();
        ball = new Ball();
        InputHandler input = new InputHandler();
        gameLoop = new GameLoop(ball, paddle, brickList, gc, input, sceneManager, this);

        AnchorPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Game/GameScene.fxml"));
            loader.setController(this);
            root = loader.load();
            root.getChildren().add(1, canvas);

            pauseButton.setOnMouseClicked(event -> {
                System.out.println("Bấm Pause!");
                gameLoop.stop();
                sceneManager.switchTo("Pause");
            });

            exitButton.setOnMouseClicked(event -> {
                System.out.println("Bấm Exit!");
                gameLoop.stop();
                sceneManager.switchTo("Menu");
            });

        } catch (IOException e) {
            System.err.println(e.getMessage());
            root = new AnchorPane(canvas);
        }
        scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        input.getKey(scene);
    }

    public Scene getScene() {
        return scene;
    }

    public void start() {
        reset();
        System.out.println("\uD83C\uDFAF Score: " + gameLoop.getScore());
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }

    public void reset() {
        gameLoop.setScore(0);
        ball.reset();
        paddle.reset();
        brickList.clear();
        gameLoop.getPowerUpManager().reset(); // Reset power-ups
        RenderMap rendermap = new RenderMap();
        rendermap.render(currentLevel, brickList);
        updateLivesUI(3);
        heart1.setVisible(true);
        heart2.setVisible(true);
        heart3.setVisible(true);
    }

    public void goToNextLevel() {
        gameLoop.stop();
        currentLevel ++;
        final int MAX_LEVEL = 15;
        if (currentLevel > MAX_LEVEL) {
            System.out.println("thang me may roi");
            sceneManager.switchTo("Win");
        } else {
            start();
        }
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void updateLivesUI(int currentLives) {
        if (currentLives == 2) heart3.setVisible(false);
        if (currentLives == 1) heart2.setVisible(false);
        if (currentLives == 0) heart1.setVisible(false);
    }

}
