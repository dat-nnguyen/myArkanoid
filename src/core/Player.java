package core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Player manager.
 *
 */
public class Player implements Comparable<Player> {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    private final LocalDateTime localDateTime; // Last time played.
    private int score; // Current score.
    private final String name; // Current name.

    /**
     * Initialize player with date.
     *
     * @param score current score.
     * @param localDateTime current date.
     * @param name current name.
     */
    public Player(int score, LocalDateTime localDateTime, String name) {
        this.score = score;
        this.localDateTime = localDateTime;
        this.name = name;
    }

    /**
     * Initialize player.
     *
     * @param score current score.
     * @param name current name.
     */
    public Player(int score, String name) {
        this.score = score;
        localDateTime = LocalDateTime.now( ZoneId.of("Asia/Ho_Chi_Minh"));
        this.name = name;
    }

    // Getter and Setter methods.
    public int getScore() {
        return score;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Take info of this player.
     *
     * @return info of this player follow format
     */
    @Override
    public String toString() {
        String formateDate = localDateTime.format(DATE_FORMAT);
        return name + " - " + score;
    }

    /**
     * Compare 2 player by score.
     *
     * @param other other player compare to.
     * @return a number respond to smaller / equal / bigger than other.
     */
    @Override
    public int compareTo(Player other) {
        return Integer.compare(score, other.getScore());
    }
}
