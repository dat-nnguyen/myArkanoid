import PowerUpSystem.PowerUpConfig;
import PowerUpSystem.PowerUpContext;
import PowerUpSystem.PowerUpType;
import PowerUpSystem.SlowBallPowerUp;
import audio.SoundManager;
import entities.Ball;
import entities.Paddle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Constants;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;


class SlowBallPowerUpTest {

    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {

        }
    }
    private SlowBallPowerUp powerUp;
    private PowerUpContext context;
    private Paddle fakeTestPaddle;
    private Ball mainBall;
    private Ball extraBall; // Bóng phụ để test
    private ArrayList<Ball> ballList;
    private IntegerProperty fakeScoreProperty;

    @BeforeEach
    void setUp() {
        FakeSoundManager fakeManager = new FakeSoundManager();
        fakeTestPaddle = new Paddle(null);

        // Tạo bóng chính
        mainBall = new Ball(fakeManager, null);
        mainBall.setSpeed(Constants.BALL_SPEED); // Đặt tốc độ gốc

        // Tạo bóng phụ
        extraBall = new Ball(fakeManager, null);
        extraBall.setSpeed(Constants.BALL_SPEED); // Đặt tốc độ gốc

        // Tạo danh sách bóng và thêm bóng phụ vào
        ballList = new ArrayList<>();
        ballList.add(extraBall);

        fakeScoreProperty = new SimpleIntegerProperty(0);

        // Tạo context
        context = new PowerUpContext(mainBall, fakeTestPaddle, ballList, fakeManager);
        context.setScoreProperty(fakeScoreProperty);

        // Tạo đối tượng cần test
        powerUp = new SlowBallPowerUp(0, 0);
    }

    @Test
    void testConstructor_SetsCorrectType() {
        assertEquals(PowerUpType.SLOW_BALL, powerUp.getType(), "Loại PowerUp không đúng");
    }

    @Test
    void testApplyEffect_DecreasesSpeedForAllBalls() {
        // 1. Arrange
        double initialSpeed = Constants.BALL_SPEED;

        // Tính toán tốc độ mong đợi (sẽ chậm hơn)
        double expectedSpeed = initialSpeed * PowerUpConfig.SLOW_BALL_MULTIPLIER;

        // 2. Act
        // Áp dụng hiệu ứng
        powerUp.applyEffect(context);

        // 3. Assert
        // Kiểm tra cả hai bóng
        assertEquals(expectedSpeed, mainBall.getSpeed(), "Tốc độ BÓNG CHÍNH không giảm đúng");
        assertEquals(expectedSpeed, extraBall.getSpeed(), "Tốc độ BÓNG PHỤ không giảm đúng");
    }

    @Test
    void testRemoveEffect_RevertsSpeedForAllBalls() {
        // 1. Arrange
        double initialSpeed = Constants.BALL_SPEED;

        // Áp dụng hiệu ứng
        powerUp.applyEffect(context);

        //  tốc độ đã giảm
        double slowSpeed = initialSpeed * PowerUpConfig.SLOW_BALL_MULTIPLIER;
        assertEquals(slowSpeed, mainBall.getSpeed(), "Bóng chính đáng lẽ phải chậm lại trước khi test remove");
        assertEquals(slowSpeed, extraBall.getSpeed(), "Bóng phụ đáng lẽ phải chậm lại trước khi test remove");

        // 2. Act
        // Xóa bỏ hiệu ứng
        powerUp.removeEffect(context);

        // 3. Assert
        // Kiểm tra cả hai bóng đã quay về tốc độ ban đầu
        assertEquals(initialSpeed, mainBall.getSpeed(), "Tốc độ BÓNG CHÍNH không quay về ban đầu");
        assertEquals(initialSpeed, extraBall.getSpeed(), "Tốc độ BÓNG PHỤ không quay về ban đầu");
    }
}