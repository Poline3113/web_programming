package rest.response;

public class SessionStatus {
    public boolean authenticated;

    public SessionStatus(boolean authenticated) {
        this.authenticated = authenticated;
    }
}