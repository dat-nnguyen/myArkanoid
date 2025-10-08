package core;

import javafx.scene.canvas.GraphicsContext;


/**
 * This is the base of all entities in the game which contains size and position
 * x, y top-left corner coordinates of the rectangle including entities
 */
public abstract class GameObject {
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    /**
     * @param x X top-left corner coordinate
     * @param y Y top-left corner coordinate
     * @param width size of entity
     * @param height size of entity
     */

    public GameObject(double x, double y,  double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
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

    /**
     * GETTER method
     */
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }

    /**
     * SETTER method
     */
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the center along the X and Y axis (used for collision detection when needed).
     */

    public double getCenterX() {
        return x + (width / 2);
    }
    public double getCenterY() {
        return y + (height / 2);
    }
    /**
     * Why use getCenter?
     * In almost framework, (x, y) coordinates of GameObject is conventionally top-left corner
     * of retangle around objects so it is easy for draw objects
     * When we calculate for collision or distance of two objects, we have to use real center coordinate
     */
}
