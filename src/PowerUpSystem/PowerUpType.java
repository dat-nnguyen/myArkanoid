package PowerUpSystem;

public enum PowerUpType {

    NONE(0, null, null),
    EXPAND_PADDLE(10.0, "/images/powerUps/expandPaddle.png", "/images/powerUps/effects/paddleExpanded.png"),
    SHRINK_PADDLE(8.0, "/images/powerUps/shrinkPaddle.png", "/images/powerUps/effects/paddleShrank.png"),
    SLOW_BALL(12.0, "/images/powerUps/slowBall.png", null),
    MULTI_BALL(0, "/images/powerUps/multiBall.png", null); // No duration (instant)

    private final double duration; // Duration in seconds (0 = instant/permanent)
    private final String texturePath; // Icon when falling
    private final String effectTexturePath; // Texture when effect is active (paddle only)

    PowerUpType(double duration, String texturePath, String effectTexturePath) {
        this.duration = duration;
        this.texturePath = texturePath;
        this.effectTexturePath = effectTexturePath;
    }

    public double getDuration() { return duration; }
    public String getTexturePath() { return texturePath; }
    public String getEffectTexturePath() { return effectTexturePath; }
    public boolean hasEffectTexture() { return effectTexturePath != null; }
}