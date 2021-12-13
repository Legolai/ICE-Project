package controllers;

import entities.User;
import org.json.JSONObject;

public interface UserController {
    User login(String username, String password);
    User getUser(User user);
    Boolean updateUser(User user, String key, String value);
    User newUser(String username, String password, String email, String firstname, String surname);
    boolean deleteUser(JSONObject jsonObject);
}
