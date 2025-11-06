import PowerUpSystem.PowerUpContext;
import PowerUpSystem.PowerUpManager;
import PowerUpSystem.PowerUpType;
import PowerUpSystem.TimedPowerUp;
import audio.SoundManager;
import core.GameLoop;
import entities.Ball;
import entities.Paddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



/**
 * Lớp FakePowerUp để theo dõi các hiệu ứng
 * dùng để kiểm tra xem apply/remove có được gọi không
 */
class FakeTimedPowerUp extends TimedPowerUp {
    boolean effectApplied = false;
    boolean effectRemoved = false;
    boolean collected = false;

    public FakeTimedPowerUp(PowerUpType type) {
        super(0, 0, type);
        this.setActive(true); // Đang rơi
        this.setWidth(10); // Đặt kích thước để va chạm
        this.setHeight(10);
    }

    @Override
    public void applyEffect(PowerUpContext context) {
        effectApplied = true;
    }

    @Override
    public void removeEffect(PowerUpContext context) {
        effectRemoved = true;
    }

    @Override
    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    @Override
    public boolean isCollected() {
        return this.collected;
    }
}


class PowerUpManagerTest {
    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() { super(); }
        @Override
        public void play(String soundName) {
            // Không làm gì cả
        }
    }

    private PowerUpManager manager;
    private PowerUpContext context;
    private Ball testBall;
    private Paddle testPaddle;
    private ArrayList<Ball> ballList;
    private FakeSoundManager fakeSoundManager;
    private GameLoop fakeGameLoop;


    @BeforeEach
    void setUp() {
        // 1. Arrange
        fakeSoundManager = new FakeSoundManager();

        // Tạo các đối tượng test
        testBall = new Ball(fakeSoundManager, null);
        testPaddle = new Paddle(null);
        ballList = new ArrayList<>();

        // fakeGameLoop là null
        fakeGameLoop = null;

        // Tạo context
        context = new PowerUpContext(testBall, testPaddle, ballList, fakeSoundManager);

        // Tạo Manager
        manager = new PowerUpManager(testBall, testPaddle, ballList, fakeSoundManager);

        // Truyền null logic if(.. != null)
        manager.setGameLoop(fakeGameLoop);
    }

    @Test
    void testReset_CallsRemoveEffect_OnActiveEffects() {
        // Arrange
        // Tạo một power-up và giar sử đã được nhặt
        FakeTimedPowerUp powerUp = new FakeTimedPowerUp(PowerUpType.EXPAND_PADDLE);

        // Giả lập va chạm để nhận power up
        // Đặt paddle và power-up ở cùng 1 chỗ
        testPaddle.setPositionX(0); testPaddle.setPositionY(0);
        powerUp.setPositionX(0); powerUp.setPositionY(0);

        manager.getActivePowerUps().add(powerUp); // Thêm vào danh sách rơi
        manager.checkCollision(testPaddle); // Gọi hàm public để thu thập

        // power up đã được thu thập và đang active
        assertTrue(powerUp.effectApplied, "Effect đáng lẽ phải được áp dụng");
        assertFalse(manager.activeEffects.isEmpty(), "Phải có 1 effect active");

        // Act
        manager.reset();

        // Assert
        assertTrue(powerUp.effectRemoved, "removeEffect() phải được gọi khi reset");
        assertTrue(manager.activeEffects.isEmpty(), "activeEffects phải rỗng sau khi reset");
    }

    @Test
    void testUpdate_RemovesEffect_AfterDuration() {
        // 1. Arrange
        FakeTimedPowerUp powerUp = new FakeTimedPowerUp(PowerUpType.EXPAND_PADDLE);
        // Đặt thời gian tồn tại của power-up (lấy từ type)
        double duration = powerUp.getType().getDuration(); // Giả sử 10s

        // Giả lập thu thập power-up
        testPaddle.setPositionX(0); testPaddle.setPositionY(0);
        powerUp.setPositionX(0); powerUp.setPositionY(0);
        manager.getActivePowerUps().add(powerUp);
        manager.checkCollision(testPaddle);

        // Act (Lần 1)
        // Chạy update gần hết giờ (ví dụ: 9.9 giây)
        manager.update(duration - 0.1);

        // Assert (Lần 1)
        assertFalse(powerUp.effectRemoved, "Effect không được xóa trước khi hết giờ");

        // Act (Lần 2)
        // Chạy update thêm (qua mốc 10 giây)
        manager.update(0.2); // Tổng 10.1 giây

        // 3. Assert (Lần 2)
        assertTrue(powerUp.effectRemoved, "Effect PHẢI bị xóa sau khi hết giờ");
        assertTrue(manager.activeEffects.isEmpty(), "activeEffects phải rỗng sau khi effect hết hạn");
    }

    @Test
    void testConflict_OppositePowerUps_CancelOut() {
        // Arrange
        // Tạo 2 power-up đối lập
        FakeTimedPowerUp expand = new FakeTimedPowerUp(PowerUpType.EXPAND_PADDLE);
        expand.setPositionX(0); expand.setPositionY(0);

        FakeTimedPowerUp shrink = new FakeTimedPowerUp(PowerUpType.SHRINK_PADDLE);
        shrink.setPositionX(0); shrink.setPositionY(0);

        testPaddle.setPositionX(0); testPaddle.setPositionY(0);

        // Act (Lần 1: Thu thập Expand)
        manager.getActivePowerUps().add(expand);
        manager.checkCollision(testPaddle);

        // Assert (Lần 1)
        assertTrue(expand.effectApplied, "Expand effect phải được áp dụng");
        assertFalse(manager.activeEffects.isEmpty(), "Phải có 1 effect (Expand) active");

        // Act (Lần 2: Thu thập Shrink)
        manager.getActivePowerUps().add(shrink);
        manager.checkCollision(testPaddle); // Logic xung đột xảy ra ở đây

        // Assert (Lần 2)
        assertTrue(expand.effectRemoved, "Expand (cái cũ) phải bị xóa đi");
        assertFalse(shrink.effectApplied, "Shrink (cái mới) không được áp dụng (vì nó hủy cái cũ)");
        assertTrue(manager.activeEffects.isEmpty(), "Cả 2 effect phải bị hủy và list rỗng");
        }
    }