import PowerUpSystem.FastBallPowerUp;
import PowerUpSystem.PowerUpConfig;
import PowerUpSystem.PowerUpContext;
import PowerUpSystem.PowerUpType;
import audio.SoundManager;
import entities.Ball;
import entities.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Constants;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FastBallPowerUpTest {
    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {
        }
    }

    private FastBallPowerUp powerUp;
    private PowerUpContext context;
    private Paddle FakePaddle;
    private Ball mainBall;
    private Ball extraBall; // Bóng phụ để test
    private ArrayList<Ball> ballList;

    // Hàm này chạy TRƯỚC MỖI @Test
    @BeforeEach
    void setUp() {
        // 1. Arrange
        FakeSoundManager fakeManager = new FakeSoundManager();
        FakePaddle = new Paddle(null);

        // Tạo bóng chính
        mainBall = new Ball(fakeManager, null);
        mainBall.setSpeed(Constants.BALL_SPEED); // Đặt tốc độ gốc

        // Tạo bóng phụ
        extraBall = new Ball(fakeManager, null);
        extraBall.setSpeed(Constants.BALL_SPEED); // Đặt tốc độ gốc

        // Tạo danh sách bóng và thêm bóng phụ vào
        ballList = new ArrayList<>();
        ballList.add(extraBall);

        // Tạo context
        context = new PowerUpContext(mainBall, FakePaddle, ballList, new SoundManager());

        // Tạo đối tượng cần test
        powerUp = new FastBallPowerUp(0, 0);
    }

    @Test
    void testConstructor_SetsCorrectType() {
        // 3. Assert
        assertEquals(PowerUpType.FAST_BALL, powerUp.getType(), "Loại PowerUp không đúng");
    }

    @Test
    void testApplyEffect_IncreasesSpeedForAllBalls() {
        // 1. Arrange
        double initialSpeed = Constants.BALL_SPEED;

        // Tính toán tốc độ mong đợi
        double expectedSpeed = initialSpeed * PowerUpConfig.FAST_BALL_MULTIPLIER;

        // 2. Act
        // Áp dụng hiệu ứng
        powerUp.applyEffect(context);

        // 3. Assert
        // Kiểm tra cả hai bóng
        assertEquals(expectedSpeed, mainBall.getSpeed(), "Tốc độ BÓNG CHÍNH không tăng đúng");
        assertEquals(expectedSpeed, extraBall.getSpeed(), "Tốc độ BÓNG PHỤ không tăng đúng");
    }

    @Test
    void testRemoveEffect_RevertsSpeedForAllBalls() {
        // 1. Arrange
        double initialSpeed = Constants.BALL_SPEED;

        // Áp dụng hiệu ứng TRƯỚC
        powerUp.applyEffect(context);

        // Khẳng định rằng tốc độ đã tăng
        double expandedSpeed = initialSpeed * PowerUpConfig.FAST_BALL_MULTIPLIER;
        assertEquals(expandedSpeed, mainBall.getSpeed(), "Bóng chính đáng lẽ phải tăng tốc trước khi test remove");
        assertEquals(expandedSpeed, extraBall.getSpeed(), "Bóng phụ đáng lẽ phải tăng tốc trước khi test remove");

        // 2. Act
        // Xóa bỏ hiệu ứng
        powerUp.removeEffect(context);

        // 3. Assert
        // Kiểm tra cả hai bóng đã quay về tốc độ ban đầu
        assertEquals(initialSpeed, mainBall.getSpeed(), "Tốc độ BÓNG CHÍNH không quay về ban đầu");
        assertEquals(initialSpeed, extraBall.getSpeed(), "Tốc độ BÓNG PHỤ không quay về ban đầu");
    }
}