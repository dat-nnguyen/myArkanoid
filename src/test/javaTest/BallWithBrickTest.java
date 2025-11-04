import CollisionManager.BallWithBrick;
import audio.SoundManager;
import entities.Ball;
import entities.Brick;
import entities.BrickType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BallWithBrickTest {
    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {
        }
    }
    private Ball ball;
    private Brick brick;
    private SoundManager fakeSoundManager;

    @BeforeEach
    void setUp() {
        // 1. Arrange (Chuẩn bị)
        fakeSoundManager = new FakeSoundManager();

        // Tạo các đối tượng test (dùng constructor test)
        ball = new Ball(fakeSoundManager, null); // constructor test từ Ball
        brick = new Brick(null); // constructor test từ Brick

        // Set up kích thước để tính toán
        ball.setRadius(Constants.BALL_RADIUS); // 10
        ball.setWidth(Constants.BALL_RADIUS * 2); // 20
        ball.setHeight(Constants.BALL_RADIUS * 2); // 20

        brick.setWidth(Constants.BRICK_WIDTH); // 60
        brick.setHeight(Constants.BRICK_HEIGHT); // 20
    }

    @Test
    void testCollision_WhenBrickIsDestroyed_ShouldDoNothing() {
        // 1. Arrange
        brick.setIsDestroyed(true); // Gạch đã vỡ
        brick.setLives(0);
        ball.setDirectionY(1.0); // Bóng đang bay

        // 2. Act
        // Gọi logic va chạm
        BallWithBrick.checkCollision(ball, brick, fakeSoundManager);

        // 3. Assert
        // Hướng bóng KHÔNG đổi, mạng gạch KHÔNG đổi
        assertEquals(1.0, ball.getDirectionY(), "Hướng bóng không được thay đổi nếu gạch đã vỡ");
        assertEquals(0, brick.getLives(), "Mạng gạch không được thay đổi");
    }

    @Test
    void testCollision_WhenNoCollision_ShouldDoNothing() {
        // 1. Arrange
        // Đặt gạch ở (100, 100)
        brick.setPositionX(100); brick.setPositionY(100);
        brick.setLives(2);

        // Đặt bóng ở (500, 500) - Rất xa
        ball.setPositionX(500); ball.setPositionY(500);
        ball.setDirectionY(1.0);

        // 2. Act
        BallWithBrick.checkCollision(ball, brick, fakeSoundManager);

        // 3. Assert
        // Hướng bóng KHÔNG đổi, mạng gạch KHÔNG đổi
        assertEquals(1.0, ball.getDirectionY(), "Hướng bóng không được thay đổi nếu không va chạm");
        assertEquals(2, brick.getLives(), "Mạng gạch không được thay đổi nếu không va chạm");
    }

    @Test
    void testCollision_FromTop_ShouldReverseYAndLoseLife() {
        // 1. Arrange
        // Đặt gạch ở (100, 100), kích thước (60, 20)
        brick.setPositionX(100); brick.setPositionY(100);
        brick.setCurrentBrickType(BrickType.NORMAL);
        brick.setLives(2);

        // Đặt bóng ở (120, 81), kích thước (20, 20), bán kính 10
        // Bóng đang ở ngay TRÊN gạch
        ball.setPositionX(120); ball.setPositionY(81);
        ball.setDirectionY(1.0); // Bay xuống
        double initialDirY = ball.getDirectionY();

        // 2. Act
        BallWithBrick.checkCollision(ball, brick, fakeSoundManager);

        // 3. Assert
        // Hướng bóng PHẢI đổi, mạng gạch PHẢI giảm
        assertEquals(-initialDirY, ball.getDirectionY(), "Hướng bóng Y (directionY) phải bị đảo ngược");
        assertEquals(1, brick.getLives(), "Mạng gạch (lives) phải giảm 1");
    }

    @Test
    void testCollision_FromSide_ShouldReverseXAndLoseLife() {
        // 1. Arrange
        // Đặt gạch ở (100, 100), kích thước (60, 20)
        brick.setPositionX(100); brick.setPositionY(100);
        brick.setCurrentBrickType(BrickType.NORMAL);
        brick.setLives(2);

        // Đặt bóng ở (81, 100), kích thước (20, 20), bán kính 10
        // Bóng đang ở ngay BÊN TRÁI gạch
        ball.setPositionX(81); ball.setPositionY(100);
        ball.setDirectionX(1.0); // Bay sang phải
        double initialDirX = ball.getDirectionX();

        // 2. Act
        BallWithBrick.checkCollision(ball, brick, fakeSoundManager);

        // 3. Assert
        // Hướng bóng PHẢI đổi, mạng gạch PHẢI giảm
        assertEquals(-initialDirX, ball.getDirectionX(), "Hướng bóng X (directionX) phải bị đảo ngược");
        assertEquals(1, brick.getLives(), "Mạng gạch (lives) phải giảm 1");
    }

    @Test
    void testCollision_WithUnbreakableBrick_ShouldReverseBallButNotLoseLife() {
        // 1. Arrange
        // Đặt gạch ở (100, 100)
        brick.setPositionX(100); brick.setPositionY(100);
        brick.setCurrentBrickType(BrickType.IMPOSSIBLE); // Gạch KHÔNG THỂ VỠ
        brick.setLives(99); // Gán mạng tượng trưng

        // Đặt bóng ở (120, 81) - va chạm từ trên
        ball.setPositionX(120); ball.setPositionY(81);
        ball.setDirectionY(1.0); // Bay xuống
        double initialDirY = ball.getDirectionY();

        // 2. Act
        BallWithBrick.checkCollision(ball, brick, fakeSoundManager);

        // 3. Assert
        // Hướng bóng PHẢI đổi, mạng gạch KHÔNG đổi
        assertEquals(-initialDirY, ball.getDirectionY(), "Hướng bóng Y (directionY) phải bị đảo ngược");
        assertEquals(99, brick.getLives(), "Mạng gạch (lives) KHÔNG được thay đổi với gạch UNBREAKABLE");
    }
}