package database;

import entities.Bookmark;
import entities.User;

import java.sql.*;
import java.util.ArrayList;

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
        PreparedStatement preparedStatement= null;
        try {
            connect();

            String query = "SELECT * FROM Favorite_Website_DB.Bookmark WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user.getUser_id());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                ArrayList<Bookmark> bookmarks = new ArrayList<>();
                while (resultSet.next()) {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setBookmark_id(resultSet.getInt("bookmark_id"));
                    bookmark.setUser_id(user.getUser_id());
                    bookmark.setUrl(resultSet.getString("url"));
                    bookmark.setName(resultSet.getString("bookmark_name"));
                    bookmark.setStatus(resultSet.getString("status"));
                    bookmark.setRating(resultSet.getInt("rating"));
                    bookmarks.add(bookmark);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public User authenticate(String username, String password) {
        PreparedStatement preparedStatement= null;

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
