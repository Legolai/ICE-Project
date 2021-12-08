package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bookmark {
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

}
