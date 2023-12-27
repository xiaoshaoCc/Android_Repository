package com.example.myapplication.Databases;

public class Merchant {
    private String merchant_id;
    private String merchant_password;
    private String merchant_phone;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_password() {
        return merchant_password;
    }

    public void setMerchant_password(String merchant_password) {
        this.merchant_password = merchant_password;
    }

    public String getMerchant_phone() {
        return merchant_phone;
    }

    public void setMerchant_phone(String merchant_phone) {
        this.merchant_phone = merchant_phone;
    }
}
