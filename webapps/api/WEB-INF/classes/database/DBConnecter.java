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

    public void saveUser(User user) {
        String sql = "";
        if (user.getUser_id() > 0) {
            sql = "INSERT INTO User (user_id, email, firstname, surname, username, password) " +
                    "VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE email=?, firstname=?, surname=?, username=?, password=?;";
        } else {
            sql = "INSERT INTO User (email, firstname, surname, username, password) " +
                    "VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE email=?, firstname=?, surname=?, username=?, password=?;";
        }
        try {
            connect();
            System.out.println("Creating statement...");
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (user.getUser_id() > 0) {
                pstmt.setInt(1, user.getUser_id());
                pstmt.setString(2, user.getEmail());
                pstmt.setString(3, user.getFirstname());
                pstmt.setString(4, user.getSurname());
                pstmt.setString(5, user.getUsername());
                pstmt.setString(6, user.getPassword());
            } else {
                pstmt.setString(1, user.getEmail());
                pstmt.setString(2, user.getFirstname());
                pstmt.setString(3, user.getSurname());
                pstmt.setString(4, user.getUsername());
                pstmt.setString(5, user.getPassword());
            }

            pstmt.executeBatch();
            pstmt.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Bookmark> getBookmarks(User user) {
        PreparedStatement preparedStatement = null;
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
/*
                    String s2 = "SELECT * FROM Favorite_Website_DB.Bookmark_Genre WHERE bookmark_id = ?";
                    String s3 = "SELECT * FROM Favorite_Website_DB.Genre WHERE genre_id = ?";
                    getBookmarksGenreOrTag(bookmark, s2, s3, "genre_id", "genre_name");
                    s2 = "SELECT * FROM Favorite_Website_DB.Bookmark_Tag WHERE bookmark_id = ?";
                    s3 = "SELECT * FROM Favorite_Website_DB.Tag WHERE tag_id = ?";
                    getBookmarksGenreOrTag(bookmark, s2, s3, "tag_id", "tag_name");
*/
                    bookmarks.add(bookmark);
                }
                return bookmarks;
            }
            close();
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
                    if (id.equals("genre_id")) {
                        bm.setGenres(list);
                    } else {
                        bm.setTags(list);
                    }
                } while (rs2.next());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bm;
    }

    public void saveBookmark(Bookmark bm) {
        String sql = "";
        if (bm.getBookmark_id() > 0) {
            sql = "INSERT INTO Bookmark (bookmark_id, user_id, bookmark_name, url, media_name, status, rating) " +
                    "VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE bookmark_name=?, url=?, media_name=?, status=?, rating=?;";
        } else {
            sql = "INSERT INTO Bookmark (user_id, bookmark_name, url, media_name, status, rating) " +
                    "VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE bookmark_name=?, url=?, media_name=?, status=?, rating=?;";
        }

        try {
            connect();
            System.out.println("Creating statement...");
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (bm.getBookmark_id() > 0) {
                pstmt.setInt(1, bm.getBookmark_id());
                pstmt.setInt(2, bm.getUser_id());
                pstmt.setString(3,bm.getName());
                pstmt.setString(4,bm.getUrl());
                pstmt.setString(5,bm.getMedia());
                pstmt.setString(6,bm.getStatus());
                pstmt.setInt(7, bm.getRating());
            } else {
                pstmt.setInt(1, bm.getUser_id());
                pstmt.setString(2,bm.getName());
                pstmt.setString(3,bm.getUrl());
                pstmt.setString(4,bm.getMedia());
                pstmt.setString(5,bm.getStatus());
                pstmt.setInt(6, bm.getRating());
            }

            // Checks if the media exists in database, if not insert it
            String query = "SELECT * FROM Favorite_Website_DB.Media WHERE media_name = ?";
            PreparedStatement pstmtMedia = conn.prepareStatement(query);
            pstmtMedia.setString(1, bm.getMedia());
            ResultSet rs = pstmtMedia.executeQuery();
            if (rs.next() == false) {
                String sqlmedia = "INSERT INTO Media (media_name) VALUES (?) ON DUPLICATE KEY media_name = media_name";
                pstmtMedia = conn.prepareStatement(sqlmedia, Statement.RETURN_GENERATED_KEYS);
                pstmtMedia.setString(1,bm.getMedia());
            } else {
                //do nothing
            }

            pstmtMedia.executeBatch();
            pstmtMedia.close();
            pstmt.executeBatch();
            pstmt.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(int user_id) {
        PreparedStatement preparedStatement = null;

        try {
            connect();

            String query = "SELECT * FROM Favorite_Website_DB.User WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user_id);

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
