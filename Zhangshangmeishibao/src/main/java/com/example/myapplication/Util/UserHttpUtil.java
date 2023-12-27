package com.example.myapplication.Util;

import com.example.myapplication.Databases.User;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserHttpUtil {

    String responses;
    //注册验证
    public String Register(User user)  {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        OkHttpClient client=new OkHttpClient();
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url("http://192.168.0.3:8080/usr/insert")
                .post(body)
                .build();

        try {
           Response response = client.newCall(request).execute();
           responses=response.body().string();
           response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
    //重复值验证
    public String CheckRegister(User usr){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(usr);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url("http://192.168.0.3:8080/usr/checkregister")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
    //登录验证
    public String LoginCheck(User usr){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(usr);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url("http://192.168.0.3:8080/usr/selectpsw")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
    public String WindowGet(){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://192.168.0.3:8080/window/windowlist")
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
    public String FoodGet(String window_id){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://192.168.0.3:8080/food/get_food?window_id="+window_id)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
    //修改密码
    public String User_Edit(User usr){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(usr);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url("http://192.168.0.3:8080/usr/edit_user")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
}
