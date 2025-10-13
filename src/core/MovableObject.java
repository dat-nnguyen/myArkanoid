package core;

/**
 * Moving object management.
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
     */
    public MovableObject(int width, int height) {
        super(width, height);
    }

    /**
     * Initialize object with default displacement vector (0,0).
     *
     * @param positionX Upper-left x-coordinate.
     * @param positionY Upper-left y-coordinate.
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public MovableObject(double positionX, double positionY, int width, int height) {
        super(positionX, positionY, width, height);
    }

    /**
     * Initialize object with default coordinates (0,0).
     *
     * @param width The width of the object.
     * @param height The height of the object.
     * @param deltaX Displacement vector along the x-axis.
     * @param deltaY Displacement vector along the y-axis.
     */
    public MovableObject(int width, int height, double deltaX, double deltaY) {
        super(width, height);
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
     */
    public MovableObject(double positionX, double positionY, int width, int height, double deltaX, double deltaY) {
        super(positionX, positionY, width, height);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Object movement method.
     *
     * @param deltaTime Time between last frame to current frame.
     */
    public abstract void move(double deltaTime);

    // Getter and Setter method.

    public double getDeltaX() { return deltaX; }

    public void setDeltaX(double deltaX) { this.deltaX = deltaX; }

    public double getDeltaY() { return deltaY; }

    public void setDeltaY(double deltaY) { this.deltaY = deltaY; }
}