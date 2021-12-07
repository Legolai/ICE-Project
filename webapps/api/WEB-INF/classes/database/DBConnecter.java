package database;

import entities.Bookmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConnecter {

    private Connection conn;

    public DBConnecter() {
        this.conn = null;
    }

    private void connect() throws SQLException {
        this.conn = DriverManager.getConnection("http://localhost:3307", "user", "user");
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

    private void close() throws SQLException {
        this.conn.close();
    }
}
