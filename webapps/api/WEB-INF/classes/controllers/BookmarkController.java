package controllers;

import entities.Bookmark;
import entities.User;
import org.json.JSONObject;

import java.util.ArrayList;

public interface BookmarkController {
    ArrayList<Bookmark> getAll(User user);
    Bookmark addBookmark(JSONObject jsonObject);
    void removeBookmark(String accessToken, String ...info);
    void updateBookmark(String accessToken, String ...info);
}
