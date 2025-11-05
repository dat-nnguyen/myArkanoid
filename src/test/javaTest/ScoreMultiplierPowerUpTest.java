import PowerUpSystem.PowerUpConfig;
import PowerUpSystem.PowerUpContext;
import PowerUpSystem.ScoreMultiplierPowerUp;
import audio.SoundManager;
import entities.Ball;
import entities.Paddle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;



class ScoreMultiplierPowerUpTest {

    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {}
    }
    private ScoreMultiplierPowerUp powerUp;
    private PowerUpContext context;
    private IntegerProperty fakeScoreProperty;

    @BeforeEach
    void setUp() {
        FakeSoundManager fakeSoundManager = new FakeSoundManager();
        Ball testBall = new Ball(fakeSoundManager, null);
        Paddle testPaddle = new Paddle(null);

        // Tạo score giả
        fakeScoreProperty = new SimpleIntegerProperty(0);

        // Tạo context
        context = new PowerUpContext(testBall, testPaddle, new ArrayList<Ball>(), fakeSoundManager);

        // Gán score giả vào context
        context.setScoreProperty(fakeScoreProperty);

        // Tạo đối tượng cần test
        powerUp = new ScoreMultiplierPowerUp(0, 0);
    }

    @Test
    void testApplyEffect_MultipliesScoreCorrectly() {
        // 1. Arrange
        fakeScoreProperty.set(100); // Đặt điểm ban đầu
        int multiplier = PowerUpConfig.SCORE_MULTIPLIER_VALUE; // 2
        int expectedScore = 100 * multiplier;

        // 2. Act
        powerUp.applyEffect(context);

        // 3. Assert
        // Kiểm tra xem score giả đã bị thay đổi chưa
        assertEquals(expectedScore, fakeScoreProperty.get(), "Điểm số đã không được nhân lên");
    }

    @Test
    void testApplyEffect_ScoreIsZero_RemainsZero() {
        // 1. Arrange
        fakeScoreProperty.set(0);
        int expectedScore = 0;

        // 2. Act
        powerUp.applyEffect(context);

        // 3. Assert
        assertEquals(expectedScore, fakeScoreProperty.get(), "Điểm 0 nhân lên phải vẫn là 0");
    }
}