package inspector;

import exception.FormatException;

public class UserDataInspector {
    public static void inspectData(String username, String password) throws FormatException {
        inspectUsername(username);
        inspectUserPassword(password);
    }

    private static void inspectUsername(String username) throws FormatException {
        if (username == null || username.isEmpty()) {
            throw new FormatException("username is incorrect");
        }
    }

    private static void inspectUserPassword(String password) throws FormatException {
        if (password == null || password.isEmpty()) {
            throw new FormatException("password is incorrect");
        }
    }
}
