package UI;

import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;

import audio.SoundManager;
import javafx.scene.Scene;
import javafx.stage.Stage;
import UI.Game.GameScene;

public class SceneManager {
    //object scenemanager duy nhat
    private static SceneManager instance;

    //main stage
    private Stage primaryStage;

    // Luu cac scenes gom ten scene va scene
    private final Map<String, Scene> scenes = new HashMap<>();

    private GameScene gameSceneInstance;
    private String currentSceneName;

    private final SoundManager soundManager = new SoundManager();

    private SceneManager() {} // k cho phep khoi tao obj ben ngoai

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

    public Scene getScene(String Name){
        return scenes.get(Name);
    }

    public void switchTo(String name){
        Scene scene = scenes.get(name);

        if(scene == null){
            System.out.println("Chưa có màn này: " + name);
            return;
        }

        if ("Game".equals(currentSceneName) && gameSceneInstance != null) {
            System.out.println("Đang rời Game !");
            gameSceneInstance.stop();
            gameSceneInstance.setCurrentLevel(1);
        }

        if (primaryStage != null) {
            primaryStage.setScene(scene);
            this.currentSceneName = name;
        }

        if ("Game".equals(name) && gameSceneInstance != null) {
            System.out.println("Đang vào Game !");
            gameSceneInstance.start();
        }

    }

    public SoundManager getSoundManager() {
        return soundManager;
    }
}