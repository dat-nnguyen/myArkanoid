package core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Game Object Management (e.g., buttons, ball, paddle, bricks, etc.).
 *
 */
public abstract class GameObject {

    private double positionX; // Upper-left x-coordinate.
    private double positionY; // Upper-left y-coordinate.
    private int width; // The width of the object.
    private int height; // The height of the object.
    private Image texture; // The texture of the object.

    private void loadTexture(String texturePath) {
        //nếu path là null (ví dụ: khi test)
        // bỏ qua để tránh lỗi.
        if (texturePath == null || texturePath.isEmpty()) {
            this.texture = null;
            return;
        }

        // chỉ tải ảnh nếu tìm thấy
        try {
            String textureUrl = Objects.requireNonNull(getClass().getResource(texturePath)).toExternalForm();
            texture = new Image(textureUrl);
        } catch (Exception e) {
            // nếu có lỗi
            // Chỉ in ra lỗi và để texture là null.
            // KHÔNG cố tải "/null.png" vì sẽ làm crash test.
            System.err.println("Image loading failed! File not found at: " + texturePath);
            this.texture = null;
        }
    }

    /**
     * Initialize object with default coordinates (0,0).
     *
     * @param width The width of the object.
     * @param height The height of the object.
     * @param texturePath The path to the object's texture.
     */
    public GameObject(int width, int height, String texturePath) {
        this.positionX = 0.0;
        this.positionY = 0.0;
        this.width = width;
        this.height = height;
        loadTexture(texturePath);
    }

    /**
     * Initialize object.
     *
     * @param positionX Upper-left x-coordinate.
     * @param positionY Upper-left y-coordinate.
     * @param width The width of the object.
     * @param height The height of the object.
     * @param texturePath The path to the object's texture.
     */
    public GameObject(double positionX, double positionY, int width, int height, String texturePath) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        loadTexture(texturePath);
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

    public Image getTexture() { return texture; }

    public void setTexture(Image texture) { this.texture = texture; }
}