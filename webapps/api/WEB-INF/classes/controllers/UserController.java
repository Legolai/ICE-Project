package controllers;

import entities.User;

public interface UserController {
    User login(String username, String password);
    User updateUser(String username, String password, String email);
    void logout();
}
