package com.example.myapplication.Test;


import com.example.myapplication.Databases.Order;
import com.example.myapplication.Util.MerchantHttpUtil;

import okhttp3.OkHttpClient;

public class httptest {


    public static void main(String[] args)  {
        MerchantHttpUtil mhtl=new MerchantHttpUtil();
        Order order=new Order("20231228232722123","123","2023-12-28 23:27:22",44.00,"已完成","HY1_1");
        System.out.println(mhtl.Order_Status(order));
    }
}
