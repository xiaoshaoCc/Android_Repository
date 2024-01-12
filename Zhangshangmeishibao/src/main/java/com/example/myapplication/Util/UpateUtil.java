package com.example.myapplication.Util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpateUtil {

    String url="http://121.40.249.214:8080";
    String responses;

    public String VersionGet(){
        OkHttpClient client= new OkHttpClient();
        Request request=new Request.Builder()
                .url(url+"/find")
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
