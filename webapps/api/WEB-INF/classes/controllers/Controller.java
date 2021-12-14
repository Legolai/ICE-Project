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

        return saveBookmark(jsonObject, 0);
    }
    @Override
    public Bookmark updateBookmark(JSONObject jsonObject) {
        System.out.println("Json from request: " +jsonObject);

        return saveBookmark(jsonObject, jsonObject.getInt("bookmark_id"));
    }
    private Bookmark saveBookmark(JSONObject jsonObject, int bookmarkID) {
        int user_id = jsonObject.getInt("user_id");
        String bookmark_name = jsonObject.getString("bookmark_name");
        String description = jsonObject.getString("description");
        String url = jsonObject.getString("url");
        String media_name = jsonObject.getString("media_name");
        String status = jsonObject.getString("status");
        int rating = jsonObject.getInt("rating");

        Bookmark bookmark = new Bookmark(user_id,bookmark_name,description,url,media_name,status,rating);
        // the below will be overwritten with the correct despite it being 0 when adding new
        bookmark.setBookmark_id(bookmarkID);

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
    public boolean removeBookmark(JSONObject jsonObject) {
        System.out.println("Json from request: " +jsonObject);

        int bookmark_id = jsonObject.getInt("bookmark_id");
        String bookmark_name = jsonObject.getString("bookmark_name");

        return dbConnecter.deleteUserORBookmark(bookmark_id, bookmark_name, "bookmark");
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
    @Override
    public boolean deleteUser(JSONObject jsonObject) {
        System.out.println("Json from request: " +jsonObject);

        int user_id = jsonObject.getInt("user_id");
        String username = jsonObject.getString("username");

        return dbConnecter.deleteUserORBookmark(user_id, username, "user");
    }
}
