package com.example.myapplication.Util;

import com.example.myapplication.Databases.Food;
import com.example.myapplication.Databases.Merchant;
import com.example.myapplication.Databases.Order;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MerchantHttpUtil {

    String url="http://192.168.0.3:8080";

    String responses;
    //注册验证
    public String Register(Merchant merchant)  {
        Gson gson = new Gson();
        String json = gson.toJson(merchant);
        OkHttpClient client=new OkHttpClient();
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/merchant/insert")
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
    public String CheckRegister(Merchant merchant){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(merchant);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/merchant/checkregister")
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
    public String LoginCheck(Merchant merchant){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(merchant);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/merchant/selectpsw")
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
    //窗口状态修改
    public String Window_Statuschange(String status,String id){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/window/window_status?window_status="+status+"&window_id="+id)
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
    //添加套餐
    public String Food_Insert(String name,String price,String window_id){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/food/insert_food?food_name="+name+"&food_price="+price+"&window_id="+window_id)
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
    //删除套餐
    public String Food_delete(String id){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://192.168.0.3:8080/food/food_delete?food_id="+id)
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
    //更新套餐
    public String Food_update(Food food){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(food);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/food/food_update")
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
    //显示商户所需处理订单
    public  String Merchant_GetOrder(String merchant_id){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/order/merchant_order?merchant_id="+merchant_id)
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
    //更新订单状态
    public String Order_Status(Order order){
        OkHttpClient client= new OkHttpClient();
        Gson gson=new Gson();
        String json=gson.toJson(order);
        RequestBody body=RequestBody.create(json, MediaType.parse("application/json"));
        Request request=new Request.Builder()
                .url(url+"/order/findById")
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
    //查看订单详情
    public String Order_detail(String order_id){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/order_detail/getdetail?order_id="+order_id)
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

    //获取套餐名称
    public String Get_food_name(String food_id){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/food/get_food_name?food_id="+food_id)
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
}
