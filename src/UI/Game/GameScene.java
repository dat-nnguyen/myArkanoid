package UI.Game;

import PowerUpSystem.PowerUpManager;
import PowerUpSystem.PowerUpType;
import RenderManager.RenderMap;
import UI.Shop.CreditsManager;
import UI.SceneManager;
import UI.Shop.SkinData;
import UI.Shop.SkinManager;
import core.GameLoop;
import core.HighScore;
import core.InputHandler;
import core.Player;
import entities.Ball;
import entities.Brick;
import entities.Paddle;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utils.Constants;

import java.net.URL;
import java.util.*;

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
    private final HashMap<PowerUpType, Node> powerUpUIMap = new HashMap<>();
    private final HighScore highScore;
    private final String currentPlayerName = sceneManager.getCurrentPlayerName();

    @FXML
    private ImageView heart1;
    @FXML
    private ImageView heart2;
    @FXML
    private ImageView heart3;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private VBox boxOfPowerUp;
    @FXML
    private VBox boxOfHighScores;
    @FXML
    private Label creditsLabel;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private ImageView titleImageView;
    @FXML
    private ImageView frameImageView;


    /**
     * Initialize game scene.
     *
     */
    public GameScene() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/fonts/AGENCYB.TTF"), 1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        brickList = new ArrayList<>();
        paddle = new Paddle();
        ball = new Ball();
        InputHandler input = new InputHandler();
        ArrayList<Ball> ballList = new ArrayList<>();
        powerUpManager = new PowerUpManager(ball, paddle, ballList);
        gameLoop = new GameLoop(ball, paddle, brickList, gc, input, sceneManager, this, powerUpManager, ballList);
        highScore = new HighScore();
        AnchorPane root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Game/GameScene.fxml"));
            loader.setController(this);
            root = loader.load();
            root.getChildren().add(1, canvas);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            root = new AnchorPane(canvas);
        }
        scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        input.getKey(scene);
    }

    /**
     * Initialize fxml.
     *
     */
    @FXML
    public void initialize() {
        scoreLabel.textProperty().bind(gameLoop.getScoreProperty().asString("%d"));
        creditsLabel.textProperty().bind(CreditsManager.getInstance().getCreditsProperty().asString("%d"));
        ActivePowerUpListener apc = new ActivePowerUpListener(this);
        powerUpManager.getActivePowerUpCredits().addListener(apc);
        createHighScoreUI();
    }

    /**
     * Create high scores board.
     *
     */
    private void createHighScoreUI() {
            boxOfHighScores.getChildren().clear();
            List<Player> top = highScore.getHighScoresList();
            if (top.isEmpty()) {
                Label first = new Label("Lan dau cua em...");
                boxOfHighScores.getChildren().add(first);
                return;
            }
            for (int i = top.size() - 1; i >= 0; --i) {
                Player player = top.get(i);
                HBox row = createScoreRow(top.size() - i, player);
                boxOfHighScores.getChildren().add(row);
            }
    }

    /**
     * Create row for each player with high score.
     *
     * @param rank player's rank
     * @param player player's info
     * @return a row contain player's rank and name.
     */
    private HBox createScoreRow(int rank, Player player) {
        Label rankLabel;
        Label info = new Label(player.toString());
        String colorStyle;
        if (rank == 1) {
            rankLabel = new Label("\uD83E\uDD47");
            colorStyle = "-fx-text-fill: gold;";
        } else if (rank == 2) {
            rankLabel = new Label("\uD83E\uDD48");
            colorStyle = "-fx-text-fill: #E5E4E2;";
        } else if (rank == 3) {
            rankLabel = new Label("\uD83E\uDD49");
            colorStyle = "-fx-text-fill: #CD7F32;";
        } else {
            rankLabel = new Label("\uD83E\uDD1D");
            colorStyle = "-fx-text-fill: silver;";
        }
        info.setStyle(colorStyle);
        rankLabel.setStyle(colorStyle);
        HBox row = new HBox(10, rankLabel, info);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    /**
     * Create power-up info board.
     *
     * @param type power-up type.
     * @param timer time remaining.
     */
    protected void createPowerUpUI(PowerUpType type, DoubleProperty timer) {
        Label nameLabel = new Label(type.getName());

        Label timerLabel = new Label();
        timerLabel.setTextFill(Color.YELLOW);

        timerLabel.textProperty().bind(timer.asString("%.1fs"));

        HBox row = new HBox(20, nameLabel, timerLabel);
        row.setAlignment(Pos.CENTER);

        powerUpUIMap.put(type, row);
        boxOfPowerUp.getChildren().add(row);
    }

    /**
     * Remove power-up info from the board.
     *
     * @param type power-up type need to remove.
     */
    protected void removePowerUpUI(PowerUpType type) {
        Node nodeToRemove = powerUpUIMap.remove(type);
        if (nodeToRemove != null) {
            boxOfPowerUp.getChildren().remove(nodeToRemove);
        }
    }

    /**
     * Something you need.
     *
     * @return this scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Call map renderer for render map.
     *
     */
    public void renderMap() {
        RenderMap rendermap = new RenderMap();
        rendermap.render(currentLevel, brickList);
    }

    /**
     * Start game.
     *
     */
    public void start() {
        SceneManager.getInstance().getSoundManager().play("play");
        updateSkin();
        updateUI();
        gameLoop.start();
    }

    /**
     * Stop game.
     *
     */
    public void stop() {
        gameLoop.stop();
    }

    /**
     * Reset game.
     *
     */
    public void reset() {
        currentLevel = 1;
        gameLoop.setScore(0);
        gameLoop.reset();
        brickList.clear();
        ball.reset();
        paddle.reset();
        renderMap();
        updateLivesUI();
    }

    /**
     * Next step...
     *
     */
    public void goToNextLevel() {
        gameLoop.stop();
        ++ currentLevel;
        if (currentLevel > Constants.MAX_LEVEL) {
            System.out.println("Win roi em oi");
            sceneManager.getSoundManager().play("win");
            sceneManager.switchTo("Win");
        } else {
            gameLoop.reset();
            brickList.clear();
            ball.reset();
            paddle.reset();
            updateLivesUI();
            updateUI();
            renderMap();
            start();
        }
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

    }

    @FXML
    private void pauseClicked() {
          SceneManager.getInstance().getSoundManager().play("click");
          gameLoop.stop();
          sceneManager.switchTo("Pause");
          System.out.println("Bấm Pause!");
    }

    @FXML
    private void exitClicked() {
        SceneManager.getInstance().getSoundManager().play("click");
        System.out.println("Bấm Exit!");
        gameLoop.stop();
        updateHighScore();
        sceneManager.switchTo("Menu");
    }

    /**
     * Update high scores board.
     *
     */
    public void updateHighScore() {
        highScore.addScore(gameLoop.getScore(), currentPlayerName);
        createHighScoreUI();
    }

    /**
     * Update UI of game scene.
     *
     */
    private void updateUI() {
        String backgroundPath = String.format("/levels/lv%d/lv%dBackground.png", currentLevel, currentLevel);
        String framePath = String.format("/levels/lv%d/KHUNG%d.png", currentLevel, currentLevel);
        String titlePath = String.format("/levels/lv%d/ARKANOID%d.png", currentLevel, currentLevel);

        URL backgroundUrl = getClass().getResource(backgroundPath);
        URL frameUrl = getClass().getResource(framePath);
        URL titleUrl = getClass().getResource(titlePath);

        if (backgroundUrl != null && frameUrl != null && titleUrl != null) {
            Image backgroundImage = new Image(backgroundUrl.toString());
            Image frameImage = new Image(frameUrl.toString());
            Image titleImage = new Image(titleUrl.toString());
            backgroundImageView.setImage(backgroundImage);
            frameImageView.setImage(frameImage);
            titleImageView.setImage(titleImage);
        } else {
            System.err.println("Cannot find background: ");
            if (backgroundUrl == null) {
                System.err.println(" ! " + backgroundPath);
            }
            if (frameUrl == null) {
                System.err.println(" ! " + framePath);
            }
            if (titleUrl == null) {
                System.err.println(" ! " + titlePath);
            }
        }
        if (currentLevel < 10) {
            levelLabel.setText("0" + currentLevel);
        } else {
            levelLabel.setText("" + currentLevel);
        }
    }

    /**
     * Update skin of paddle, ball.
     *
     */
    private void updateSkin() {
        SkinData selectedPaddle = SkinManager.getInstance().getSelectedPaddle();
        SkinData selectedBall = SkinManager.getInstance().getSelectedBall();
        Image texturePaddle = new Image(Objects.requireNonNull(getClass().getResourceAsStream(selectedPaddle.getImagePath())));
        Image textureBall = new Image(Objects.requireNonNull(getClass().getResourceAsStream(selectedBall.getImagePath())));
        paddle.setTexture(texturePaddle);
        ball.setTexture(textureBall);
    }

}