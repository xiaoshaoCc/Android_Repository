package com.example.myapplication.Test;


import com.example.myapplication.Databases.Order;
import com.example.myapplication.Databases.User;
import com.example.myapplication.Util.MerchantHttpUtil;
import com.example.myapplication.Util.UserHttpUtil;

import okhttp3.OkHttpClient;

public class httptest {


    public static void main(String[] args)  {
        UserHttpUtil userHttpUtil=new UserHttpUtil();
        User usr=new User();
        usr.setId("123");
        usr.setPassword("123");
        System.out.println(userHttpUtil.LoginCheck(usr));
    }
}
