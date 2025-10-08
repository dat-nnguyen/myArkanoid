package entities;

import core.GameObject;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends GameObject {

    private int hitPoints;
    private boolean isDestroyed;
    private final Rectangle brick;

    public Brick(float x, float y, int width, int height, Color color, int hits) {
        super(x, y, width, height);
        this.hitPoints = hits;
        this.isDestroyed = false;

        // Initialize size and shape brick
        this.brick = new Rectangle(x, y, width, height);
        this.brick.setFill(color);
        this.brick.setStroke(Color.WHITE);// set stroke
        this.brick.setArcWidth(10); // set arc
        this.brick.setArcHeight(10);
    }

    @Override
    public void update(float deltaTime) {
        // Brick is a static entity, no need to upadate
    }

    @Override
    public Node getNode() {
        return this.brick;
    }


    public void handleHit() {
        // this method will be call in CollisionManager class
        // will be updated soon
    }

    /**
     * Check if all brick are destroyed
     * @return true if there is no brick
     */
    public boolean isDestroyed() {
        return this.isDestroyed;
    }
}