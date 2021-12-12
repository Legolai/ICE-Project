package controllers;

import entities.User;

public interface UserController {
    User login(String username, String password);
    Boolean updateUser(User user, String key, String value);
    User newUser(String username, String password, String email, String firstname, String surname);
    void logout();
}
