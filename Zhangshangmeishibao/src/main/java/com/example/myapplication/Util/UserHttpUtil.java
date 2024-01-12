package com.example.myapplication.Util;

import com.example.myapplication.Databases.Comment;
import com.example.myapplication.Databases.Healthy_diet;
import com.example.myapplication.Databases.Order;
import com.example.myapplication.Databases.Order_detail;
import com.example.myapplication.Databases.User;
import com.example.myapplication.UserCenterFragment.Healthydiet;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserHttpUtil {
    String url="http://121.40.249.214:8080";

    String responses;


    //注册验证
    public String Register(User user)  {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        OkHttpClient client=new OkHttpClient();
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/usr/insert")
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
                .url(url+"/usr/checkregister")
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
                .url(url+"/usr/selectpsw")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                // 在将其转换为字符串之前检查响应是否成功
                responses = response.body().string();
            } else {
                responses = "登录失败"; // 设置适当的消息以表示登录失败
            }

            // 关闭响应体
            if (response.body() != null) {
                response.body().close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
    public String WindowGet(){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/window/windowlist")
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
                .url(url+"/food/get_food?window_id="+window_id)
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
                .url(url+"/usr/edit_user")
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
    //提交订单
    public void Order_submit(Order order){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(order);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/order/insert_order")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //提交详细订单
    public void Order_detail_submit(Order_detail order){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(order);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/order_detail/insert")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取用户订单
    public String Find_Order(String user_id){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/order/select_order?user_id="+user_id)
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

    public String Insert_Text(Healthy_diet healthy_diet){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(healthy_diet);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/healthydiet/insert")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  responses;
    }

    public String Healthydiet_Findall(){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/healthydiet/findall")
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses=response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  responses;
    }


    public String Insert_Comment(Comment comment) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(comment);
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url + "/comment/insert")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses = response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }

    public String Get_Comment(int Healthy_diet_id){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/comment/get_comment?id="+Healthy_diet_id)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses = response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }

    public String FindMyAll(String  username){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/healthydiet/findmyall?username="+username)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            responses = response.body().string();
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responses;
    }
}
