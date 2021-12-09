package database;

import entities.Bookmark;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnecter {

    private Connection conn;
    private static final String DB_URL = "jdbc:mysql://db:3306/Favorite_Website_DB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public DBConnecter() {
        conn = null;
    }

    private void connect() throws SQLException {
        this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public ArrayList<Bookmark> getBookmarks(User user) {
        PreparedStatement preparedStatement = null;
        try {
            connect();

            String query = "SELECT * FROM Favorite_Website_DB.Bookmark WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user.getUser_id());

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Bookmark> bookmarks = new ArrayList<>();

            if (resultSet.next() == false) {
                return null;
            } else {
                do {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setBookmark_id(resultSet.getInt("bookmark_id"));
                    bookmark.setUser_id(user.getUser_id());
                    bookmark.setUrl(resultSet.getString("url"));
                    bookmark.setName(resultSet.getString("bookmark_name"));
                    bookmark.setMedia(resultSet.getString("media_name"));
                    bookmark.setStatus(resultSet.getString("status"));
                    bookmark.setRating(resultSet.getInt("rating"));

                    String s2 = "SELECT * FROM Favorite_Website_DB.Bookmark_Genre WHERE bookmark_id = ?";
                    String s3 = "SELECT * FROM Favorite_Website_DB.Genre WHERE genre_id = ?";
                    getBookmarksGenreOrTag(bookmark, s2, s3, "genre_id", "genre_name");
                    s2 = "SELECT * FROM Favorite_Website_DB.Bookmark_Tag WHERE bookmark_id = ?";
                    s3 = "SELECT * FROM Favorite_Website_DB.Tag WHERE tag_id = ?";
                    getBookmarksGenreOrTag(bookmark, s2, s3, "tag_id", "tag_name");

                    bookmarks.add(bookmark);
                } while (resultSet.next());
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Bookmark getBookmarksGenreOrTag(Bookmark bm, String s2, String s3, String id, String name) {
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        try {
            String query2 = s2;
            ps2 = conn.prepareStatement(query2);
            ps2.setInt(1, bm.getBookmark_id());
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next() == false) {
                return null;
            } else {
                do {
                    String query3 = s3;
                    ps3 = conn.prepareStatement(query3);
                    ps3.setInt(1, rs2.getInt(id));
                    ResultSet rs3 = ps3.executeQuery();
                    List<String> list = new ArrayList<>();

                    do {
                        list.add(rs3.getString(name));
                    } while (rs2.next());
                    bm.setGenres(list);
                } while (rs2.next());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bm;
    }


    public User authenticate(String username, String password) {
        PreparedStatement preparedStatement = null;

        try {
            connect();

            String query = "SELECT * FROM Favorite_Website_DB.User WHERE username = ? AND password = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                User user = new User();
                while (resultSet.next()) {
                    user.setUser_id(resultSet.getInt("user_id"));
                    user.setFirstname(resultSet.getString("firstname"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                }
                close();
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("failed!");
        }
        return null;
    }

    private void close() throws SQLException {
        this.conn.close();
    }
}
