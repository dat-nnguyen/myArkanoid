package entities;

import core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick extends GameObject {

    private boolean isDestroyed = false;
    private BrickType currentBrickType = BrickType.NONE;
    private int lives = 1;
    private boolean destroyedLog = false;

    private String getInfo() {
        String position = "(" + this.getPositionX() + "," + this.getPositionY() + ")";
        String type = "TYPE=" + currentBrickType;
        return position + " " + type;
    }

    public Brick(int width, int height) {
        super(width, height);
        System.out.println("\uD83E\uDDF1 Brick Init Success: " + getInfo() );
    }

    public Brick(double positionX, double positionY, int width, int height) {
        super(positionX, positionY, width, height);
        System.out.println("\uD83E\uDDF1 Brick Init Success: " + getInfo() );
    }

    public Brick(int width, int height, BrickType currentBrickType) {
        super(width, height);
        this.currentBrickType = currentBrickType;
        switch (currentBrickType) {
            case BrickType.MEDIUM:
                    lives = 3;
                    break;
            case BrickType.HARD:
                    lives = 5;
                    break;
        }
        System.out.println("\uD83E\uDDF1 Brick Init Success: " + getInfo() );
    }

    public Brick(double positionX, double positionY, int width, int height, BrickType currentBrickType) {
        super(positionX, positionY, width, height);
        this.currentBrickType = currentBrickType;
        switch (currentBrickType) {
            case BrickType.MEDIUM:
                lives = 3;
                break;
            case BrickType.HARD:
                lives = 5;
                break;
        }
        System.out.println("\uD83E\uDDF1 Brick Init Success: " + getInfo() );
    }

    @Override
    public void update(double deltaTime) {
        if (lives == 0 && !destroyedLog) {
            destroyedLog = true;
            isDestroyed = true;
            String position = "(" + this.getPositionX() + "," + this.getPositionY() + ")";
            System.out.println("\uD83E\uDDF1 Brick Destroyed: " + getInfo());
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        switch (currentBrickType) {
            case BrickType.NONE:
                gc.setFill(Color.FORESTGREEN);
                break;
            case BrickType.MEDIUM:
                gc.setFill(Color.GOLD);
                break;
            case BrickType.HARD:
                gc.setFill(Color.INDIANRED);
                break;
            case BrickType.IMPOSSIBLE:
                gc.setFill(Color.DARKGRAY);
                break;
        }
        gc.fillRect(this.getPositionX(), this.getPositionY(), this.getWidth(), this.getHeight());
    }

    public boolean getIsDestroyed() { return isDestroyed; }

    public void setIsDestroyed(boolean isDestroyed) { this.isDestroyed = isDestroyed; }

    public BrickType getCurrentBrickType() { return currentBrickType; }

    public void setCurrentBrickType(BrickType currentBrickType) { this.currentBrickType = currentBrickType; }

    public int getLives() { return lives; }

    public void setLives(int lives) { this.lives = lives; }

}
