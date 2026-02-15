package rest.response;

public class ApiResponse {
    public String status;
    public String message;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
