package controllers;

import entities.Bookmark;
import entities.User;
import org.json.JSONObject;

import java.util.ArrayList;

public interface BookmarkController {
    ArrayList<Bookmark> getAll(User user);
    Bookmark addBookmark(JSONObject jsonObject, int user_id);
    Bookmark updateBookmark(JSONObject jsonObject, int user_id);
    boolean removeBookmark(JSONObject jsonObject);
    ArrayList<String> getUsersGenresOrTags(User user, String genreTagSelector);
}
