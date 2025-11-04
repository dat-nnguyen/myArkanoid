package PowerUpSystem;

import UI.SceneManager;
import core.GameLoop;
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
 *
 * Responsibilities:
 * - Spawn power-ups from destroyed bricks
 * - Update falling power-ups
 * - Handle collision with paddle
 * - Manage active timed effects
 * - Resolve conflicts between opposite power-ups
 * - Manage UI credits display
 */
public class PowerUpManager {

    // ===== COLLECTIONS =====
    private final ArrayList<PowerUp> activePowerUps;
    private final Map<PowerUpType, ActiveEffect> activeEffects;
    private final ObservableMap<PowerUpType, DoubleProperty> activePowerUpCredits;

    // ===== CONTEXT & UTILS =====
    private final PowerUpContext context;
    private final Random random;

    // ===== CONFIGURATION =====
    private double spawnChance = 1.0;

    // ===== CONSTRUCTOR =====

    public PowerUpManager(entities.Ball ball, Paddle paddle, ArrayList<entities.Ball> ballList) {
        this.activePowerUps = new ArrayList<>();
        this.activeEffects = new HashMap<>();
        this.context = new PowerUpContext(ball, paddle, ballList);
        this.random = new Random();
        this.activePowerUpCredits = FXCollections.observableHashMap();
    }

    /**
     * Set GameLoop reference (called after PowerUpManager creation).
     */
    public void setGameLoop(GameLoop gameLoop) {
        context.setGameLoop(gameLoop);
    }

    // ===============================================================
    // SPAWNING POWER-UPS
    // ===============================================================

    /**
     * Try to spawn a power-up from a destroyed brick.
     */
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

    /**
     * Get a random power-up type with equal probability.
     */
    private PowerUpType getRandomPowerUpType() {
        PowerUpType[] allTypes = {
                PowerUpType.EXPAND_PADDLE,
                PowerUpType.SHRINK_PADDLE,
                PowerUpType.SLOW_BALL,
                PowerUpType.MULTI_BALL,
                PowerUpType.FAST_BALL,
                PowerUpType.EXTRA_LIFE,
                // PowerUpType.SUDDEN_DEATH, // Commented out: too harsh
                PowerUpType.SCORE_MULTIPLIER,
                PowerUpType.SLOW_MOTION
        };
        return allTypes[random.nextInt(allTypes.length)];
    }

    // ===============================================================
    // UPDATE LOOP
    // ===============================================================

    /**
     * Update all power-ups (falling and active effects).
     */
    public void update(double deltaTime) {
        updateFallingPowerUps(deltaTime);
        updateActiveEffects(deltaTime);
        updateUICredits(deltaTime);
    }

    /**
     * Update falling power-ups.
     */
    private void updateFallingPowerUps(double deltaTime) {
        Iterator<PowerUp> powerUpIterator = activePowerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            powerUp.update(deltaTime);

            if (!powerUp.isActive()) {
                powerUpIterator.remove();
            }
        }
    }

    /**
     * Update active timed effects (countdown and removal).
     */
    private void updateActiveEffects(double deltaTime) {
        Iterator<Map.Entry<PowerUpType, ActiveEffect>> effectIterator =
                activeEffects.entrySet().iterator();

        while (effectIterator.hasNext()) {
            Map.Entry<PowerUpType, ActiveEffect> entry = effectIterator.next();
            ActiveEffect effect = entry.getValue();

            effect.elapsedTime += deltaTime;

            if (effect.elapsedTime >= effect.duration) {
                effect.powerUp.removeEffect(context);
                effectIterator.remove();
                System.out.println("⏱️ Power-up effect ended: " + entry.getKey());
            }
        }
    }

    /**
     * Update UI credits (countdown timers).
     */
    private void updateUICredits(double deltaTime) {
        Iterator<ObservableMap.Entry<PowerUpType, DoubleProperty>> iterator =
                activePowerUpCredits.entrySet().iterator();
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

    // ===============================================================
    // COLLISION & COLLECTION
    // ===============================================================

    /**
     * Check collision between falling power-ups and paddle.
     */
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

                // Show UI timer for timed power-ups only
                if (powerUp instanceof TimedPowerUp) {
                    updatePowerUpCredit(powerUp.getType(), powerUp.getType().getDuration());
                }
            }
        }
    }

    /**
     * Collect a power-up and apply its effect.
     */
    private void collectPowerUp(PowerUp powerUp) {
        powerUp.setCollected(true);
        powerUp.setActive(false);

        PowerUpType type = powerUp.getType();

        // Instant power-ups: apply immediately
        if (powerUp instanceof InstantPowerUp) {
            powerUp.applyEffect(context);
            System.out.println("✨ Instant power-up collected: " + type);
            return;
        }

        // Timed power-ups: check for conflicts
        if (powerUp instanceof TimedPowerUp) {
            handleTimedPowerUp((TimedPowerUp) powerUp, type);
        }
    }

    /**
     * Handle timed power-up with conflict resolution.
     */
    private void handleTimedPowerUp(TimedPowerUp powerUp, PowerUpType newType) {
        PowerUpType oppositeType = getOppositeType(newType);

        // Check for opposite effect (cancel out)
        if (oppositeType != null && activeEffects.containsKey(oppositeType)) {
            ActiveEffect oppositeEffect = activeEffects.remove(oppositeType);
            oppositeEffect.powerUp.removeEffect(context);
            System.out.println("⚖️ " + newType + " canceled out " + oppositeType);
            return;
        }

        // Check for same effect (refresh timer)
        if (activeEffects.containsKey(newType)) {
            ActiveEffect existingEffect = activeEffects.get(newType);
            existingEffect.elapsedTime = 0;
            System.out.println("🔄 " + newType + " effect refreshed (timer reset)");
            return;
        }

        // Apply new effect
        powerUp.applyEffect(context);
        activeEffects.put(newType, new ActiveEffect(powerUp, powerUp.getType().getDuration()));
        System.out.println("✨ Timed power-up collected: " + newType);
    }

    /**
     * Get the opposite power-up type for conflict resolution.
     * Returns null if no opposite exists.
     */
    private PowerUpType getOppositeType(PowerUpType type) {
        switch (type) {
            case EXPAND_PADDLE: return PowerUpType.SHRINK_PADDLE;
            case SHRINK_PADDLE: return PowerUpType.EXPAND_PADDLE;
            case FAST_BALL: return PowerUpType.SLOW_BALL;
            case SLOW_BALL: return PowerUpType.FAST_BALL;
            default: return null; // No opposite
        }
    }

    // ===============================================================
    // RENDERING
    // ===============================================================

    /**
     * Render all active falling power-ups.
     */
    public void render(GraphicsContext gc) {
        for (PowerUp powerUp : activePowerUps) {
            powerUp.render(gc);
        }
    }

    // ===============================================================
    // RESET
    // ===============================================================

    /**
     * Reset all power-ups (called when game restarts or level changes).
     */
    public void reset() {
        // Remove all active effects
        for (ActiveEffect effect : activeEffects.values()) {
            effect.powerUp.removeEffect(context);
        }

        activePowerUps.clear();
        activeEffects.clear();
        activePowerUpCredits.clear();

        // Reset time scale (in case SlowMotion was active)
        if (context.getGameLoop() != null) {
            context.getGameLoop().setTimeScale(PowerUpConfig.NORMAL_TIME_SCALE);
        }
    }

    // ===============================================================
    // UI CREDIT MANAGEMENT
    // ===============================================================

    /**
     * Update or create a UI credit timer for a power-up.
     */
    private void updatePowerUpCredit(PowerUpType type, double duration) {
        if (activePowerUpCredits.containsKey(type)) {
            activePowerUpCredits.get(type).set(duration);
        } else {
            activePowerUpCredits.put(type, new SimpleDoubleProperty(duration));
        }
    }

    // ===============================================================
    // ACTIVE EFFECT TRACKER
    // ===============================================================

    /**
     * Tracks an active timed power-up effect.
     */
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

    // ===============================================================
    // GETTERS & SETTERS
    // ===============================================================

    public double getSpawnChance() {
        return spawnChance;
    }

    public void setSpawnChance(double spawnChance) {
        this.spawnChance = Math.max(0.0, Math.min(1.0, spawnChance));
    }

    public ArrayList<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public ObservableMap<PowerUpType, DoubleProperty> getActivePowerUpCredits() {
        return activePowerUpCredits;
    }
}