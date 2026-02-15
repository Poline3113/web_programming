package rest.json;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginCredentials {
    private String username;
    private String password;
}
