package controllers;

import com.mysql.cj.protocol.x.Notice;
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
    public Bookmark addBookmark(JSONObject jsonObject,int user_id) {
        System.out.println("Json from request: " +jsonObject);

        return saveBookmark(jsonObject, 0, user_id);
    }
    @Override
    public Bookmark updateBookmark(JSONObject jsonObject, int user_id) {
        System.out.println("UpdateBookmark:\nJson from request: " +jsonObject);
        return saveBookmark(jsonObject, jsonObject.getInt("bookmark_id"), user_id);
    }

    private Bookmark saveBookmark(JSONObject jsonObject, int bookmarkID, int user_id) {
        System.out.println("getting data");
        String bookmark_name = jsonObject.getString("bookmark_name");
        String description = jsonObject.getString("description");
        String url = jsonObject.getString("url");
        String media_name = jsonObject.getString("media_name");
        String status = jsonObject.getString("status");
        int rating = jsonObject.getInt("rating");

        Bookmark bookmark = new Bookmark(user_id,bookmark_name,description,url,media_name,status,rating);
        // the below will be overwritten with the correct despite it being 0 when adding new
        bookmark.setBookmark_id(bookmarkID);

        System.out.println("getting genres");
        JSONArray genre = jsonObject.getJSONArray("genre");
        if (!genre.isEmpty()) {
            List<String> genreList = new ArrayList<>();
            for(int i=0; i < genre.length(); i++) {
                genreList.add(genre.getString(i));
            }
            bookmark.setGenres(genreList);
        }
        System.out.println("getting tags");
        JSONArray tag = jsonObject.getJSONArray("tag");
        if (!tag.isEmpty()) {
            List<String> tagList = new ArrayList<>();
            for(int i=0; i < tag.length(); i++) {
                tagList.add(tag.getString(i));
            }
            bookmark.setTags(tagList);
        }
        
        System.out.println("going to db");
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
    public ArrayList<String> getUsersGenresOrTags(User user, String genreTagSelector) {
        return dbConnecter.getGenresOrTags(user, genreTagSelector);
    }



    @Override
    public User login(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        return dbConnecter.authenticate(username, password);
    }
    @Override
    public User getUser(User user) {
        return dbConnecter.getUser(user);
    }
    @Override
    public Boolean updateUser(User user, JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String firstname = jsonObject.getString("firstname");
        String surname = jsonObject.getString("surname");
        String email = jsonObject.getString("email");
        if (username.equals(user.getUsername()) && firstname.equals(user.getFirstname())
                && surname.equals(user.getSurname()) && email.equals(user.getEmail())) {
            return false; //no changes made when pushing update button
        }

        User userdb = new User();
        user.copyUserNoPass(user, username, firstname, surname, email);

        return dbConnecter.updateUser(user);
    }
    @Override
    public User newUser(JSONObject jsonObject) {
        User user = new User();
        user.setUsername(jsonObject.getString("username"));
        user.setPassword(jsonObject.getString("password"));
        user.setEmail(jsonObject.getString("email"));
        user.setFirstname(jsonObject.getString("firstname"));
        user.setSurname(jsonObject.getString("surname"));
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
