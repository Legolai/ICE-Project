package controllers;

import database.DBConnecter;
import entities.Bookmark;

import java.util.ArrayList;

public class Controller implements UserController, BookmarkController{

    private DBConnecter conn;

    public Controller(){
        this.conn = new DBConnecter();
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
    public String login(String username, String password) {
        return null;
    }

    @Override
    public boolean updateUser(String accessToken, String... info) {
        return false;
    }

    @Override
    public void logout() {

    }
}
