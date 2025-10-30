package PowerUpSystem;

public enum PowerUpType {

    NONE(0, null, null, "NONE"),
    EXPAND_PADDLE(10.0, "/images/powerUps/expandPaddle.png", "/images/powerUps/effects/paddleExpanded.png", "BIG PADDLE"),
    SHRINK_PADDLE(8.0, "/images/powerUps/shrinkPaddle.png", "/images/powerUps/effects/paddleShrank.png", "MINI PADDLE"),
    SLOW_BALL(12.0, "/images/powerUps/slowBall.png", null, "SLOW BALL"),
    MULTI_BALL(0, "/images/powerUps/multiBall.png", null, "X3 BALL"); // No duration (instant)

    private final double duration; // Duration in seconds (0 = instant/permanent)
    private final String texturePath; // Icon when falling
    private final String effectTexturePath; // Texture when effect is active (paddle only)
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
    public String getName() {
        return name;
    }
}