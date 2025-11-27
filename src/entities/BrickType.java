package entities;

/**
 * Brick types manager.
 *
 */
public enum BrickType {

    NORMAL(1, "/images/gameImages/brickImages/green-bricks.png"),
    HALF_MEDIUM(2, "/images/gameImages/brickImages/blue-bricks.png"),
    MEDIUM(3, "/images/gameImages/brickImages/orange-bricks.png"),
    HALF_HARD(4, "/images/gameImages/brickImages/pink-bricks.png"),
    HARD(5, "/images/gameImages/brickImages/red-bricks.png"),
    IMPOSSIBLE(-1, "/images/gameImages/brickImages/lava1.png");

    private final int hits;
    private final String texturePath;

    BrickType(int hits, String texturePath) {
        this.hits = hits;
        this.texturePath = texturePath;
    }

    public int getHits() {
        return hits;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public boolean isUnbreakable() {
        return hits == -1;
    }

    /**
     * Get type of brick with remaining hits for update texture.
     *
     * @param hits remaining hits.
     * @return type of brick.
     */
    public static BrickType getBrickType(int hits) {
        if (hits == 0) {
            return null;
        } else if (hits == 1) {
            return NORMAL;
        } else if (hits == 2) {
            return HALF_MEDIUM;
        } else if (hits == 3) {
            return MEDIUM;
        } else if (hits == 4) {
            return HALF_HARD;
        } else {
            return HARD;
        }
    }

}
