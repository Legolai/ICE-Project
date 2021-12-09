package controllers;

import entities.Bookmark;
import entities.User;

import java.util.ArrayList;

public interface BookmarkController {
    ArrayList<Bookmark> getAll(User user);
    void addBookmark(String accessToken, String ...info);
    void removeBookmark(String accessToken, String ...info);
    void updateBookmark(String accessToken, String ...info);
}
