import PowerUpSystem.PowerUpConfig;
import PowerUpSystem.PowerUpContext;
import PowerUpSystem.PowerUpType;
import PowerUpSystem.ShrinkPaddlePowerUp;
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


class ShrinkPaddlePowerUpTest {

    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {

        }
    }
    private ShrinkPaddlePowerUp powerUp;
    private PowerUpContext context;
    private Paddle fakePaddle;
    private Ball fakeBall;
    private IntegerProperty fakeScoreProperty;

    @BeforeEach
    void setUp() {
        // Arrange
        FakeSoundManager fakeSoundManager = new FakeSoundManager();
        fakeBall = new Ball(fakeSoundManager, null);
        fakePaddle = new Paddle(null);
        fakeScoreProperty = new SimpleIntegerProperty(0);

        // Tạo context (dùng constructor đã sửa)
        context = new PowerUpContext(fakeBall, fakePaddle, new ArrayList<Ball>(), fakeSoundManager);
        context.setScoreProperty(fakeScoreProperty); // Set score property

        // Tạo đối tượng cần test
        powerUp = new ShrinkPaddlePowerUp(0, 0); // Vị trí không quan trọng
    }

    @Test
    void testConstructor_SetsCorrectType() {
        // Assert
        // Kiểm tra xem super(PowerUpType.SHRINK_PADDLE) đã được gọi đúng
        assertEquals(PowerUpType.SHRINK_PADDLE, powerUp.getType(), "Loại PowerUp không đúng");
    }

    @Test
    void testApplyEffect_ShrinksPaddleWidthCorrectly() {
        // 1. Arrange
        // Lấy chiều rộng ban đầu
        double initialWidth = Constants.PADDLE_WIDTH;
        fakePaddle.setWidth((int) initialWidth); // Đảm bảo paddle có kích thước gốc

        // Tính toán chiều rộng mong đợi
        double expectedWidth = initialWidth * PowerUpConfig.SHRINK_PADDLE_MULTIPLIER;

        // Act
        // Áp dụng hiệu ứng
        powerUp.applyEffect(context);

        // Assert
        // Kiểm tra xem chiều rộng của paddle đã bị thu nhỏ đúng chưa
        assertEquals(expectedWidth, fakePaddle.getWidth(), "Paddle width đã không bị thu nhỏ đúng theo multiplier");
    }

    @Test
    void testRemoveEffect_RevertsPaddleWidthToOriginal() {
        // Arrange
        double initialWidth = Constants.PADDLE_WIDTH;
        fakePaddle.setWidth((int) initialWidth);

        // Tính toán chiều rộng khi bị ảnh hưởng
        double shrunkWidth = initialWidth * PowerUpConfig.SHRINK_PADDLE_MULTIPLIER;

        // Áp dụng hiệu ứng TRƯỚC
        powerUp.applyEffect(context);

        // Khẳng định rằng nó đã thực sự thay đổi
        assertEquals(shrunkWidth, fakePaddle.getWidth(), "Paddle đáng lẽ phải co lại trước khi test remove");

        // Act
        // Xóa bỏ hiệu ứng
        powerUp.removeEffect(context);

        // Assert
        // Kiểm tra xem chiều rộng đã quay về ban đầu chưa
        assertEquals(initialWidth, fakePaddle.getWidth(), "Paddle width đã không quay về kích thước gốc");
    }
}