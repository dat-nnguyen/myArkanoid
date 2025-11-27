package entities;

import core.GameObject;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utils.Constants;

/**
 * Brick management.
 *
 */
public class Brick extends GameObject {

    private boolean isDestroyed = false; // Check if brick is destroyed.
    private BrickType currentBrickType = BrickType.NORMAL; // Current brick type.
    private int lives = 1; // Hits remaining to destroy.
    private boolean destroyedLog = false; // Log to record which objects have been destroyed.

    private boolean powerUpSpawned = false;

    /**
     * Get info of this brick.
     *
     * @return Info in format: (x,y) TYPE=current_type.
     */
    private String getInfo() {
        String position = "(" + this.getPositionX() + "," + this.getPositionY() + ")";
        String type = "TYPE=" + currentBrickType;
        return position + " " + type;
    }

    /**
     * Initialize object with default coordinates (0,0) and normal type.
     *
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public Brick(int width, int height) {
        super(width, height, BrickType.NORMAL.getTexturePath());
        System.out.println("\uD83E\uDDF1 Brick Init Success: " + getInfo() );
    }

    /**
     * Initialize object with normal type.
     *
     * @param positionX Upper-left x-coordinate.
     * @param positionY Upper-left y-coordinate.
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public Brick(double positionX, double positionY, int width, int height) {
        super(positionX, positionY, width, height, BrickType.NORMAL.getTexturePath());
        System.out.println("\uD83E\uDDF1 Brick Init Success: " + getInfo() );
    }

    /**
     * Initialize object with default coordinates (0,0).
     *
     * @param width The width of the object.
     * @param height The height of the object.
     * @param currentBrickType Type of brick.
     */
    public Brick(int width, int height, BrickType currentBrickType) {
        super(width, height, currentBrickType.getTexturePath());
        this.currentBrickType = currentBrickType;
        lives = currentBrickType.getHits();
        System.out.println("\uD83E\uDDF1 Brick Init Success: " + getInfo() );
    }

    /**
     * Initialize object.
     *
     * @param positionX Upper-left x-coordinate.
     * @param positionY Upper-left y-coordinate.
     * @param width The width of the object.
     * @param height The height of the object.
     * @param currentBrickType Type of brick.
     */
    public Brick(double positionX, double positionY, int width, int height, BrickType currentBrickType) {
        super(positionX, positionY, width, height, currentBrickType.getTexturePath());
        this.currentBrickType = currentBrickType;
        lives = currentBrickType.getHits();
        System.out.println("\uD83E\uDDF1 Brick Init Success: " + getInfo() );
    }

    @Override
    public void update(double deltaTime) {
        if (currentBrickType.isUnbreakable()) {
            return;
        }
        updateTexture();
        // Check if brick is not destroyed and not logged.
        if (lives <= 0 && !destroyedLog) {
            destroyedLog = true;
            isDestroyed = true;
            String position = "(" + this.getPositionX() + "," + this.getPositionY() + ")";
            System.out.println("🧱 Brick Destroyed: " + getInfo());
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isDestroyed) {
            return;
        }
        gc.drawImage(this.getTexture(), this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());
    }

    //Getter and Setter method.

    public boolean getIsDestroyed() {
        return isDestroyed;
    }

    public void setIsDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public BrickType getCurrentBrickType() {
        return currentBrickType;
    }

    public void setCurrentBrickType(BrickType currentBrickType) {
        this.currentBrickType = currentBrickType; }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Check if the brick needs to spawn power-ups.
     * Returns true ONLY ONCE.
     *
     */
    public boolean shouldSpawnPowerUp() {
        if (isDestroyed && !powerUpSpawned && !currentBrickType.isUnbreakable()) {
            powerUpSpawned = true; // Đánh dấu đã spawn
            return true;
        }
        return false;
    }

    /**
     * Update brick's texture by hits remaining.
     *
     */
    public void updateTexture() {
        BrickType newBrickType = BrickType.getBrickType(lives);
        if (newBrickType != null && newBrickType != currentBrickType) {
            // currentBrickType = newBrickType;
            super.loadTexture(newBrickType.getTexturePath());
        }
    }

}