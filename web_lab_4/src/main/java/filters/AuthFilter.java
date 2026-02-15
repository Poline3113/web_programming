package filters;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.servlet.http.HttpServletRequest;
import rest.response.ApiResponse;

import java.util.function.Predicate;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest httpServletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        var path = requestContext.getUriInfo().getPath();
        var method = requestContext.getMethod();

        Predicate<String> isAuthPath = p -> p.contains("/auth/login") || p.contains("/auth/register");

        if (isAuthPath.test(path) && "POST".equals(method)) {
            return;
        }

        var session = httpServletRequest != null ? httpServletRequest.getSession(false) : null;
        var userId = session != null ? (Long) session.getAttribute("currentUser") : null;

        if (session == null || userId == null || userId <= 0) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(new ApiResponse("error", "Authentication required"))
                            .build()
            );
        }
    }
}