package utils;

public class Vector2D {
    public float x;
    public float y;

    // Constructor: create vector with (x, y)
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Copy constructor
    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    // Set new values for this vector
    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Copy values from another vector
    public void setOther(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * Compare two vectors with a small tolerance (avoid floating-point error)
     */
    public boolean equals(Vector2D other) {
        return Math.abs(this.x - other.x) < Constants.EPSILON
                && Math.abs(this.y - other.y) < Constants.EPSILON;
    }

    //In-place operations (change this vector)


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
    public void multiplyScalar(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }


    //Arithmetic (return new vector)

    // Return a + b
    public Vector2D added(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    // Return a - b
    public Vector2D subtracted(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    // Return a * b (component-wise)
    public Vector2D multiplied(Vector2D other) {
        return new Vector2D(this.x * other.x, this.y * other.y);
    }

    public Vector2D multiply(float scalar) { return new Vector2D(this.x * scalar, this.y * scalar); }


    /**
     * Return normalized (unit) vector, same direction, length = 1
     * Important when we only care about direction (not magnitude)
     */
    public Vector2D normalized() {
        float magnitude = length();
        if (magnitude < Constants.EPSILON) return new Vector2D(0, 0);
        return new Vector2D(this.x / magnitude, this.y / magnitude);
    }


    //Geometry / Math

    // Return vector length (magnitude)
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    // Distance between this vector and another
    public float distance(Vector2D other) {
        return this.subtracted(other).length();
    }

    // Dot product (a • b), measure similarity in direction
    public float dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    // Cross product (2D version), positive if b is to the left of a
    public float crossProduct(Vector2D other) {
        return this.x * other.y - this.y * other.x;
    }

    /**
     * Return signed angle between two vectors (in radians)
     * Negative = clockwise, Positive = counterclockwise
     */
    public float angle(Vector2D other) {
        float dot = this.dotProduct(other);
        float firstLength = this.length();
        float secondLength = other.length();

        // avoid zero-length vectors
        if (firstLength < Constants.EPSILON || secondLength < Constants.EPSILON) {
            return 0f;
        }

        float cosAngle = dot / (firstLength * secondLength);
        cosAngle = Math.max(-1f, Math.min(1f, cosAngle)); // clamp to [-1, 1]

        float angle = (float) Math.acos(cosAngle);

        // determine sign using 2D cross product
        if (this.crossProduct(other) < 0) {
            angle = -angle;
        }

        return angle;
    }

    //Vector relationships

    // Check if two vectors are parallel
    public boolean isParallel(Vector2D other) {
        return Math.abs(this.crossProduct(other)) < Constants.EPSILON;
    }

    // Return a vector rotated 90 degree to the left (counterclockwise)
    public Vector2D normalLeft() {
        return new Vector2D(-this.y, this.x);
    }

    // Return a vector rotated 90 degree to the right (clockwise)
    public Vector2D normalRight() {
        return new Vector2D(this.y, -this.x);
    }
}
