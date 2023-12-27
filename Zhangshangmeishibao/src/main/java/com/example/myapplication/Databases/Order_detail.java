package com.example.myapplication.Databases;

public class Order_detail {

    private String detail_id;
    private String order_id;
    private String food_id;
    private String food_quality;

    public Order_detail() {
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_quality() {
        return food_quality;
    }

    public void setFood_quality(String food_quality) {
        this.food_quality = food_quality;
    }
}
