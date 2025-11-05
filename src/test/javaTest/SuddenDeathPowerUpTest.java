import PowerUpSystem.PowerUpContext;
import PowerUpSystem.PowerUpType;
import PowerUpSystem.SuddenDeathPowerUp;
import audio.SoundManager;
import entities.Ball;
import entities.Paddle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class SuddenDeathPowerUpTest {
    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {
        }
    }
    private SuddenDeathPowerUp powerUp;
    private PowerUpContext context;
    private Ball mainBall;
    private Ball extraBall; // Bóng phụ để test
    private ArrayList<Ball> ballList;

    @BeforeEach
    void setUp() {
        // 1. Arrange
        FakeSoundManager fakeManager = new FakeSoundManager();
        Paddle testPaddle = new Paddle(null);

        // Tạo bóng chính
        mainBall = new Ball(fakeManager, null);

        // Tạo bóng phụ
        extraBall = new Ball(fakeManager, null);

        // Tạo danh sách bóng và thêm bóng phụ vào
        ballList = new ArrayList<>();
        ballList.add(extraBall);

        IntegerProperty fakeScoreProperty = new SimpleIntegerProperty(0);
        DoubleProperty fakeTimeScaleProperty = new SimpleDoubleProperty(1.0);

        // Tạo context\
        context = new PowerUpContext(mainBall, testPaddle, ballList, fakeManager);
        context.setScoreProperty(fakeScoreProperty);
        context.setTimeScaleProperty(fakeTimeScaleProperty);

        // Tạo đối tượng cần test
        powerUp = new SuddenDeathPowerUp(0, 0);
    }

    @Test
    void testConstructor_SetsCorrectType() {
        // 3. Assert
        assertEquals(PowerUpType.SUDDEN_DEATH, powerUp.getType(), "Loại PowerUp không đúng");
    }

    @Test
    void testApplyEffect_KillsAllBalls() {
        // 1. Arrange
        // Đặt mạng cho cả hai bóng và play
        mainBall.setLives(3);
        mainBall.play = true;

        extraBall.setLives(1);
        extraBall.play = true;

        // 2. Act
        // Áp dụng hiệu ứng
        powerUp.applyEffect(context);

        // 3. Assert
        // Kiểm tra cả hai bóng
        assertEquals(0, mainBall.getLives(), "Mạng (lives) của bóng chính phải bằng 0");
        assertFalse(mainBall.play, "Bóng chính 'play' phải là false");

        assertEquals(0, extraBall.getLives(), "Mạng (lives) của bóng phụ phải bằng 0");
        assertFalse(extraBall.play, "Bóng phụ 'play' phải là false");
    }
}