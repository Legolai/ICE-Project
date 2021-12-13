package database;

import entities.Bookmark;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

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

    private void close() throws SQLException {
        this.conn.close();
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

    public User getUser(User user) {
        PreparedStatement preparedStatement = null;

        try {
            connect();

            String query = "SELECT * FROM Favorite_Website_DB.User WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user.getUser_id());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                User tmpUser = new User();
                while (resultSet.next()) {
                    tmpUser.setUser_id(resultSet.getInt("user_id"));
                    tmpUser.setFirstname(resultSet.getString("firstname"));
                    tmpUser.setSurname(resultSet.getString("surname"));
                    tmpUser.setUsername(resultSet.getString("username"));
                    tmpUser.setEmail(resultSet.getString("email"));
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

    public User newUser(User user) {
        try {
            connect();

            String sql = "INSERT INTO User (email, firstname, surname, username, password) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getFirstname());
            pstmt.setString(3, user.getSurname());
            pstmt.setString(4, user.getUsername());
            pstmt.setString(5, user.getPassword());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setUser_id(generatedKeys.getInt(1));
                }

                pstmt.close();
                close();
                return user;
            }

            pstmt.close();
            close();
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Boolean updateUser(User user, String key, String value) {
        try {
            connect();

            String sql = "INSERT INTO User ? " +
                    "VALUES ? WHERE user_id = ? ON DUPLICATE KEY UPDATE ?=?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, key);
            pstmt.setString(2, value);
            pstmt.setInt(3, user.getUser_id());
            pstmt.setString(4, key);
            pstmt.setString(5, value);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                pstmt.close();
                close();
                return true;
            }

            pstmt.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<Bookmark> getBookmarks(User user) {
        PreparedStatement preparedStatement = null;
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        try {
            connect();

            String query = "SELECT * FROM Favorite_Website_DB.Bookmark WHERE user_id = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, user.getUser_id());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Bookmark bookmark = new Bookmark();
                    bookmark.setBookmark_id(resultSet.getInt("bookmark_id"));
                    bookmark.setUser_id(user.getUser_id());
                    bookmark.setDescription(resultSet.getString("description"));
                    bookmark.setUrl(resultSet.getString("url"));
                    bookmark.setName(resultSet.getString("bookmark_name"));
                    bookmark.setStatus(resultSet.getString("status"));
                    bookmark.setRating(resultSet.getInt("rating"));
                    bookmark.setMedia(resultSet.getString("media_name"));

                    String bookmark_join_genre = "SELECT * FROM Favorite_Website_DB.Bookmark_Genre WHERE bookmark_id = ?";
                    String bookmark_join_genre_id = "SELECT * FROM Favorite_Website_DB.Genre WHERE genre_id = ?";
                    getBookmarksGenreOrTag(bookmark, bookmark_join_genre, bookmark_join_genre_id, "genre_id", "genre_name");
                    String bookmark_join_tag = "SELECT * FROM Favorite_Website_DB.Bookmark_Tag WHERE bookmark_id = ?";
                    String bookmark_join_tag_id = "SELECT * FROM Favorite_Website_DB.Tag WHERE tag_id = ?";
                    getBookmarksGenreOrTag(bookmark, bookmark_join_tag, bookmark_join_tag_id, "tag_id", "tag_name");

                    bookmarks.add(bookmark);
                }
            }
            close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return bookmarks;
    }

    private Bookmark getBookmarksGenreOrTag(Bookmark bm, String bookmarkToBeJoined, String bookmark_join_id, String genre_tag_id, String name) {    //genre_tag_id is genre or tag_id
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        try {
            String query2 = bookmarkToBeJoined;
            ps2 = conn.prepareStatement(query2);

            ps2.setInt(1, bm.getBookmark_id());
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.isBeforeFirst()) {
                while (rs2.next()) {
                    String query3 = bookmark_join_id;
                    ps3 = conn.prepareStatement(query3);
                    ps3.setInt(1, rs2.getInt(genre_tag_id));
                    ResultSet rs3 = ps3.executeQuery();

                    List<String> list = new ArrayList<>();

                    while (rs3.next()){
                        list.add(rs3.getString(name));
                    }

                    if (genre_tag_id.equals("genre_id")) {
                        bm.setGenres(list);
                    } else {
                        bm.setTags(list);
                    }
                }
                return bm;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Bookmark saveBookmark(Bookmark bm) {
        String sql = "";
        if (bm.getBookmark_id() > 0) {
            sql = "INSERT INTO Bookmark (bookmark_id, user_id, bookmark_name, description, url, media_name, status, rating) " +
                    "VALUES (?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE bookmark_name=?, description=?, url=?, media_name=?, status=?, rating=?;";
        } else {
            sql = "INSERT INTO Bookmark (user_id, bookmark_name, description, url, media_name, status, rating) " +
                    "VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE bookmark_name=?, description=?, url=?, media_name=?, status=?, rating=?;";
        }

        try {
            connect();
            System.out.println("Creating statement...");
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (bm.getBookmark_id() > 0) {
                pstmt.setInt(1, bm.getBookmark_id());
                pstmt.setInt(2, bm.getUser_id());
                pstmt.setString(3,bm.getName());
                pstmt.setString(4,bm.getDescription());
                pstmt.setString(5,bm.getUrl());
                pstmt.setString(6,bm.getMedia());
                pstmt.setString(7,bm.getStatus());
                pstmt.setInt(8, bm.getRating());
            } else {
                pstmt.setInt(1, bm.getUser_id());
                pstmt.setString(2,bm.getName());
                pstmt.setString(3,bm.getDescription());
                pstmt.setString(4,bm.getUrl());
                pstmt.setString(5,bm.getMedia());
                pstmt.setString(6,bm.getStatus());
                pstmt.setInt(7, bm.getRating());
            }

            // Checks if the media exists in database, if not insert it
            String query = "SELECT * FROM Favorite_Website_DB.Media WHERE media_name = ?";
            PreparedStatement pstmtMedia = conn.prepareStatement(query);
            pstmtMedia.setString(1, bm.getMedia());
            ResultSet rs = pstmtMedia.executeQuery();
            if (!rs.isBeforeFirst()) {
                String sqlmedia = "INSERT INTO Media (media_name) VALUES (?) ON DUPLICATE KEY media_name = media_name";
                pstmtMedia = conn.prepareStatement(sqlmedia, Statement.RETURN_GENERATED_KEYS);
                pstmtMedia.setString(1,bm.getMedia());
                pstmtMedia.executeBatch();
            }
            pstmtMedia.close();

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    bm.setBookmark_id(generatedKeys.getInt(1));
                }
                // this part checks in Genre and Tag db, if not found, insert
                checkAndInsertGenreTag(bm);
                pstmt.close();
                close();
                return bm;
            }
            // this part checks in Genre and Tag db, if not found, insert
            checkAndInsertGenreTag(bm);
            pstmt.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void checkAndInsertGenreTag(Bookmark bm) {
        String genreQuery = "SELECT * FROM Favorite_Website_DB.Genre WHERE genre_name = ?";
        String genreSQL = "INSERT INTO Genre (genre_name) VALUES (?)";
        ArrayList<Integer> genreIDs =  checkAndInsertGenreTag(bm, genreQuery, genreSQL, "genre_id");
        String tagQuery = "SELECT * FROM Favorite_Website_DB.Tag WHERE tag_name = ?";
        String tagSQL = "INSERT INTO Tag (tag_name) VALUES (?)";
        ArrayList<Integer> tagIDs = checkAndInsertGenreTag(bm, tagQuery, tagSQL, "tag_id");

        // this is for the 3rd table for relations between bookmark and genre/tag
        String genreJoinQuery = "SELECT * FROM Favorite_Website_DB.Bookmark_Genre WHERE bookmark_id = ?, genre_id = ?";
        String genreJoinSQL = "INSERT INTO Bookmark_Genre (bookmark_id, genre_id) VALUES (?,?)";
        checkJoinTable(bm.getBookmark_id(),genreIDs, genreJoinQuery, genreJoinSQL, "genre_id");
        String tagJoinQuery = "SELECT * FROM Favorite_Website_DB.Bookmark_Tag WHERE bookmark_id = ?, tag_id = ?";
        String tagJoinSQL = "INSERT INTO Bookmark_Tag (bookmark_id, tag_id) VALUES (?,?)";
        checkJoinTable(bm.getBookmark_id(),genreIDs, tagJoinQuery, tagJoinSQL, "tag_id");
    }
    private ArrayList<Integer> checkAndInsertGenreTag(Bookmark bm, String query, String sql, String IDtype) {
        ArrayList<Integer> genreTagIDs = new ArrayList<>();
        try {
            List<String> genreOrTag = new ArrayList<>();
            if (IDtype.equals("genre_id")) {
                genreOrTag.addAll(bm.getGenres());
            } else {
                genreOrTag.addAll(bm.getTags());
            }

            int genreTagID = 0;
            for (String searchInsert : genreOrTag) {
                // Checks if the genre or tag exists in database
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, searchInsert);
                ResultSet rs = pstmt.executeQuery();
                // if it doesn't exist in the DB, insert it
                if (!rs.isBeforeFirst()) {
                    pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1,searchInsert);

                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        ResultSet generatedKeys = pstmt.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            genreTagID = generatedKeys.getInt(1);
                        }
                    }
                } else {
                    genreTagID = rs.getInt(IDtype);
                }
                genreTagIDs.add(genreTagID);
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genreTagIDs;
    }
    private void checkJoinTable(int bookmarkID, ArrayList<Integer> genreTagIDs, String query, String sql, String IDtype) {
        try {
            for (int searchInsert : genreTagIDs) {
                // Checks if the genre or tag exists in database
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, searchInsert);
                ResultSet rs = pstmt.executeQuery();
                // if it doesn't exist in the DB, insert it
                if (!rs.isBeforeFirst()) {
                    pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1,bookmarkID);
                    pstmt.setInt(2,searchInsert);
                }
                pstmt.executeBatch();
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteUserORBookmark(int id, String name, String userOrBookmark) {
        String sql = "";        //id is user or bookmark id, and name is username or bookmark_name
        if (userOrBookmark.toLowerCase(Locale.ROOT).equals("user")) {
            sql = "DELETE * FROM Favorite_Website_DB.User WHERE user_id = ? AND username = ?";
        } else {
            sql = "DELETE * FROM Favorite_Website_DB.Bookmark WHERE bookmark_id = ? AND bookmark_name = ?";
        }

        try {
            connect();
            System.out.println("Creating statement...");
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, id);
            pstmt.setString(2, name);

            pstmt.executeBatch();
            pstmt.close();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
