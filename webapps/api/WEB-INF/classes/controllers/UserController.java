package controllers;

public interface UserController {
    String login(String username, String password);
    boolean updateUser(String accessToken, String ...info);
    void logout();
}
