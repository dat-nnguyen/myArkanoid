package core;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Manage high scores list of game.
 *
 */
public class HighScore {

    private static final String HIGHSCORE_PATH = "highscores.txt";

    private final List<Player> highScoresList;

    /**
     * Initialize high scores list managers.
     *
     */
    public HighScore() {
        this.highScoresList = loadHighScoresList();
    }

    /**
     * Add player to the list.
     *
     * @param score score of current player.
     * @param currentPlayerName name of current player.
     */
    public void addScore(int score, String currentPlayerName) {
        if (score == 0) {
            return;
        }
        boolean isExist = false;
        for (Player player : highScoresList) {
            if (player.getName().equals(currentPlayerName)) {
                player.setScore(Math.max(player.getScore(), score));
                isExist = true;
            }
        }
        if (!isExist) {
            highScoresList.add(new Player(score, currentPlayerName));
        }
        Collections.sort(highScoresList);
        while (highScoresList.size() > 8) {
            highScoresList.removeLast();
        }
        saveHighScoresList();
    }

    /**
     * Save high scores for the next time.
     *
     */
    private void saveHighScoresList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGHSCORE_PATH))) {
            for (Player player : highScoresList) {
                String line = player.getName() + "~" + player.getScore() + "~" + player.getLocalDateTime();
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Load high scores list in the last time.
     *
     * @return a list of top 8 players with the most score.
     */
    private List<Player> loadHighScoresList() {
        List<Player> load = new ArrayList<Player>();
        if (!new File(HIGHSCORE_PATH).exists()) {
            return load;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGHSCORE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("~");
                int score = Integer.parseInt(tokens[1]);
                String name = tokens[0];
                LocalDateTime time = LocalDateTime.parse(tokens[2]);
                load.add(new Player(score, time, name));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return load;
    }

    // Getter method.
    public List<Player> getHighScoresList() {
        return highScoresList;
    }
}
