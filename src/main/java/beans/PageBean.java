package beans;

import dao.Point;
import inspector.DataInspector;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ejb.EJB;

@SessionScoped
@Named("daoBean")
public class PageBean implements Serializable {
    @EJB
    private DataBean dataBean;

    private Float x;
    private String y;
    private String r;

    @PostConstruct
    public void init() {
        this.x = 0f;
        this.y = "0";
        this.r = "1";
    }

    public List<Point> getPoints() {
        return dataBean.loadPoints();
    }

    public Float getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getR() {
        return r;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setR() {
        this.r = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("rValue");
    }

    public boolean rActive(String value) {
        return r != null && r.equals(value);
    }

    public String getPointsAsJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(dataBean.loadPoints()).replaceAll("\n", "");
    }

    public void checkHit() {
        try {
            DataInspector.inspectData(x, y, r);

            final float x = this.x;
            final BigDecimal y = new BigDecimal(this.y);
            final float r = Float.parseFloat(this.r);
            final boolean hit;

            boolean inCircle = x >= 0 && y.compareTo(BigDecimal.ZERO) >= 0 &&
                    BigDecimal.valueOf(x * x).add(y.multiply(y)).compareTo(BigDecimal.valueOf(r * r)) <= 0;

            boolean inTriangle = x <= 0 && y.compareTo(BigDecimal.ZERO) >= 0 &&
                    y.compareTo(BigDecimal.valueOf((.5 * x) + (.5 * r))) <= 0;

            boolean inRectangle = x >= 0 && y.compareTo(BigDecimal.ZERO) <= 0 &&
                    x <= r / 2 && y.compareTo(BigDecimal.valueOf(-r)) >= 0;

            hit = inCircle || inTriangle || inRectangle;

            String message = hit ? "hit" : "miss";
            dataBean.savePoint(
                    String.valueOf(this.x),
                    this.y,
                    this.r,
                    hit,
                    message
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}