package entities;

import core.MovableObject;
import javafx.scene.shape.Rectangle;

public class Ball extends MovableObject {
    private PowerUpType powerUpType;

    public Ball(int width, int height) {
        super(width, height);
    }

    @Override
    public void update(double deltaTime, boolean[] keyPressed) {

    }

    @Override
    public Rectangle render() {
        return null;
    }
}
