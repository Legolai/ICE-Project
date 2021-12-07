package controllers;

import entities.Bookmark;

import java.util.ArrayList;

public interface BookmarkController {
    ArrayList<Bookmark> getAll(String accessToken);
    void addBookmark(String accessToken, String ...info);
    void removeBookmark(String accessToken, String ...info);
    void updateBookmark(String accessToken, String ...info);
}
