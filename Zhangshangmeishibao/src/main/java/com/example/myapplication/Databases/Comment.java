package com.example.myapplication.Databases;

public class Comment {

    private int id;
    private String comment;
    private String upload_time;
    private String username;
    private int healthy_diet_id;

    public Comment(String comment, String upload_time, String username, int healthy_diet) {
        this.comment = comment;
        this.upload_time = upload_time;
        this.username = username;
        this.healthy_diet_id = healthy_diet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHealthy_diet_id() {
        return healthy_diet_id;
    }

    public void setHealthy_diet_id(int healthy_diet_id) {
        this.healthy_diet_id = healthy_diet_id;
    }
}
