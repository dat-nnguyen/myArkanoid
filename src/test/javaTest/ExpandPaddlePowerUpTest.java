import PowerUpSystem.ExpandPaddlePowerUp;
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

class ExpandPaddlePowerUpTest {

    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {
        }
    }

    private ExpandPaddlePowerUp powerUp;
    private PowerUpContext context;
    private Paddle FakePaddle;
    private Ball FakeBall;

    // Hàm này chạy TRƯỚC MỖI @Test
    @BeforeEach
    void setUp() {
        // 1. Arrange (Chuẩn bị)
        FakePaddle = new Paddle(null);
        FakeBall = new Ball(new FakeSoundManager(), null);
        context = new PowerUpContext(FakeBall, FakePaddle, new ArrayList<Ball>(), new SoundManager());
        // Tạo đối tượng cần test
        powerUp = new ExpandPaddlePowerUp(0, 0);
    }

    @Test
    void testConstructor_SetsCorrectType() {
        // 3. Assert
        // Kiểm tra xem super(PowerUpType.EXPAND_PADDLE) đã được gọi đúng
        assertEquals(PowerUpType.EXPAND_PADDLE, powerUp.getType(), "Loại PowerUp không đúng");
    }

    @Test
    void testApplyEffect_IncreasesPaddleWidthCorrectly() {
        // 1. Arrange
        double initialWidth = Constants.PADDLE_WIDTH;
        FakePaddle.setWidth((int) initialWidth); // Đảm bảo paddle có kích thước gốc

        // Tính toán chiều rộng mong đợi
        double expectedWidth = initialWidth * PowerUpConfig.EXPAND_PADDLE_MULTIPLIER;
        //Act
        // Áp dụng hiệu ứng
        powerUp.applyEffect(context);
        //Assert
        // Kiểm tra xem chiều rộng của paddle đã tăng đúng chưa
        assertEquals(expectedWidth, FakePaddle.getWidth(), "Paddle width đã không tăng đúng theo multiplier");
    }

    @Test
    void testRemoveEffect_RevertsPaddleWidthToOriginal() {
        // 1. Arrange
        // Lấy chiều rộng ban đầu
        double initialWidth = Constants.PADDLE_WIDTH;
        FakePaddle.setWidth((int) initialWidth);

        // Tính toán chiều rộng khi bị ảnh hưởng
        double expandedWidth = initialWidth * PowerUpConfig.EXPAND_PADDLE_MULTIPLIER;

        // Áp dụng hiệu ứng TRƯỚC
        powerUp.applyEffect(context);

        // Khẳng định rằng nó đã thực sự thay đổi
        assertEquals(expandedWidth, FakePaddle.getWidth(), "Paddle đáng lẽ phải giãn ra trước khi test remove");

        // 2. Act
        // Xóa bỏ hiệu ứng
        powerUp.removeEffect(context);

        // 3. Assert
        // Kiểm tra xem chiều rộng đã quay về ban đầu chưa
        assertEquals(initialWidth, FakePaddle.getWidth(), "Paddle width đã không quay về kích thước gốc");
    }
}