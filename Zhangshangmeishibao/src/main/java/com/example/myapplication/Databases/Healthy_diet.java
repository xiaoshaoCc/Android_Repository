package com.example.myapplication.Databases;

import java.io.Serializable;

public class Healthy_diet implements Serializable {
    private int id;
    private String title;
    private String healthtext;
    private String uploadtime;
    private String username;

    public Healthy_diet() {
    }

    public Healthy_diet(String title, String healthtext, String uploadtime, String username) {
        this.title = title;
        this.healthtext = healthtext;
        this.uploadtime = uploadtime;
        this.username = username;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHealthtext() {
        return healthtext;
    }

    public void setHealthtext(String healthtext) {
        this.healthtext = healthtext;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
