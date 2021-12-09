package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bookmark {
    private int bookmark_id;
    private int user_id;
    private String name;
    private String description;
    private String url;
    private String media;
    private String status;
    private List<String> genres;
    private List<String> tags;
    private int rating;


    public Bookmark() {
        name = "";
        description = "";
        url = "";
        media = "";
        status = "";
        genres = new ArrayList<>();
        tags = new ArrayList<>();
        rating = 0;
    }

    public void fillBookmark(String info) {
        String[] splitted = info.split(";");

        name = splitted[0];
        description = splitted[1];
        url = splitted[2];
        media = splitted[3];
        status = splitted[4];
        genres = Arrays.asList(splitted[5].split(","));
        tags = Arrays.asList(splitted[6].split(","));
        rating = Integer.parseInt(splitted[7]);
    }

    public String bookmarkToString() {
        String res = "";

        res += name+";";
        res += description+";";
        res += url+";";
        res += media+";";
        res += status+";";
        res += listToString(genres)+";";
        res += listToString(tags)+";";
        res += rating;

        return res;
    }

    private String listToString(List<String> list) {
        if (list == null) {
            return "";
        }
        String res = "";
        for(String s : list) {
            res += s+",";
        }
        res = res.substring(0,res.length()-1);

        return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getBookmark_id() {
        return bookmark_id;
    }

    public void setBookmark_id(int bookmark_id) {
        this.bookmark_id = bookmark_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
