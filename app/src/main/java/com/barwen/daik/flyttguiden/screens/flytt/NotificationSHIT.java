package com.barwen.daik.flyttguiden.screens.flytt;

/**
 * Created by daik on 2018-02-08.
 */

public class NotificationSHIT {

    private int id;
    private String title;
    private String longText;
    private long time;

    public NotificationSHIT(int id, String title, String longText, long time) {
        this.id = id;
        this.title = title;
        this.longText = longText;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLongText() {
        return longText;
    }
}
