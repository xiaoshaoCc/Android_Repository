package com.example.myapplication.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.myapplication.MerchantCenterFragment.MerchantMainActivity;
import com.example.myapplication.UserCenterFragment.UserMainActivity;
import com.example.myapplication.Databases.Merchant;
import com.example.myapplication.Databases.User;
import com.example.myapplication.R;
import com.example.myapplication.Util.AppUtil;
import com.example.myapplication.Util.MerchantHttpUtil;
import com.example.myapplication.Util.UpateUtil;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private LocationClient mLocationClient;  //定位客户端
    private TextView register;
    private TextView forget;
    private EditText email;
    private EditText psw;
    private Button login;
    private Animation anim;
    public static String address;
    private User user;
    private Merchant merchant;
    private MerchantHttpUtil merchantHttpUtil;
    private UserHttpUtil userHttpUtil;
    private Spinner spinner;
    String response,version;
    //点击两次退出按钮后退出
    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //如果没有定位权限，动态请求用户允许使用该权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            requestLocation();
        }

        String versionName= AppUtil.getAppVersionName(getApplicationContext());


        //应用程序更新
        new Thread(new Runnable() {
            @Override
            public void run() {
                UpateUtil upateUtil=new UpateUtil();
                version=upateUtil.VersionGet();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!version.equals(versionName)) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("更新");
                            builder.setMessage("应用程序已更新到"+version+"版本，请更新软件");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //更新服务
                                    String downloadUrl = "http://121.40.249.214:8080/download?fileName=掌上美食宝.apk";
                                    Context context = getApplicationContext();
                                    // 使用 Intent 打开浏览器并下载文件
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                                    // 添加 FLAG_ACTIVITY_NEW_TASK 标志
                                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    context.startActivity(browserIntent);
                                }
                            });
                            builder.show();
                        }
                    }
                });

            }
        }).start();




        register=findViewById(R.id.register);
        email=findViewById(R.id.email);
        forget=findViewById(R.id.forget_psw);
        psw=findViewById(R.id.psw);
        login=findViewById(R.id.login_button);
        spinner=findViewById(R.id.spinner);
        ImageView img1=findViewById(R.id.imageView2);
        ImageView img2=findViewById(R.id.imageView3);
        FloatingActionButton btn1=findViewById(R.id.Wechat);
        FloatingActionButton btn2=findViewById(R.id.QQ);
        FloatingActionButton btn3=findViewById(R.id.Email);
        //动画
        anim=AnimationUtils.loadAnimation(this,R.anim.login_anim);
        email.startAnimation(anim);
        psw.startAnimation(anim);
        login.startAnimation(anim);
        register.startAnimation(anim);
        spinner.startAnimation(anim);
        img1.startAnimation(anim);
        img2.startAnimation(anim);
        btn1.startAnimation(anim);
        btn2.startAnimation(anim);
        btn3.startAnimation(anim);
        forget.startAnimation(anim);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getSelectedItem().equals("用户")) {
                    user = new User();
                    user.setId(email.getText().toString());
                    user.setPassword(psw.getText().toString());
                    if (!user.getId().equals("") && !user.getPassword().equals("")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                userHttpUtil = new UserHttpUtil();
                                response = userHttpUtil.LoginCheck(user);
                                if (response.equals("登陆成功")) {
                                    Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
                                    intent.putExtra("username",user.getId());
                                    startActivity(intent);
                                    finish();
                                } else{
                                    Snackbar.make(view,response,Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }).start();
                    } else if (email.getText().toString().equals("") || psw.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "请输入账号或密码", Toast.LENGTH_LONG).show();
                    }
                }else if(spinner.getSelectedItem().equals("商家")){
                    merchant=new Merchant();
                    merchant.setMerchant_id(email.getText().toString());
                    merchant.setMerchant_password(psw.getText().toString());
                    if(!merchant.getMerchant_id().equals("")&&!merchant.getMerchant_password().equals("")){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                merchantHttpUtil=new MerchantHttpUtil();
                                response=merchantHttpUtil.LoginCheck(merchant);
                                if (response.equals("登陆成功")) {
                                    Intent intent = new Intent(LoginActivity.this, MerchantMainActivity.class);
                                    intent.putExtra("merchant_id",merchant.getMerchant_id());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Snackbar.make(view,response,Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }).start();
                    }else if (email.getText().toString().equals("") || psw.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "请输入账号或密码", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
    //定位权限的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "没有定位权限！", Toast.LENGTH_LONG).show();
                } else {
                    requestLocation();
                }
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    private void initLocation() {  //初始化
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        //设置扫描时间间隔
        option.setScanSpan(1000);
        //设置定位模式，三选一
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        /*option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);*/
        //设置需要地址信息
        option.setIsNeedAddress(true);
        //保存定位参数
        mLocationClient.setLocOption(option);
    }
    //内部类，百度位置监听器
    public class MyLocationListener  implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            address=bdLocation.getAddrStr();
            address=address.substring(2);
        }
    }
    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onPause() {
        super.onPause();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }
}