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
    public ArrayList<Bookmark> getAll(String accessToken) {
        return null;
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
