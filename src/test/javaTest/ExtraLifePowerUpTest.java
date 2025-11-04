import PowerUpSystem.ExtraLifePowerUp;
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

public class ExtraLifePowerUpTest {
    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {
        }
    }
    private ExtraLifePowerUp powerUp;
    private PowerUpContext context;
    private Ball FakeBall;

    @BeforeEach
    void setUp() {
        // 1. Arrange
        Paddle testPaddle = new Paddle(null);
        FakeBall = new Ball(new FakeSoundManager(), null);
        context = new PowerUpContext(FakeBall, testPaddle, new ArrayList<Ball>(), new SoundManager());
        powerUp = new ExtraLifePowerUp(0, 0);
    }

    @Test
    void testConstructor_SetsCorrectType() {
        // 2. Assert
        // Kiểm tra xem super(PowerUpType.EXTRA_LIFE) đã được gọi đúng
        assertEquals(PowerUpType.EXTRA_LIFE, powerUp.getType(), "Loại PowerUp không đúng");
    }

    @Test
    void testApplyEffect_AddsLife_WhenBelowMax() {
        // 1. Arrange
        // Giả sử bóng đang có 1 mạng
        int initialLives = 1;
        FakeBall.setLives(initialLives);

        // 2. Act
        // Áp dụng hiệu ứng
        powerUp.applyEffect(context);

        // 3. Assert
        // Mạng phải tăng lên 1 (thành 2)
        assertEquals(initialLives + 1, FakeBall.getLives(), "Mạng (lives) đã không tăng lên 1");
    }

    @Test
    void testApplyEffect_DoesNotAddLife_WhenAtMax() {
        // 1. Arrange
        // Giả sử bóng đang ở mức mạng tối đa (Constants.BALL_LIVES)
        int maxLives = Constants.BALL_LIVES;
        FakeBall.setLives(maxLives);

        // 2. Act
        // Áp dụng hiệu ứng
        powerUp.applyEffect(context);

        // 3. Assert
        // Mạng giữ nguyên ở mức tối đa
        assertEquals(maxLives, FakeBall.getLives(), "lives đã quá mức tối đa");
    }
}