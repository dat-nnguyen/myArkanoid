package core;

import javafx.scene.canvas.GraphicsContext;

/**
 * Game Object Management (e.g., buttons, ball, paddle, bricks, etc.).
 *
 */
public abstract class GameObject {

    private double positionX; // Upper-left x-coordinate.
    private double positionY; // Upper-left y-coordinate.
    private int width; // The width of the object.
    private int height; // The height of the object.

    /**
     * Initialize object with default coordinates (0,0).
     *
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public GameObject(int width, int height) {
        this.positionX = 0.0;
        this.positionY = 0.0;
        this.width = width;
        this.height = height;
    }

    /**
     * Initialize object.
     *
     * @param positionX Upper-left x-coordinate.
     * @param positionY Upper-left y-coordinate.
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public GameObject(double positionX, double positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    /**
     * Update logic for object.
     * Call each frame with deltaTime (second).
     *
     * @param deltaTime Time between last frame to current frame.
     */
    public abstract void update(double deltaTime);

    /**
     * Draw entities with GraphicsContext.
     *
     * @param gc GraphicsContext of Canvas.
     */
    public abstract void render(GraphicsContext gc);

    // Getter and Setter method.

    public double getPositionX() { return positionX; }

    public void setPositionX(double positionX) { this.positionX = positionX; }

    public double getPositionY() { return positionY; }

    public void setPositionY(double positionY) { this.positionY = positionY; }

    public int getWidth() { return width; }

    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }

    public void setHeight(int height) { this.height = height; }
}
