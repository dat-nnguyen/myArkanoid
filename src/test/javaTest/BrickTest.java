import entities.Brick;
import entities.BrickType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BrickTest {

    private Brick brick;

    @BeforeEach
    void setUp() {
        // 1. Arrange (Chuẩn bị)
        // Gọi constructor "test" mới với texture path là null
        brick = new Brick(null);
    }

    @Test
    void testUpdate_DestroysBrick_WhenLivesReachZero() {
        // 1. Arrange
        // Thiết lập trạng thái ban đầu: Gạch bình thường, còn sống
        brick.setCurrentBrickType(BrickType.NORMAL);
        brick.setLives(1);
        brick.setIsDestroyed(false);

        // 2. Act
        // Giảm mạng của gạch xuống 0 và gọi update()
        brick.setLives(0);
        brick.update(1.0); // deltaTime không quan trọng ở đây

        // 3. Assert
        // Kiểm tra xem gạch đã bị phá hủy chưa
        assertTrue(brick.getIsDestroyed(), "Gạch phải bị phá hủy (isDestroyed = true) khi lives <= 0");
    }

    @Test
    void testUpdate_DoesNotDestroyBrick_WhenUnbreakable() {
        // 1. Arrange
        // Thiết lập là gạch không thể phá hủy
        brick.setCurrentBrickType(BrickType.IMPOSSIBLE);
        brick.setLives(0); // Thậm chí gán lives = 0
        brick.setIsDestroyed(false);

        // 2. Act
        brick.update(1.0);

        // 3. Assert
        // Gạch KHÔNG được phép bị phá hủy
        assertFalse(brick.getIsDestroyed(), "Gạch UNBREAKABLE không bao giờ được bị phá hủy");
    }

    @Test
    void testShouldSpawnPowerUp_ReturnsTrue_OnlyOnceWhenDestroyed() {
        // 1. Arrange
        // Gạch bình thường, vừa bị phá hủy
        brick.setCurrentBrickType(BrickType.NORMAL);
        brick.setIsDestroyed(true);

        // 2. Act (Lần 1)
        boolean firstCallResult = brick.shouldSpawnPowerUp();

        // 2. Act (Lần 2)
        // Gọi lại ngay lập tức
        boolean secondCallResult = brick.shouldSpawnPowerUp();

        // 3. Assert
        assertTrue(firstCallResult, "Phải trả về TRUE ở lần gọi đầu tiên sau khi gạch vỡ");
        assertFalse(secondCallResult, "Phải trả về FALSE ở lần gọi thứ hai (chỉ spawn 1 lần)");
    }

    @Test
    void testShouldSpawnPowerUp_ReturnsFalse_WhenNotDestroyed() {
        // 1. Arrange
        // Gạch bình thường, CHƯA bị phá hủy
        brick.setCurrentBrickType(BrickType.NORMAL);
        brick.setIsDestroyed(false);

        // 2. Act
        boolean result = brick.shouldSpawnPowerUp();

        // 3. Assert
        assertFalse(result, "Không được spawn power-up khi gạch chưa vỡ");
    }

    @Test
    void testShouldSpawnPowerUp_ReturnsFalse_WhenUnbreakable() {
        // 1. Arrange
        // Gạch không thể vỡ, và đã bị vỡ
        brick.setCurrentBrickType(BrickType.IMPOSSIBLE);
        brick.setIsDestroyed(true);

        // 2. Act
        boolean result = brick.shouldSpawnPowerUp();

        // 3. Assert
        //!currentBrickType.isUnbreakable()
        assertFalse(result, "Gạch UNBREAKABLE không được spawn power-up");
    }
}