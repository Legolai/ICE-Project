package controllers;

import entities.User;
import org.json.JSONObject;

public interface UserController {
    User login(JSONObject jsonObject);
    User getUser(User user);
    Boolean updateUser(User user, JSONObject jsonObject);
    User newUser(JSONObject jsonObject);
    boolean deleteUser(JSONObject jsonObject);
}
