package UI.Game;

import PowerUpSystem.PowerUpManager;
import PowerUpSystem.PowerUpType;
import RenderManager.RenderMap;
import UI.SceneManager;
import audio.SoundManager;
import core.GameLoop;
import core.InputHandler;
import entities.Ball;
import entities.Brick;
import entities.Paddle;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameScene {

    private final SceneManager sceneManager = SceneManager.getInstance();

    private final Scene scene;
    private final GameLoop gameLoop;

    private final Ball ball;
    private final Paddle paddle;
    private final ArrayList<Brick> brickList;
    private final GraphicsContext gc;
    private int currentLevel = 1;

    private final PowerUpManager powerUpManager;
    private final Map<PowerUpType, Node> powerUpUIMap = new HashMap<>();

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
    @FXML
    private Label scoreLabel;
    @FXML
    private VBox boxOfCredits;
    @FXML
    private ScrollPane credits;


    public GameScene() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/fonts/AGENCYB.TTF"), 1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        RenderMap renderMap = new RenderMap();
        brickList = new ArrayList<>();
        paddle = new Paddle();
        ball = new Ball();
        InputHandler input = new InputHandler();
        ArrayList<Ball> ballList = new ArrayList<>();
        powerUpManager = new PowerUpManager(ball, paddle, ballList);
        gameLoop = new GameLoop(ball, paddle, brickList, gc, input, sceneManager, this, powerUpManager, ballList);

        AnchorPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Game/GameScene.fxml"));
            loader.setController(this);
            root = loader.load();
            root.getChildren().add(1, canvas);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            root = new AnchorPane(canvas);
        }
        scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        input.getKey(scene);
    }

    @FXML
    public void initialize() {
        scoreLabel.textProperty().bind(gameLoop.getScoreProperty().asString("%d"));

        boxOfCredits.prefWidthProperty().bind(credits.widthProperty().subtract(15));

        powerUpManager.getActivePowerUpCredits().addListener(
                (MapChangeListener<PowerUpType, DoubleProperty>) change -> {
                    Platform.runLater(() -> {
                        if (change.wasAdded()) {
                            createPowerUpUI(change.getKey(), change.getValueAdded());
                        } else if (change.wasRemoved()) {
                            removePowerUpUI(change.getKey());
                        }
                    });
                });
    }

    private void createPowerUpUI(PowerUpType type, DoubleProperty timer) {
        Label nameLabel = new Label(type.getName());
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(Font.font("Agency FB", 20));

        Label timerLabel = new Label();
        timerLabel.setTextFill(Color.YELLOW);
        timerLabel.setFont(Font.font("Agency FB", 20));

        timerLabel.textProperty().bind(timer.asString("%.1fs"));

        HBox row = new HBox(20, nameLabel, timerLabel);
        row.setAlignment(Pos.CENTER);

        powerUpUIMap.put(type, row);
        boxOfCredits.getChildren().add(row);
    }

    private void removePowerUpUI(PowerUpType type) {
        Node nodeToRemove = powerUpUIMap.remove(type);
        if (nodeToRemove != null) {
            boxOfCredits.getChildren().remove(nodeToRemove);
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void start() {
        reset();
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }

    public void reset() {
        SceneManager.getInstance().getSoundManager().play("play");
        gameLoop.setScore(0);
        ball.reset();
        paddle.reset();
        brickList.clear();
        gameLoop.getPowerUpManager().reset();
        RenderMap rendermap = new RenderMap();
        rendermap.render(currentLevel, brickList);
        updateLivesUI();
    }

    public void goToNextLevel() {
        gameLoop.stop();
        currentLevel++;
        final int MAX_LEVEL = 15;
        if (currentLevel > MAX_LEVEL) {
            System.out.println("thang me may roi");
            sceneManager.switchTo("Win");
        } else {
            SceneManager.getInstance().getSoundManager().play("play");
            ball.reset();
            paddle.reset();
            brickList.clear();
            gameLoop.getPowerUpManager().reset();
            RenderMap rendermap = new RenderMap();
            rendermap.render(currentLevel, brickList);
            updateLivesUI();
            gameLoop.start();
        }
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Update lives UI based on current ball lives.
     * UPDATED: Now shows hearts when lives are gained back.
     */
    public void updateLivesUI() {
        int lives = ball.getLives();

        // Show/hide hearts based on current lives
        heart1.setVisible(lives >= 1);
        heart2.setVisible(lives >= 2);
        heart3.setVisible(lives >= 3);

        // Debug log
        System.out.println("💖 Lives UI updated: " + lives + " lives");
    }

    @FXML
    public void pauseClicked() {
        SceneManager.getInstance().getSoundManager().play("click");
        System.out.println("Bấm Pause!");
        gameLoop.stop();
        sceneManager.switchTo("Pause");
    }

    @FXML
    public void exitClicked() {
        SceneManager.getInstance().getSoundManager().play("click");
        System.out.println("Bấm Exit!");
        gameLoop.stop();
        sceneManager.switchTo("Menu");
    }
}