package audio;

import javafx.scene.media.AudioClip;
import java.util.HashMap;

// Dat
public class SoundManager {
    private static final HashMap<String, AudioClip> soundMap = new HashMap<>();
    private double volume = 1.0;

    public SoundManager() {
        loadSounds();
    }

    private void loadSounds() {
        addSound("hit", "/sound/ball-kick.wav");
        addSound("dead", "/sound/Arkanoid SFX (3).wav");
        addSound("power", "/sound/collect (2).wav");
        addSound("play", "/sound/play-game.wav");
        addSound("click", "/sound/click (1).wav");
    }

    private void addSound(String name, String path) {
        try {
            var resource = getClass().getResource(path);
            if (resource == null) {
                System.err.println("Cannot find sound: " + path);
                return;
            }
            AudioClip sound = new AudioClip(resource.toExternalForm());
            soundMap.put(name, sound);
            System.out.println("Loaded sound: " + name);
        } catch (Exception ex) {
            System.err.println("Error sound " + path + ": " + ex.getMessage());
        }
    }


    public void play(String name){
        AudioClip clip = soundMap.get(name);
        if (clip != null) {
            clip.setVolume(volume);
            clip.play();
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    public void stop(String name){
        AudioClip clip = soundMap.get(name);
        if (clip != null) {
            clip.stop();
        }
    }

    public void setVolume(double volume) {
        this.volume = Math.max(0.0, Math.min(1.0, volume)); // clamp 0–1
    }

    public double getVolume() {
        return volume;
    }
}