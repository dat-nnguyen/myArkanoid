import CollisionManager.BallWithPaddle;
import audio.SoundManager;
import entities.Ball;
import entities.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Constants;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BallWithPaddleTest {

    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {
            // Không làm gì cả
        }
    }
    private Ball ball;
    private Paddle paddle;
    private SoundManager fakeSoundManager;

    @BeforeEach
    void setUp() {
        // 1. Arrange
        fakeSoundManager = new FakeSoundManager();

        // Tạo các đối tượng test bằng constructor test)
        ball = new Ball(fakeSoundManager, null); // constructor test từ Ball
        paddle = new Paddle(null); // constructor test từ Paddle

        // Set up kích thước để tính toán
        ball.setRadius(Constants.BALL_RADIUS); // 10
        ball.setWidth(Constants.BALL_RADIUS * 2); // 20
        ball.setHeight(Constants.BALL_RADIUS * 2); // 20

        paddle.setWidth(Constants.PADDLE_WIDTH); // 100 (Giả sử)
        paddle.setHeight(Constants.PADDLE_HEIGHT); // 20 (Giả sử)
    }

    @Test
    void testCollision_WhenNoCollision_ShouldDoNothing() {
        // 1. Arrange
        // Đặt paddle ở (100, 500)
        paddle.setPositionX(100); paddle.setPositionY(500);

        // Đặt bóng ở (500, 100) - Rất xa
        ball.setPositionX(500); ball.setPositionY(100);
        ball.setDirectionY(1.0); // Bay xuống
        double initialDirY = ball.getDirectionY();

        // 2. Act
        // Gọi logic va chạm(fake manager)
        BallWithPaddle.checkCollision(ball, paddle, fakeSoundManager);

        // 3. Assert
        // Hướng bóng KHÔNG đổi
        assertEquals(initialDirY, ball.getDirectionY(), "Hướng bóng không được thay đổi nếu không va chạm");
    }

    @Test
    void testCollision_FromTopCenter_ShouldReverseYAndSetAngle() {
        // 1. Arrange
        // Đặt paddle ở (100, 500), kích thước (100, 20)
        paddle.setPositionX(100); paddle.setPositionY(500);
        // => paddleCenterX = 150

        // Đặt bóng ở (140, 481), kích thước (20, 20) (bán kính 10)
        // Bóng đang ở ngay TRÊN TÂM paddle, bay xuống
        ball.setPositionX(140); ball.setPositionY(481);
        ball.setDirectionY(1.0); // Bay xuống
        ball.setDirectionX(0.5); // Bay xiên

        // 2. Act
        BallWithPaddle.checkCollision(ball, paddle, fakeSoundManager);

        // 3. Assert
        // Khi va chạm tâm, DirX mới = 0, DirY mới = -1 (đi lên)
        assertEquals(0.0, ball.getDirectionX(), 0.01, "Hướng X phải gần 0 khi va chạm tâm");
        assertEquals(-1.0, ball.getDirectionY(), 0.01, "Hướng Y phải là -1 khi va chạm tâm");
    }

    @Test
    void testCollision_FromSide_ShouldReverseX() {
        // 1. Arrange
        // Đặt paddle ở (100, 500), kích thước (100, 20)
        paddle.setPositionX(100); paddle.setPositionY(500);

        // Đặt bóng ở (81, 500), kích thước (20, 20) (bán kính 10)
        // Bóng đang ở ngay BÊN TRÁI paddle, bay sang phải
        ball.setPositionX(81); ball.setPositionY(500);
        ball.setDirectionX(1.0); // Bay sang phải
        double initialDirX = ball.getDirectionX();

        // 2. Act
        BallWithPaddle.checkCollision(ball, paddle, fakeSoundManager);

        // 3. Assert
        // Hướng bóng X PHẢI đổi
        assertEquals(-initialDirX, ball.getDirectionX(), "Hướng bóng X phải bị đảo ngược");
    }
}