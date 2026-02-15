package rest.app;

import beans.Point;
import beans.User;
import dao.PointData;
import dao.UserData;
import exception.FormatException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import rest.json.PointCredentials;
import rest.response.ApiResponse;
import rest.response.PointsResponse;

import java.util.List;

@Path("/main")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MainResource {
    @Inject
    private UserData userData;
    @Inject
    private PointData pointData;

    @POST
    public Response addPoint(PointCredentials data, @Context HttpServletRequest request) {

        String username = (String) request.getSession().getAttribute("currentUser");
        if (username == null || username.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            Point point = new Point(data.getX(), data.getY(), data.getR());
            var test = userData.findByUsername(username);

            if (!test.isPresent()) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            User owner = test.get();
            point.setOwner(owner);
            pointData.save(point);

            System.out.println("Point saved successfully!");

            return Response.ok()
                    .entity(new ApiResponse("success", "point added successfully"))
                    .build();
        } catch (FormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse("error", "cannot add point: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    public Response getAllPoints(@Context HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("currentUser");
        if (username == null || username.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ApiResponse("error", "unauthorized"))
                    .build();
        }

        List<Point> points = pointData.findAllPoints();
        return Response.ok()
                .entity(new PointsResponse("success", points))
                .build();
    }

    @DELETE
    @Path("/clear")
    public Response clearAllPoints(@Context HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("currentUser");
        if (username == null || username.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        pointData.clearByUsername(username);
        return Response.ok().entity(new ApiResponse("success", "all points cleared"))
                .build();
    }
}