package core;

import javafx.scene.image.Image;

/**
 * Moving object manager.
 *
 */
public abstract class MovableObject extends GameObject{

    private double deltaX = 0.0; // Displacement vector along the x-axis.
    private double deltaY = 0.0; // Displacement vector along the y-axis.

    /**
     * Initialize object with default coordinates (0,0) and displacement vector (0,0).
     *
     * @param width The width of the object.
     * @param height The height of the object.
     * @param texturePath The path to the object's texture.
     */
    public MovableObject(int width, int height, String texturePath) {
        super(width, height, texturePath);
    }

    /**
     * Initialize object with default displacement vector (0,0).
     *
     * @param positionX Upper-left x-coordinate.
     * @param positionY Upper-left y-coordinate.
     * @param width The width of the object.
     * @param height The height of the object.
     * @param texturePath The path to the object's texture.
     */
    public MovableObject(double positionX, double positionY, int width, int height, String texturePath) {
        super(positionX, positionY, width, height, texturePath);
    }

    /**
     * Initialize object with default coordinates (0,0).
     *
     * @param width The width of the object.
     * @param height The height of the object.
     * @param deltaX Displacement vector along the x-axis.
     * @param deltaY Displacement vector along the y-axis.
     * @param texturePath The path to the object's texture.
     */
    public MovableObject(int width, int height, double deltaX, double deltaY, String texturePath) {
        super(width, height, texturePath);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Initialize object.
     *
     * @param positionX Upper-left x-coordinate.
     * @param positionY Upper-left y-coordinate.
     * @param width The width of the object.
     * @param height The height of the object.
     * @param deltaX Displacement vector along the x-axis.
     * @param deltaY Displacement vector along the y-axis.
     * @param texturePath The path to the object's texture.
     */
    public MovableObject(double positionX, double positionY, int width, int height, double deltaX, double deltaY, String texturePath) {
        super(positionX, positionY, width, height, texturePath);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Object movement method.
     *
     * @param deltaTime Time between last frame to current frame.
     */
    public abstract void move(double deltaTime);

    /**
     * Reset object.
     *
     */
    public abstract void reset();

    // Getter and Setter method.

    public double getDeltaX() { return deltaX; }

    public void setDeltaX(double deltaX) { this.deltaX = deltaX; }

    public double getDeltaY() { return deltaY; }

    public void setDeltaY(double deltaY) { this.deltaY = deltaY; }
}