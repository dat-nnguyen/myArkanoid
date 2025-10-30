package PowerUpSystem;

import UI.SceneManager;
import audio.SoundManager;
import entities.Ball;
import entities.Brick;
import entities.Paddle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Manages all power-ups in the game.
 * FIXED: Prevents stacking of same-type power-ups.
 */
public class PowerUpManager {

    private final ArrayList<PowerUp> activePowerUps;
    private final Map<PowerUpType, ActiveEffect> activeEffects; // KEY CHANGE: Map by type
    private final PowerUpContext context;
    private final Random random;

    private final ObservableMap<PowerUpType, DoubleProperty> activePowerUpCredits;

    private double spawnChance = 0.4;//spawn chance

    // Track original values to prevent stacking
    private Integer originalPaddleWidth = null;
    private Double originalBallSpeed = null;

    public PowerUpManager(Ball ball, Paddle paddle, ArrayList<Ball> ballList) {
        this.activePowerUps = new ArrayList<>();
        this.activeEffects = new HashMap<>(); // Type → Effect mapping
        this.context = new PowerUpContext(ball, paddle, ballList);
        this.random = new Random();
        this.activePowerUpCredits = FXCollections.observableHashMap();
    }

    public void trySpawnPowerUp(Brick brick) {
        if (random.nextDouble() < spawnChance) {
            PowerUpType type = getRandomPowerUpType();
            PowerUp powerUp = PowerUpFactory.createPowerUp(
                    brick.getPositionX() + brick.getWidth() / 2.0 - 15,
                    brick.getPositionY(),
                    type
            );
            powerUp.setActive(true);
            activePowerUps.add(powerUp);
            System.out.println("⭐ Power-up spawned: " + type);
        }
    }

    private PowerUpType getRandomPowerUpType() {
        PowerUpType[] types = {
                PowerUpType.EXPAND_PADDLE,
                PowerUpType.SHRINK_PADDLE,
                PowerUpType.SLOW_BALL,
                PowerUpType.MULTI_BALL
        };
        return types[random.nextInt(types.length)];
    }

    public void update(double deltaTime) {
        // Update falling power-ups
        Iterator<PowerUp> powerUpIterator = activePowerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update(deltaTime);

            if (!powerUp.isActive()) {
                powerUpIterator.remove();
            }
        }

        // Update active effects
        Iterator<Map.Entry<PowerUpType, ActiveEffect>> effectIterator =
                activeEffects.entrySet().iterator();

        while (effectIterator.hasNext()) {
            Map.Entry<PowerUpType, ActiveEffect> entry = effectIterator.next();
            ActiveEffect effect = entry.getValue();

            effect.elapsedTime += deltaTime;

            if (effect.elapsedTime >= effect.duration) {
                effect.powerUp.removeEffect(context);
                effectIterator.remove();

                // Clear original values when effect ends
                if (entry.getKey() == PowerUpType.EXPAND_PADDLE ||
                        entry.getKey() == PowerUpType.SHRINK_PADDLE) {
                    originalPaddleWidth = null;
                }
                if (entry.getKey() == PowerUpType.SLOW_BALL) {
                    originalBallSpeed = null;
                }

                System.out.println("⏱️ Power-up effect ended: " + entry.getKey());
            }
        }

        Iterator<ObservableMap.Entry<PowerUpType, DoubleProperty>> iterator = activePowerUpCredits.entrySet().iterator();
        while (iterator.hasNext()) {
            ObservableMap.Entry<PowerUpType, DoubleProperty> entry = iterator.next();
            DoubleProperty timer = entry.getValue();
            double newTime = timer.get() - deltaTime;
            if (newTime <= 0) {
                iterator.remove();
            } else {
                timer.set(newTime);
            }
        }
    }

    public void checkCollision(Paddle paddle) {
        for (PowerUp powerUp : activePowerUps) {
            if (!powerUp.isActive() || powerUp.isCollected()) continue;

            boolean collisionX = powerUp.getPositionX() + powerUp.getWidth() >= paddle.getPositionX() &&
                    paddle.getPositionX() + paddle.getWidth() >= powerUp.getPositionX();

            boolean collisionY = powerUp.getPositionY() + powerUp.getHeight() >= paddle.getPositionY() &&
                    paddle.getPositionY() + paddle.getHeight() >= powerUp.getPositionY();

            if (collisionX && collisionY) {
                SceneManager.getInstance().getSoundManager().play("power");
                collectPowerUp(powerUp);
                updateCredits(powerUp.getType(), powerUp.getType().getDuration());
            }
        }
    }

    /**
     * Collect power-up with smart stacking prevention.
     */
    private void collectPowerUp(PowerUp powerUp) {
        powerUp.setCollected(true);
        powerUp.setActive(false);

        PowerUpType type = powerUp.getType();

        // === HANDLE PADDLE SIZE POWER-UPS ===
        if (type == PowerUpType.EXPAND_PADDLE || type == PowerUpType.SHRINK_PADDLE) {
            handlePaddleSizePowerUp(powerUp, type);
        }
        // === HANDLE BALL SPEED POWER-UPS ===
        else if (type == PowerUpType.SLOW_BALL) {
            handleBallSpeedPowerUp(powerUp, type);
        }
        // === HANDLE INSTANT POWER-UPS (Multi-ball) ===
        else if (type == PowerUpType.MULTI_BALL) {
            powerUp.applyEffect(context);
            System.out.println("✨ Power-up collected: " + type);
        }
    }

    /**
     * Handle paddle size power-ups (Expand/Shrink).
     * Logic:
     * - Expand + Expand = Refresh timer (no stacking)
     * - Shrink + Shrink = Refresh timer
     * - Expand + Shrink = Cancel out (return to normal)
     */
    private void handlePaddleSizePowerUp(PowerUp powerUp, PowerUpType newType) {
        PowerUpType oppositeType = (newType == PowerUpType.EXPAND_PADDLE)
                ? PowerUpType.SHRINK_PADDLE
                : PowerUpType.EXPAND_PADDLE;

        // Check if opposite effect is active
        if (activeEffects.containsKey(oppositeType)) {
            // Remove opposite effect → Return to normal
            ActiveEffect oppositeEffect = activeEffects.remove(oppositeType);
            oppositeEffect.powerUp.removeEffect(context);
            originalPaddleWidth = null;
            System.out.println("⚖️ " + newType + " canceled out " + oppositeType);
            return;
        }

        // Check if same effect is already active
        if (activeEffects.containsKey(newType)) {
            // Refresh timer (don't apply again)
            ActiveEffect existingEffect = activeEffects.get(newType);
            existingEffect.elapsedTime = 0; // Reset timer
            System.out.println("🔄 " + newType + " effect refreshed (timer reset)");
            return;
        }

        // Save original width ONLY on first application
        if (originalPaddleWidth == null) {
            originalPaddleWidth = context.getPaddle().getWidth();
        }

        // Apply new effect
        powerUp.applyEffect(context);
        activeEffects.put(newType, new ActiveEffect(powerUp, powerUp.getType().getDuration()));
        System.out.println("✨ Power-up collected: " + newType);
    }

    /**
     * Handle ball speed power-ups.
     */
    private void handleBallSpeedPowerUp(PowerUp powerUp, PowerUpType newType) {
        // Check if same effect is already active
        if (activeEffects.containsKey(newType)) {
            // Refresh timer
            ActiveEffect existingEffect = activeEffects.get(newType);
            existingEffect.elapsedTime = 0;
            System.out.println("🔄 " + newType + " effect refreshed (timer reset)");
            return;
        }

        // Save original speed ONLY on first application
        if (originalBallSpeed == null) {
            originalBallSpeed = context.getBall().getSpeed();
        }

        // Apply new effect
        powerUp.applyEffect(context);
        activeEffects.put(newType, new ActiveEffect(powerUp, powerUp.getType().getDuration()));
        System.out.println("✨ Power-up collected: " + newType);
    }

    public void render(GraphicsContext gc) {
        for (PowerUp powerUp : activePowerUps) {
            powerUp.render(gc);
        }
    }

    public void reset() {
        for (ActiveEffect effect : activeEffects.values()) {
            effect.powerUp.removeEffect(context);
        }
        activePowerUps.clear();
        activeEffects.clear();
        originalPaddleWidth = null;
        originalBallSpeed = null;
        activePowerUpCredits.clear();
    }

    private static class ActiveEffect {
        PowerUp powerUp;
        double duration;
        double elapsedTime;

        ActiveEffect(PowerUp powerUp, double duration) {
            this.powerUp = powerUp;
            this.duration = duration;
            this.elapsedTime = 0;
        }
    }

    private void updateCredits(PowerUpType type, double duration) {
        if (activePowerUpCredits.containsKey(type)) {
            activePowerUpCredits.get(type).set(duration);
        } else {
            activePowerUpCredits.put(type, new SimpleDoubleProperty(duration));
        }
    }

    // Getters
    public double getSpawnChance() { return spawnChance; }
    public void setSpawnChance(double spawnChance) {
        this.spawnChance = Math.max(0.0, Math.min(1.0, spawnChance));
    }
    public ArrayList<PowerUp> getActivePowerUps() { return activePowerUps; }
    public ObservableMap<PowerUpType, DoubleProperty> getActivePowerUpCredits() {
        return activePowerUpCredits;
    }
}