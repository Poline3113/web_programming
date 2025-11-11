package dao;

import java.math.BigDecimal;

public class Point {
    private final float x;
    private final BigDecimal y;
    private final float r;
    private final boolean hit;

    public Point(float x, BigDecimal y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = checkHit();
    }

    public float getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public boolean isHit() {
        return hit;
    }

    private boolean checkHit() {
        if (x <= 0 && y.compareTo(BigDecimal.ZERO) >= 0) {
            return BigDecimal.valueOf(x * x).add(y.multiply(y)).compareTo(BigDecimal.valueOf(r * r)) <= 0;
        }

        if (x <= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            return x >= -r &&
                   y.compareTo(BigDecimal.valueOf(-r / 2)) >= 0;
        }

        if (x >= 0 && y.compareTo(BigDecimal.ZERO) <= 0) {
            return y.compareTo(BigDecimal.valueOf(2 * x - r)) >= 0;
        }

        return false;
    }
}
