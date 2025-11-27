package UI;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;

import audio.SoundManager;
import core.Player;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import UI.Game.GameScene;

/**
 * Scene manager : Singleton
 *
 */
public class SceneManager {
    //object scenemanager duy nhat
    private static SceneManager instance;

    // Main stage
    private Stage primaryStage;

    // Save (Name_of_scene, Scene)
    private final Map<String, Scene> scenes = new HashMap<>();

    private GameScene gameSceneInstance;
    private String currentSceneName;

    private final SoundManager soundManager = new SoundManager();
    private String currentPlayerName;
    private boolean isPlayed = false;

    private SceneManager() {
        askForName();
    }

    // Getter and Setter methods.
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    public  void addScene(String name, Scene scene){
        scenes.put(name, scene);
    }

    public void addGameScene(String name, GameScene gameScene) {
        this.gameSceneInstance = gameScene;
        this.scenes.put(name, gameScene.getScene());
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public GameScene getGameSceneInstance() {
        return gameSceneInstance;
    }

    /**
     * Switch to another scene.
     *
     * @param name name of scene you want to switch.
     */
    public void switchTo(String name) {
        Scene scene = scenes.get(name);

        if (scene == null) {
            System.out.println("Chưa có màn này: " + name);
            return;
        }

        String previousSceneName = this.currentSceneName;

        if ("Game".equals(previousSceneName) && gameSceneInstance != null) {
            System.out.println("Đang rời Game !");
            gameSceneInstance.stop();
        }

        if (primaryStage != null) {
            primaryStage.setScene(scene);
            this.currentSceneName = name;
        }

        if ("Game".equals(name) && gameSceneInstance != null) {
            System.out.println("Đang vào Game !");

            if ("Pause".equals(previousSceneName)) {
                gameSceneInstance.start();
            } else {
                if (isPlayed) {
                    gameSceneInstance.reset();
                } else {
                    gameSceneInstance.renderMap();
                    isPlayed = true;
                }
                gameSceneInstance.start();
            }
        }
    }

    /**
     * Check if player has a valid name ?
     *
     * @param name current name.
     * @return yes / or ? Is it valid ?
     */
    private boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        String check = name.trim();
        if (check.length() > 5 || check.length() < 2) {
            return false;
        }
        for (int i = 0; i < check.length(); ++i) {
            if (!Character.isLetterOrDigit(check.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ask for player name to play.
     *
     */
    private void askForName() {
        while (currentPlayerName == null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Welcome to Arkanoid \uD83C\uDFAE");
            dialog.setHeaderText("Are you ready ?");
            dialog.setContentText("Enter your name:");
            dialog.setGraphic(null);
            Optional<String> input = dialog.showAndWait();
            if (input.isPresent()) {
                String name = input.get();
                if (isValidName(name)) {
                    currentPlayerName = name.trim();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid name \uD83D\uDEAB");
                    alert.setHeaderText("Enter your name again !");
                    alert.setContentText("Name must be 2-5 characters long and contain only letters and numbers.");
                    alert.setGraphic(null);
                    alert.showAndWait();
                }
            } else {
                Platform.exit();
                break;
            }
        }
    }
}