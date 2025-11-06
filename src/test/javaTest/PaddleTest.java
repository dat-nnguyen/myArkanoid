import entities.Paddle;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaddleTest {
    private Paddle paddle;

    @BeforeEach
    public void setUp() {
        paddle = new Paddle(null);
    }

    @Test
    void testReset_ReturnsAllValuesToDefault() {
        // Arrange
        paddle.setPositionX(123);
        paddle.setSpeed(999);
        paddle.setWidth(1000);
        paddle.setKeyPressed(KeyCode.LEFT, true); // Nhấn phím
        double initialX = paddle.getPositionX();

        // Act
        paddle.reset(); // reset các đại lượng từ đầu

        // Assert
        assertEquals(Constants.PADDLE_START_POSITION_X, paddle.getPositionX(), "Vị trí X phải được reset");
        assertEquals(Constants.PADDLE_SPEED, paddle.getSpeed(), "speed hải được reset");
        assertEquals(Constants.PADDLE_WIDTH, paddle.getWidth(), "width phải được reset");

        // Kiểm tra xem keyPressed có được reset không
        paddle.update(1.0);
        assertEquals(Constants.PADDLE_START_POSITION_X, paddle.getPositionX(), "Paddle vẫn di chuyển sau khi reset, keyPressed chưa được xóa");
    }

    @Test
    void testMove_MovesRight_WhenRightKeyPressed() {
        // 1. Arrange
        paddle.reset();
        double initialX = paddle.getPositionX();
        // Giả lập nhấn phím PHẢI
        paddle.setKeyPressed(KeyCode.RIGHT, true);

        // 2. Act
        // Giả lập game chạy trong 1 giây
        paddle.update(1.0);

        // 3. Assert
        // Lấy tốc độ TRỰC TIẾP TỪ PADDLE
        double expectedX = initialX + paddle.getSpeed() * 1.0;

        assertEquals(expectedX, paddle.getPositionX(), "Paddle không di chuyển sang phải");
    }

    @Test
    void testMove_MovesLeft_WhenLeftKeyPressed() {
        // 1. Arrange
        paddle.reset();
        double initialX = paddle.getPositionX();
        // Giả lập nhấn phím TRÁI
        paddle.setKeyPressed(KeyCode.LEFT, true);

        // 2. Act
        paddle.update(1.0); // 1 giây

        // 3. Assert
        // Lấy tốc độ TRỰC TIẾP TỪ PADDLE và dùng dấu TRỪ
        double expectedX = initialX - paddle.getSpeed() * 1.0;

        assertEquals(expectedX, paddle.getPositionX(), "Paddle không di chuyển sang trái");
    }
    @Test
    void testMove_DoesNotMove_WhenNoKeyPressed() {
        // 1. Arrange
        paddle.reset();
        double initialX = paddle.getPositionX();
        // Không nhấn phím nào (reset đã làm điều này)

        // 2. Act
        paddle.update(1.0);

        // 3. Assert
        assertEquals(initialX, paddle.getPositionX(), "Paddle đã di chuyển dù không có phím nào được nhấn");
    }

    // === TEST LOGIC QUAN TRỌNG NHẤT (BIÊN) ===

    @Test
    void testMove_StopsAtLeftBoundary() {
        // 1. Arrange
        double leftBoundary = Constants.MARGIN_WIDTH;
        paddle.setPositionX(leftBoundary + 1.0); // Đặt paddle ngay sát biên trái
        paddle.setKeyPressed(KeyCode.LEFT, true); // Cố gắng di chuyển sang trái

        // 2. Act
        paddle.update(1.0); // Di chuyển trong 1 giây (quá đủ để đâm sầm)

        // 3. Assert
        // Vị trí X phải bị "kẹp" (clamp) lại ở biên trái
        assertEquals(leftBoundary, paddle.getPositionX(), "Paddle đã di chuyển vượt quá biên trái");
    }

    @Test
    void testMove_StopsAtRightBoundary() {
        // 1. Arrange
        double rightBoundary = Constants.MARGIN_WIDTH + Constants.PLAY_SCREEN_WIDTH;
        double paddleWidth = paddle.getWidth();
        // Đặt paddle ngay sát biên phải
        paddle.setPositionX(rightBoundary - paddleWidth - 1.0);
        paddle.setKeyPressed(KeyCode.RIGHT, true); // Cố gắng di chuyển sang phải

        // 2. Act
        paddle.update(1.0); // Di chuyển trong 1 giây

        // 3. Assert
        // Vị trí X phải bị "kẹp" (clamp) lại ở biên phải
        assertEquals(rightBoundary - paddleWidth, paddle.getPositionX(), "Paddle đã di chuyển vượt quá biên phải");
    }

}