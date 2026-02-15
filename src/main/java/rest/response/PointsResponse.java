package rest.response;

import beans.Point;

import java.util.List;

public class PointsResponse {
    public String status;
    public List<Point> message;

    public PointsResponse(String status, List<Point> message) {
        this.status = status;
        this.message = message;
    }
}
