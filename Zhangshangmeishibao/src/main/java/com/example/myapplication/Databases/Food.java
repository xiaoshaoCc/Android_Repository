package com.example.myapplication.Databases;

public class Food {

    private String food_id;
    private String food_name;
    private String food_img;
    private double food_price;
    private String window_id;

    public Food() {
    }

    public Food(String food_id, String food_name, String food_img, double food_price, String window_id) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_img = food_img;
        this.food_price = food_price;
        this.window_id = window_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_img() {
        return food_img;
    }

    public void setFood_img(String food_img) {
        this.food_img = food_img;
    }

    public double getFood_price() {
        return food_price;
    }

    public void setFood_price(double food_price) {
        this.food_price = food_price;
    }

    public String getWindow_id() {
        return window_id;
    }

    public void setWindow_id(String window_id) {
        this.window_id = window_id;
    }

}
