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
    public User login(String username, String password) {
        return dbConnecter.authenticate(username, password);
    }

    @Override
    public User updateUser(String username, String password, String email
        //, String firstname, String surname
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        //user.setFirstname(firstname);
        //user.setSurname(surname);
        return dbConnecter.updateUser(user);
    }

    @Override
    public User newUser(String username, String password, String email
        //, String firstname, String surname
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        //user.setFirstname(firstname);
        //user.setSurname(surname);
        return dbConnecter.newUser(user);
    }

    @Override
    public void logout() {

    }
}
