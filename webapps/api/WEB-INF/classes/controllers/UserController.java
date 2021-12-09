package controllers;

import entities.User;

public interface UserController {
    User getUser(int user_id);
    User login(String username, String password);
    boolean updateUser(String accessToken, String ...info);
    void logout();
}
