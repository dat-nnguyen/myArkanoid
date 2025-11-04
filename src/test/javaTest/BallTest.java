import audio.SoundManager;
import entities.Ball;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Constants;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FakeSoundManager chỉ để dùng cho test.
 * không làm gì cả, chỉ để tránh lỗi NullPointerException.
 */

public class BallTest {

    class FakeSoundManager extends SoundManager {
        public FakeSoundManager() {
            super();
        }
        @Override
        public void play(String soundName){
            //nothing
        }
    }

    private Ball ball;
    private SoundManager fakeSoundManager;
    @BeforeEach
    void setUp(){
        fakeSoundManager = new FakeSoundManager();
        ball = new Ball(fakeSoundManager, null);

    }
    @Test
    public void testConstructor_InitializesMainBall(){
        assertTrue(ball.isMainBall(), "Bóng mới phải là bóng chính");
        assertEquals(Constants.BALL_LIVES, ball.getLives(), "Bóng mới phải có đủ mạng");
    }

    @Test
    void testReset_ReturnsValueToDefault(){
        // 1. Arrange
        // Làm xáo trộn các giá trị
        ball.setSpeed(1000);
        ball.setLives(0);
        ball.setPositionX(50);
        ball.play = true;

        // 2. Act
        ball.reset();

        // 3. Assert
        // Kiểm tra mọi thứ đã về default
        assertEquals(Constants.BALL_SPEED, ball.getSpeed());
        assertEquals(Constants.BALL_LIVES, ball.getLives());
        assertEquals(Constants.BALL_START_POSITION_X, ball.getPositionX());
        assertFalse(ball.play, "play phải là false sau khi reset");
    }

    @Test
    void testUpdate_CallsMove_WhenAliveAndPlaying() {
        // 1. Arrange
        ball.reset(); // về vị trí ban đầu
        ball.setLives(1);
        ball.play = true;

        ball.setDirectionY(-1.0);
        double initialY= ball.getPositionY();

        // 2. Act
        ball.update(1.0); // Giả lập 1 giây

        // 3. Assert
        // Nếu move() được gọi, vị trí Y phải thay đổi (vì directionY = 1.0)
        assertNotEquals(initialY, ball.getPositionY(), "Vị trí Y được thay đổi khi update và play=true");
    }

    @Test
    void testUpdate_DoesNotMove_WhenNotPlaying() {
        // 1. Arrange
        ball.reset();
        ball.setLives(1);
        ball.play = false; // không chơi bóng
        double initialY = ball.getPositionY();

        // 2. Act
        ball.update(1.0);

        // 3. Assert
        // Vị trí Y không được được thay đổi
        assertEquals(initialY, ball.getPositionY(), "Vị trí Y không được thay đổi khi play=false");
    }

    @Test
    void testBallFalls_WhenIsMainBall_DoesNotLoseLife() {
        // 1. Arrange
        ball.reset();
        ball.setMainBall(true); // Đặt là bóng chính
        ball.play = true;
        ball.setLives(Constants.BALL_LIVES);
        // Đặt vị trí ngay trên mép rơi
        ball.setPositionY(Constants.MARGIN_HEIGHT + Constants.PLAY_SCREEN_HEIGHT + 30);
        ball.setDirectionY(1.0); // bay xuống

        // 2. Act
        ball.move(1.0); // gọi move để test logic rơi

        // 3. Assert

        assertFalse(ball.play, "play phải là false khi bóng chính rơi");
        assertEquals(Constants.BALL_LIVES, ball.getLives(), "Mạng (lives) KHÔNG được giảm khi BÓNG CHÍNH rơi");
    }

    //Va chạm

    @Test
    void testMove_CollidesWithTopWall_ReversesDirectionY() {
        // 1. Arrange
        ball.reset();
        ball.setPositionY(Constants.MARGIN_HEIGHT - 1); // Đặt bóng ngay trên tường trên
        ball.setDirectionY(-1.0); // Bay lên
        double initialDirY = ball.getDirectionY();

        // 2. Act
        ball.move(1.0);

        // 3. Assert
        assertTrue(ball.getDirectionY() > 0, "Hướng Y (directionY) phải đảo ngược (thành số dương)");
        assertEquals(-initialDirY, ball.getDirectionY()); // Phải là giá trị đối
    }

    //Tính toán góc

    @Test
    void testSetDirection_WhenHitCenter() {
        // 1. Arrange
        // (ballCenterX, paddleCenterX, paddleWidth)
        double paddleWidth = 100;
        double paddleCenterX = 150;
        double ballCenterX = 150; // Bóng đập trúng tâm

        // 2. Act
        ball.setDirection(ballCenterX, paddleCenterX, paddleWidth);

        // 3. Assert
        // Đập trúng tâm (normalize = 0), góc nảy là PI/2 (90 độ)
        // directionX = cos(PI/2) = 0
        // directionY = -sin(PI/2) = -1
        assertEquals(0.0, ball.getDirectionX(), 0.001); // 0.001 là sai số cho phép
        assertEquals(-1.0, ball.getDirectionY(), 0.001);
    }
}