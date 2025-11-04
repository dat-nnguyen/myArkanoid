package PowerUpSystem;

public enum PowerUpType {

    NONE(0, null, null, "NONE"),
    EXPAND_PADDLE(10.0, "/images/powerUps/expandPaddle.png", "/images/powerUps/effects/paddleExpanded.png", "BIG PADDLE"),
    SHRINK_PADDLE(8.0, "/images/powerUps/shrinkPaddle.png", "/images/powerUps/effects/paddleShrank.png", "MINI PADDLE"),
    SLOW_BALL(12.0, "/images/powerUps/slowBall.png", null, "SLOW BALL"),
    MULTI_BALL(0, "/images/powerUps/multiBall.png", null, "X3 BALL"),

    // === NEW POWER-UPS ===
    FAST_BALL(10.0, "/images/powerUps/fastBall.png", null, "FAST BALL"),
    EXTRA_LIFE(0, "/images/powerUps/extraLife.png", null, "EXTRA LIFE"),
    SUDDEN_DEATH(0, "/images/powerUps/suddenDeath.png", null, "SUDDEN DEATH"),
    SCORE_MULTIPLIER(0, "/images/powerUps/scoreMultiplier.png", null, "X2 SCORE"),
    SLOW_MOTION(8.0, "/images/powerUps/slowMotion.png", null, "SLOW MOTION");

    private final double duration;
    private final String texturePath;
    private final String effectTexturePath;
    private final String name;

    PowerUpType(double duration, String texturePath, String effectTexturePath, String name) {
        this.duration = duration;
        this.texturePath = texturePath;
        this.effectTexturePath = effectTexturePath;
        this.name = name;
    }

    public double getDuration() { return duration; }
    public String getTexturePath() { return texturePath; }
    public String getEffectTexturePath() { return effectTexturePath; }
    public boolean hasEffectTexture() { return effectTexturePath != null; }
    public String getName() { return name; }
}