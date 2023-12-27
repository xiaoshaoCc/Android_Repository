package com.example.myapplication.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;
import com.example.myapplication.R;


public class StartpageActivity extends AppCompatActivity {

    private static int SPLASH_DISPLAY_LENGHT= 4000;    //延迟2秒
    private ImageView image1;
    private LottieAnimationView startflash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);

        image1=findViewById(R.id.startpage);
        startflash=findViewById(R.id.startflash);
        image1.animate().translationX(-1000).setDuration(2500).setStartDelay(5000);
        startflash.animate().translationX(1000).setDuration(2500).setStartDelay(5000);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(StartpageActivity.this, ScreenActivity.class);	//第二个参数即为执行完跳转后的Activity
                startActivity(intent);
                StartpageActivity.this.finish();   //关闭splashActivity，将其回收，否则按返回键会返回此界面
            }
            }, SPLASH_DISPLAY_LENGHT);
    }
}
