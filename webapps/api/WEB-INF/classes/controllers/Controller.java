package controllers;

import database.DBConnecter;
import entities.Bookmark;
import entities.User;

import java.util.ArrayList;

public class Controller implements UserController, BookmarkController{

    private final DBConnecter dbConnecter;

    public Controller(){
        this.dbConnecter = new DBConnecter();
    }

    @Override
    public ArrayList<Bookmark> getAll(User user) {
        return dbConnecter.getBookmarks(user);
    }

    @Override
    public void addBookmark(String accessToken, String... info) {

    }

    @Override
    public void removeBookmark(String accessToken, String... info) {

    }

    @Override
    public void updateBookmark(String accessToken, String... info) {

    }

    @Override
    public User getUser(int user_id) {
        return dbConnecter.getUser(user_id);
    }

    @Override
    public User login(String username, String password) {
        return dbConnecter.authenticate(username, password);
    }

    @Override
    public boolean updateUser(String accessToken, String... info) {
        return false;
    }

    @Override
    public void logout() {

    }
}
