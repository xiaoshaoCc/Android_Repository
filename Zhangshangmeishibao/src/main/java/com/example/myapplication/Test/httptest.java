package com.example.myapplication.Test;


import com.example.myapplication.Util.MerchantHttpUtil;

public class httptest {


    public static void main(String[] args)  {
        MerchantHttpUtil mhtl=new MerchantHttpUtil();
        System.out.println(mhtl.Window_Statuschange("打开","HY1_1"));
    }
}
