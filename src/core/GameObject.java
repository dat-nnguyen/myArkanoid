package core;

import javafx.scene.canvas.GraphicsContext;

/**
 * This is the base of all entities in the game which contains size and position
 * positionX, positionY top-left corner coordinates of the rectangle including entities
 */
public abstract class GameObject {

    private double positionX;
    private double positionY;
    private int width;
    private int height;

    /**
     * @param width size of entity
     * @param height size of entity
     */
    public GameObject(int width, int height) {
        this.positionX = 0.0;
        this.positionY = 0.0;
        this.width = width;
        this.height = height;
    }

    /**
     * @param positionX X top-left corner coordinate
     * @param positionY Y top-left corner coordinate
     * @param width size of entity
     * @param height size of entity
     */
    public GameObject(double positionX, double positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    /**
     * Update logic for object.
     * Call each frame with deltaTime(second)
     * @param deltaTime time between last frame to current frame
     */
    public abstract void update(double deltaTime);

    /**
     * Draw entities with GraphicsContext
     * @param gc GraphicsContext of Canvas
     */
    public abstract void render(GraphicsContext gc);
}
