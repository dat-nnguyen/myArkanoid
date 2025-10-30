package entities;

public enum BrickType {

    NORMAL(1, "/images/gameImages/brickImages/green-bricks.png"),
    MEDIUM(3, "/images/gameImages/brickImages/orange-bricks.png"),
    HARD(5, "/images/gameImages/brickImages/red-bricks.png"),
    IMPOSSIBLE(-1, "/images/gameImages/brickImages/white-bricks.png");

    private final int hits;
    private final String texturePath;

    BrickType(int hits, String texturePath) {
        this.hits = hits;
        this.texturePath = texturePath;
    }

    public int getHits() { return hits; }

    public String getTexturePath() { return texturePath; }

    public boolean isUnbreakable() { return hits == -1; }

}
