package controllers;

import database.DBConnecter;
import entities.Bookmark;
import entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    public Bookmark addBookmark(JSONObject jsonObject) {
        System.out.println("Json from request: " +jsonObject);

        int user_id = jsonObject.getInt("user_id");
        String bookmark_name = jsonObject.getString("bookmark_name");
        String description = jsonObject.getString("description");
        String url = jsonObject.getString("url");
        String media_name = jsonObject.getString("media_name");
        String status = jsonObject.getString("status");
        int rating = jsonObject.getInt("rating");

        Bookmark bookmark = new Bookmark(user_id,bookmark_name,description,url,media_name,status,rating);

        JSONArray genre = jsonObject.getJSONArray("genre");
        JSONArray tag = jsonObject.getJSONArray("tag");
        List<String> genreList = new ArrayList<>();
        List<String> tagList = new ArrayList<>();
        for(int i=0; i < genre.length(); i++) {
            genreList.add(genre.getString(i));
        }
        for(int i=0; i < tag.length(); i++) {
            tagList.add(tag.getString(i));
        }
        bookmark.setGenres(genreList);
        bookmark.setTags(tagList);

        return dbConnecter.saveBookmark(bookmark);
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
    public User getUser(User user) {
        return dbConnecter.getUser(user);
    }
    @Override
    public Boolean updateUser(User user, String key, String value) {
        return dbConnecter.updateUser(user, key, value);
    }

    @Override
    public User newUser(String username, String password, String email, String firstname, String surname) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setSurname(surname);
        return dbConnecter.newUser(user);
    }
}
