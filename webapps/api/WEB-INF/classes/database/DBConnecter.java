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

    public ArrayList<Bookmark> getBookmarks(String user) {
        ArrayList<Bookmark> bookmarks = null;
        try {
            connect();
            bookmarks = new ArrayList<>();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bookmarks;
    }

    public User authenticate(String username, String password) {
        PreparedStatement preparedStatement= null;

        try {
            System.out.println("trying to connect");
            connect();
            System.out.println("Connected!");
            String query = "SELECT * FROM User WHERE username = ? AND password = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println();

            if ( resultSet.isBeforeFirst() ) {
                User user = new User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("username"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
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
