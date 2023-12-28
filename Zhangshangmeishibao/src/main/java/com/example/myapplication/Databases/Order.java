package com.example.myapplication.Databases;

public class Order {
    private String order_id;
    private String user_id;
    private String order_date;
    private Double order_price;
    private String order_status;
    private String window_id;

    public Order(String order_id, String user_id, String order_date, Double order_price, String order_status, String window_id) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.order_date = order_date;
        this.order_price = order_price;
        this.order_status = order_status;
        this.window_id = window_id;
    }

    public String getWindow_id() {
        return window_id;
    }

    public void setWindow_id(String window_id) {
        this.window_id = window_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public Double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Double order_price) {
        this.order_price = order_price;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
