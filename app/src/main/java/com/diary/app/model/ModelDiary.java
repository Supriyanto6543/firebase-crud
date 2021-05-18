package com.diary.app.model;

public class ModelDiary {

    private String title;
    private String description;
    private String date;
    private String userid;

    public ModelDiary() {
    }

    public ModelDiary(String title, String description, String date, String userid) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
