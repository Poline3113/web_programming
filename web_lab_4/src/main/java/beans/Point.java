package beans;

import exception.FormatException;
import inspector.PointDataInspector;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "points")
@NamedQueries({
        @NamedQuery(name = "Point.deleteByOwner",
                query = "DELETE FROM Point p WHERE p.owner = :owner"),
        @NamedQuery(name = "Point.deleteByUsername",
                query = "DELETE FROM Point p WHERE p.owner.username = :username"),
        @NamedQuery(name = "Point.findByOwner",
                query = "SELECT p FROM Point p WHERE p.owner = :owner"),
        @NamedQuery(name = "Point.findByUsername",
                query = "SELECT p FROM Point p WHERE p.owner.username = :username"),
        @NamedQuery(name = "Point.findAll",
                query = "SELECT p FROM Point p")
})
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

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "username")
    private User owner;


    public Point(String x, String y, String r) throws FormatException {
        this.x = x;
        this.y = y;
        this.r = r;
        checkHit();
    }

    private void checkHit() throws FormatException {

        PointDataInspector.inspectData(x, y, r);

        final BigDecimal x = new BigDecimal(this.x);
        final BigDecimal y = new BigDecimal(this.y);
        final float r = Float.parseFloat(this.r);

        boolean inCircle = x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) >= 0 &&
                x.multiply(x).add(y.multiply(y)).compareTo(BigDecimal.valueOf(r * r / 4)) <= 0;

        boolean inTriangle = x.compareTo(BigDecimal.ZERO) >= 0 && y.compareTo(BigDecimal.ZERO) <= 0 &&
                y.compareTo(x.subtract(BigDecimal.valueOf(r / 2))) >= 0;

        boolean inRectangle = x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) <= 0 &&
                x.compareTo(BigDecimal.valueOf(-r)) >= 0 && y.compareTo(BigDecimal.valueOf(-r / 2)) >= 0;

        this.hit = inCircle || inTriangle || inRectangle;
        this.message = this.hit ? "hit" : "miss";

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

    public User getOwner() {
        return owner;
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

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
