import PowerUpSystem.MultiBallPowerUp;
import PowerUpSystem.PowerUpConfig;
import PowerUpSystem.PowerUpContext;
import PowerUpSystem.PowerUpType;
import audio.SoundManager;
import entities.Ball;
import entities.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class MultiBallPowerUpTest {
    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {
            // Không làm gì cả
        }
    }
    private MultiBallPowerUp powerUp; // Lớp cần test
    private PowerUpContext context;
    private Ball mainBall;
    private Paddle fakePaddle;
    private ArrayList<Ball> ballList;
    private FakeSoundManager fakeSoundManager;
    @BeforeEach
    void setUp() {
        // 1. Arrange
        fakeSoundManager = new FakeSoundManager();
        mainBall = new Ball(fakeSoundManager, null);
        fakePaddle = new Paddle(null);
        ballList = new ArrayList<>(); // Danh sách bóng phụ

        // Tạo context
        context = new PowerUpContext(mainBall, fakePaddle, ballList, fakeSoundManager);
        
        // Tạo đối tượng cần test
        powerUp = new MultiBallPowerUp(0, 0);
    }

    @Test
    void testConstructor_SetsCorrectType() {
        // 3. Assert
        assertEquals(PowerUpType.MULTI_BALL, powerUp.getType(), "Loại PowerUp không đúng");
    }

    @Test
    void testApplyEffect_NoActiveBall_DoesNothing() {
        // 1. Arrange
        // Đảm bảo bóng chính không chơi và danh sách bóng phụ rỗng
        mainBall.play = false;
        ballList.clear();

        // 2. Act
        powerUp.applyEffect(context);

        // 3. Assert
        // Danh sách bóng phụ phải vẫn rỗng
        assertTrue(ballList.isEmpty(), "Không được tạo bóng mới nếu không có bóng nào đang hoạt động");
    }

    @Test
    void testApplyEffect_WithActiveBall_CreatesNewBallsWithCorrectProperties() {
        // 1. Arrange
        mainBall.play = true;
        mainBall.setPositionX(100);
        mainBall.setPositionY(200);
        mainBall.setSpeed(500);
        ballList.clear();

        int expectedBallCount = PowerUpConfig.MULTI_BALL_COUNT;

        // 2. Act
        powerUp.applyEffect(context);

        // 3. Assert
        // 1. Kiểm tra số lượng bóng
        assertEquals(expectedBallCount, ballList.size(), "Phải tạo ra đúng số lượng bóng mới");

        // 2. Lấy bóng mới đầu tiên ra để kiểm tra
        Ball newBall = ballList.get(0);

        // 3. Kiểm tra các thuộc tính đã được sao chép
        assertEquals(100, newBall.getPositionX(), "Bóng mới phải sao chép vị trí X");
        assertEquals(200, newBall.getPositionY(), "Bóng mới phải sao chép vị trí Y");
        assertEquals(500, newBall.getSpeed(), "Bóng mới phải sao chép tốc độ");

        // 4. Kiểm tra các thuộc tính RIÊNG của bóng mới (Rất quan trọng)
        assertFalse(newBall.isMainBall(), "Bóng mới KHÔNG được là bóng chính");
        assertEquals(1, newBall.getLives(), "Bóng mới phải có 1 mạng");
        assertTrue(newBall.play, "Bóng mới phải ở trạng thái 'play'");
    }
}