package com.example.myapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.Databases.Merchant;
import com.example.myapplication.Databases.User;
import com.example.myapplication.R;
import com.example.myapplication.Util.MerchantHttpUtil;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.PrimitiveIterator;


public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText psw;
    private EditText cfrimpsw;
    private EditText phone;
    private Button register;
    private User usr;
    private Merchant merchant;
    private String response;
    private Spinner spinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=findViewById(R.id.email);
        psw=findViewById(R.id.psw);
        cfrimpsw=findViewById(R.id.cfrim_psw);
        register=findViewById(R.id.register_button);
        phone=findViewById(R.id.cfrim_phone);
        spinner=findViewById(R.id.spinner);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usename=email.getText().toString();String pswd=psw.getText().toString();
                String cfrim=cfrimpsw.getText().toString();
                String phones=phone.getText().toString();
                if(spinner.getSelectedItem().toString().equals("用户")) {
                    usr=new User();
                    usr.setId(usename);
                    usr.setPassword(pswd);
                    usr.setPhone(phones);
                    UserHttpUtil userHttpUtil =new UserHttpUtil();
                    if (pswd.equals(cfrim) && usename != "" && pswd != "" && cfrim != "") {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                response = userHttpUtil.CheckRegister(usr);
                                if (response.equals("可以注册")) {
                                    response = userHttpUtil.Register(usr);
                                    if (response.equals("success")) {
                                        Snackbar.make(view, "注册成功,请登录", Snackbar.LENGTH_LONG).show();
                                        //延时操作
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        }, 2000);
                                    } else {
                                        Snackbar.make(view, "注册失败", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(view, "账号重复", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }).start();


                    } else {
                        Snackbar.make(view, "密码与确认密码不一致或者为全空", Snackbar.LENGTH_LONG).show();
                        email.setText("");
                        psw.setText("");
                        cfrimpsw.setText("");
                    }
                }else if(spinner.getSelectedItem().toString().equals("商家")) {
                    merchant=new Merchant();
                    merchant.setMerchant_id(usename);
                    merchant.setMerchant_password(pswd);
                    merchant.setMerchant_phone(phones);
                    MerchantHttpUtil merchantHttpUtil=new MerchantHttpUtil();
                    if (pswd.equals(cfrim) && usename != "" && pswd != "" && cfrim != ""){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                response=merchantHttpUtil.CheckRegister(merchant);
                                if (response.equals("可以注册")){
                                    response=merchantHttpUtil.Register(merchant);
                                    if (response.equals("success")) {
                                        Snackbar.make(view, "注册成功,请登录", Snackbar.LENGTH_LONG).show();
                                        //延时操作
                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        }, 2000);
                                    }else {
                                        Snackbar.make(view, "注册失败", Snackbar.LENGTH_LONG).show();
                                    }
                                }else {
                                    Snackbar.make(view, "账号重复", Snackbar.LENGTH_LONG).show();
                                }

                            }
                        }).start();
                    }else {
                        Snackbar.make(view, "密码与确认密码不一致或者为全空", Snackbar.LENGTH_LONG).show();
                        email.setText("");
                        psw.setText("");
                        cfrimpsw.setText("");
                    }
                }
            }
        });
    }
}