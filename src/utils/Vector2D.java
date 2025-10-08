package utils;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setOther(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * read the comment in class Constants to understand equals method
     */
    public boolean equals(Vector2D other) {
        return Math.abs(this.x - other.x) < Constants.EPSILON
                && Math.abs(this.y - other.y) < Constants.EPSILON;
    }

    /**
     *
     * in-place math operations
     */
    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void sub(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void multiply(Vector2D other) {
        this.x *= other.x;
        this.y *= other.y;
    }

    /**
     * math operations when we want new vector
     * arithmetic
     */
    public Vector2D added(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtracted(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D multiplied(Vector2D other) {
        return new Vector2D(this.x * other.x, this.y * other.y);
    }

    /**
     * What is normalized Vector 2D?
     * This is unit vector with magnitude == 1
     * But the most important here is direction of this vector, not magnitude
     */
    public Vector2D normalized(Vector2D other) {
        float magnitude = (float) Math.sqrt(this.x * this.x + this.y * this.y);

        if (magnitude < Constants.EPSILON) {
            return new Vector2D(0, 0);
        }
        // when magnitude < EPSILON - meaning it's very small, we return 0 to avoid dividing by 0
        return new Vector2D(this.x / magnitude, this.y / magnitude);
    }

    // Geometry operations
    public float length(){
        return (float) Math.sqrt(x * x + y * y);
    }

    public float distance(Vector2D other) {
        return this.subtracted(other).length();
    }
    public float dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }
    public float crossProduct(Vector2D other) {
        return this.x * other.y - this.y * other.x;
    }

    public float angle(Vector2D other) {
        float dot =  this.dotProduct(other);
        float firstLength = this.length();
        float secondLength = other.length();

        if(firstLength < Constants.EPSILON || secondLength < Constants.EPSILON){
            return 0f;
        }// length of vector is too small so we will assume that no angle here

        float cosAngle = dot / (firstLength * secondLength);
        cosAngle = Math.max(-1f, Math.min(1f, cosAngle));

        float angle = (float) Math.toDegrees(Math.acos(cosAngle));

        if(this.crossProduct(other) < 0){
            angle = -angle;
        }
        return angle;
    }
}
