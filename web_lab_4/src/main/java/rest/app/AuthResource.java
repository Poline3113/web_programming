package rest.app;

import exception.FormatException;
import inspector.UserDataInspector;
import jakarta.servlet.http.HttpSession;
import rest.json.LoginCredentials;
import rest.json.RegisterCredentials;
import rest.response.ApiResponse;
import rest.response.SessionStatus;
import service.AuthService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private AuthService authService;

    @GET
    @Path("/session-status")
    public Response getSessionStatus(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("currentUser") != null) {
            return Response.ok(new SessionStatus(true)).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new SessionStatus(false))
                    .build();
        }
    }



    @POST
    @Path("/login")
    public Response login(LoginCredentials credentials, @Context HttpServletRequest request) {
        try {
            UserDataInspector.inspectData(credentials.getUsername(), credentials.getPassword());
        } catch (FormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse("error", e.getMessage()))
                    .build();
        }
        if (authService.login(credentials.getUsername(), credentials.getPassword())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", credentials.getUsername());


            request.getSession(true).setAttribute("currentUser", credentials.getUsername());
            return Response.ok(new ApiResponse("success", "Login successful")).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ApiResponse("error", "Invalid credentials"))
                .build();
    }

    @POST
    @Path("/register")
    public Response register(RegisterCredentials credentials) {
        try {
            UserDataInspector.inspectData(credentials.getUsername(), credentials.getPassword());
        } catch (FormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse("error", e.getMessage()))
                    .build();
        }
        boolean registered = authService.register(credentials.getUsername(), credentials.getPassword());
        if (registered) {
            return Response.ok(new ApiResponse("success", "registration successful")).build();
        }
        return Response.status(Response.Status.CONFLICT)
                .entity(new ApiResponse("error", "user is already exists"))
                .build();
    }

    @DELETE
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request) {
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Response.ok(new ApiResponse("success", "logged out")).build();
    }
}