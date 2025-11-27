package audio;

import javafx.scene.media.AudioClip;
import java.util.HashMap;

// Dat
public class SoundManager {
    private static final HashMap<String, AudioClip> soundMap = new HashMap<>();
    private double volume = 1.0;

    public SoundManager() {
        loadSound();
    }

    /**
     * Pre-load all important sounds for game.
     *
     */
    private void loadSound() {
        addSound("hit", "/sound/ball-kick.wav");
        addSound("dead", "/sound/Arkanoid SFX (3).wav");
        addSound("power", "/sound/collect (2).wav");
        addSound("play", "/sound/play-game.wav");
        addSound("click", "/sound/click (1).wav");
        addSound("win", "/sound/win.wav");
    }

    /**
     * Add sound for system.
     *
     * @param name name of sound.
     * @param path path to sound file direction.
     */
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

    /**
     * Play sound with name.
     *
     * @param name name of sound you want play.
     */
    public void play(String name){
        AudioClip clip = soundMap.get(name);
        if (clip != null) {
            clip.setVolume(volume);
            clip.play();
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    /**
     * Stop sound with name.
     *
     * @param name name of sound you want stop.
     */
    public void stop(String name){
        AudioClip clip = soundMap.get(name);
        if (clip != null) {
            clip.stop();
        }
    }

    /**
     * Set volume to all sounds.
     *
     * @param volume number of volume you want.
     */
    public void setVolume(double volume) {
        this.volume = Math.max(0.0, Math.min(1.0, volume)); // clamp 0–1
    }

    /**
     * Get the value of current volume.
     *
     * @return value of current volume.
     */
    public double getVolume() {
        return volume;
    }
}