package de.moritzbruder.bofrost.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by morit on 10.06.2017.
 */
public class User {

    public static class Session {

        private static String loggedInId = "1";

        public static void login (String id) {
            loggedInId = id;
        }

        public static String getLoggedInId() {
            return loggedInId;
        }
    }

    private int id;
    private String name;
    private int points;
    @SerializedName("image")
    private String iconUrl;

    public User (int id, String name, int points, String iconUrl) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}