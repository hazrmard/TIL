package me.iahmed.til;

/**
 * Created by Ibrahim on 2/28/2016.
 */
public class Entry {

    public static int totalEntries = 0;

    public String title;
    public String user;
    public String link;

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public String getLink() {
        return link;
    }

    public Entry(String t, String u, String l) {
        this.title= t;
        this.user =u;
        this.link = l;
        totalEntries++;

    }
}
