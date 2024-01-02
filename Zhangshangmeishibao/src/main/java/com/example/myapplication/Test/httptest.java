package com.example.myapplication.Test;


import com.example.myapplication.Databases.Order;
import com.example.myapplication.Databases.User;
import com.example.myapplication.Util.AppUtil;
import com.example.myapplication.Util.MerchantHttpUtil;
import com.example.myapplication.Util.UpateUtil;
import com.example.myapplication.Util.UserHttpUtil;

import okhttp3.OkHttpClient;

public class httptest {


    public static void main(String[] args)  {
        UpateUtil upateUtil=new UpateUtil();
        System.out.println(upateUtil.VersionGet());
    }
}
