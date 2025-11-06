import PowerUpSystem.PowerUpConfig;
import PowerUpSystem.PowerUpContext;
import PowerUpSystem.PowerUpType;
import PowerUpSystem.SlowMotionPowerUp;
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

class SlowMotionPowerUpTest {
    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override public void play(String soundName) { }
    }

    private SlowMotionPowerUp powerUp;
    private PowerUpContext context;
    private DoubleProperty fakeTimeScaleProperty; //fakeTimeScale

    @BeforeEach
    void setUp() {
        // 1. Arrange
        FakeSoundManager fakeSoundManager = new FakeSoundManager();
        Ball testBall = new Ball(fakeSoundManager, null);
        Paddle testPaddle = new Paddle(null);
        IntegerProperty fakeScoreProperty = new SimpleIntegerProperty(0);

        // Tạo một thuộc tính fakeTimeScale(bắt đầu ở 1.0)
        fakeTimeScaleProperty = new SimpleDoubleProperty(PowerUpConfig.NORMAL_TIME_SCALE);

        // Tạo context
        context = new PowerUpContext(testBall, testPaddle, new ArrayList<Ball>(), fakeSoundManager);

        // Gán các thuộc tính giả vào context
        context.setScoreProperty(fakeScoreProperty);
        context.setTimeScaleProperty(fakeTimeScaleProperty);

        // Tạo đối tượng cần test
        powerUp = new SlowMotionPowerUp(0, 0);
    }

    @Test
    void testConstructor_SetsCorrectType() {
        // 3. Assert
        assertEquals(PowerUpType.SLOW_MOTION, powerUp.getType(), "Loại PowerUp không đúng");
    }

    @Test
    void testApplyEffect_SetsCorrectTimeScale() {
        // 1. Arrange
        // Giá trị mong đợi
        double expectedTimeScale = PowerUpConfig.SLOW_MOTION_TIME_SCALE;

        // 2. Act
        powerUp.applyEffect(context);

        // 3. Assert
        // Kiểm tra xem timeScale giả đã bị thay đổi chưa
        assertEquals(expectedTimeScale, fakeTimeScaleProperty.get(), 0.001);
    }

    @Test
    void testRemoveEffect_RevertsToNormalTimeScale() {
        // 1. Arrange
        // Áp dụng hiệu ứng TRƯỚC
        powerUp.applyEffect(context);

        // Khẳng định là nó đã thay đổi
        assertEquals(PowerUpConfig.SLOW_MOTION_TIME_SCALE, fakeTimeScaleProperty.get(), 0.001);

        // Giá trị mong đợi
        double expectedTimeScale = PowerUpConfig.NORMAL_TIME_SCALE;

        // 2. Act
        powerUp.removeEffect(context); // Xóa hiệu ứng

        // 3. Assert
        // Kiểm tra xem timeScale giả đã về bình thường chưa
        assertEquals(expectedTimeScale, fakeTimeScaleProperty.get(), 0.001);
    }
}