package core;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Handles keyboard and mouse input for the game.
 *
 * <p>This class listens for key presses (LEFT, RIGHT, SPACE) and mouse actions.
 * It stores input states so the game logic can easily query them each frame.
 */
public class InputHandler {

    //Keyboard state
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false; // For gun power-up or launch

    // Mouse state
    private double mouseX = 0;            // Current X position of the mouse
    private boolean mousePressed = false; // Whether mouse is currently pressed
    private boolean mouseClickedOnce = false; // Marks a single click event

    /**
     * Registers input listeners for keyboard and mouse on the given Scene.
     * <p>This should be called once when initializing the scene (e.g., before starting the game loop).
     * @param scene the JavaFX Scene where events will be captured
     */
    public void getKey(Scene scene) {
        // === Keyboard input ===
        scene.addEventHandler(
                KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        KeyCode code = keyEvent.getCode();
                        if (code == KeyCode.LEFT) {
                            leftPressed = true;
                        } else if (code == KeyCode.RIGHT) {
                            rightPressed = true;
                        } else if (code == KeyCode.SPACE) {
                            spacePressed = true;
                        }
                    }
                });

        scene.addEventHandler(
                KeyEvent.KEY_RELEASED,
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        KeyCode code = keyEvent.getCode();
                        if (code == KeyCode.LEFT) {
                            leftPressed = false;
                        } else if (code == KeyCode.RIGHT) {
                            rightPressed = false;
                        } else if (code == KeyCode.SPACE) {
                            spacePressed = false;
                        }
                    }
                });

        // Mouse input
        // Track mouse movement
        scene.addEventHandler(
                MouseEvent.MOUSE_MOVED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mouseX = mouseEvent.getX();
                    }
                });

        // Detect mouse press (start of a click)
        scene.addEventHandler(
                MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mousePressed = true;
                        mouseClickedOnce = true; // Mark a single click
                        mouseX = mouseEvent.getX();
                    }
                });

        // Detect mouse release (end of a click)
        scene.addEventHandler(
                MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        mousePressed = false;
                        mouseX = mouseEvent.getX();
                    }
                });
    }

    // Keyboard getters
    public boolean isLeftPressed() {
        return leftPressed;
    }
    public boolean isRightPressed() {
        return rightPressed;
    }
    public boolean isSpacePressed() {
        return spacePressed;
    }

    //Mouse getters
    /** Returns the current X position of the mouse cursor. */
    public double getMouseX() {
        return mouseX;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Consumes a single mouse click event.
     *
     * <p>Returns true only once per actual click, then resets the flag.
     *
     * @return true if a click occurred since last check, otherwise false
     */
    public boolean consumeClick() {
        if (mouseClickedOnce) {
            mouseClickedOnce = false;
            return true;
        }
        return false;
    }

    /**
     * Resets all input states to false.
     *
     * <p>Useful when restarting the game or changing scenes.
     */
    public void reset() {
        leftPressed = false;
        rightPressed = false;
        spacePressed = false;
        mouseClickedOnce = false;
        mousePressed = false;
    }
}
