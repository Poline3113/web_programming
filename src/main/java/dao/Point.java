package dao;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "points")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x_value")
    private String x;

    @Column(name = "y_value")
    private String y;

    @Column(name = "r_value")
    private String r;

    @Column(name = "hit")
    private boolean hit;

    @Column(name = "message")
    private String message;

    public Point(String x, String y, String r, boolean hit, String message) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.message = message;
    }

    public Point() {
    }

    public Long getId() {
        return id;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getR() {
        return r;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean getHit() {
        return hit;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setR(String r) {
        this.r = r;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}