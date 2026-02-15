package rest.json;

import lombok.Getter;

@Getter
public class PointCredentials {
    private String x;
    private String y;
    private String r;

    public void setX(String x) {
        this.x = x.replaceAll(",", ".");
    }

    public void setY(String y) {
        this.y = y.replaceAll(",", ".");
    }

    public void setR(String r) {
        this.r = r.replaceAll(",", ".");
    }
}
