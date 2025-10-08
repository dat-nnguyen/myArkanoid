package core;

import javafx.scene.Node;
import utils.Vector2D;

/**
 * This is abstract class defining all entities in the game.
 * include attributes and their behavior
 *
 */
public abstract class GameObject {

    /**
     * @param position top-left corner of rectangle surrounding the object
     * protected Ball, Paddle, etcs. can access to
     */
    protected Vector2D position;

    /**
     * Size of entity
     * Use for render and check collision
     */
    protected int width, height;

    /**
     * Constructor of GameObject
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param width
     * @param height
     */
    public GameObject(float x, float y, int width, int height) {
        this.position = new Vector2D(x, y);
        this.width = width;
        this.height = height;
    }

    /**
     * Update status of entity after each frame
     * @param deltaTime time elapsed since the previous frame.
     */
    public abstract void update(float deltaTime);

    /**
     *
     * @return Node (animation entity of JavaFx) display on screen
     * For instance, Circle stands for Ball, Rectangle stands for Paddle
     */
    public abstract Node getNode();

    /**
     * Checks collision between this and another object by using
     * AABB(Axis-Aligned Bounding Box) method
     * Will be use in CollisionManager class
     * @param other The other GameObject to check collision
     * @return {@code true} if the bounding boxes intersect, {@code false} if not
     */
    public boolean intersects(GameObject other) {
        return position.x < other.position.x + other.width &&
                position.x + width > other.position.x &&
                position.y < other.position.y + other.height &&
                position.y + height > other.position.y;
    }

    //GETTER - READ ONLY

    public float getX() { return position.x; }
    public float getY() { return position.y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}