package service;

import dao.UserData;
import beans.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import utils.HashGenerator;

import java.util.Optional;

@Stateless
public class AuthService {

    @Inject
    private UserData userData;

    public boolean login(String username, String password) {
        System.out.println("Trying to login user: " + username);
        Optional<User> userOpt = userData.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("User found, stored hash: " + user.getPassword());
            boolean result = HashGenerator.verifyPassword(password, user.getPassword());
            System.out.println("Password verification result: " + result);
            return result;
        } else {
            System.out.println("User not found");
            return false;
        }
    }

    public boolean register(String username, String password) {
        if (userData.checkUserIfExists(username)) {
            return false;
        }

        String hashedPassword = HashGenerator.generate(password);
        User newUser = new User(username, hashedPassword);
        userData.saveUser(newUser);

        return true;
    }
}