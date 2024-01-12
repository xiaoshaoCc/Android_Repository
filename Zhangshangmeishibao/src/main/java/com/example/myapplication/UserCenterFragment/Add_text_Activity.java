package com.example.myapplication.UserCenterFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Databases.Healthy_diet;
import com.example.myapplication.R;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.snackbar.Snackbar;

public class Add_text_Activity extends AppCompatActivity {
    private TextClock upload_time;
    private TextView title;
    private EditText health_text;
    private String username;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);

        title=findViewById(R.id.title);
        health_text=findViewById(R.id.healthtext);
        upload_time=findViewById(R.id.uploadtime);
        submit=findViewById(R.id.upload);
        Intent msg=getIntent();
        username=msg.getStringExtra("username");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Healthy_diet healthydiet;
                if(title.getText().toString().trim().equals("")||health_text.getText().toString().trim().equals("")){
                    Toast.makeText(Add_text_Activity.this,"请把内容填写完整！",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    healthydiet=new Healthy_diet(title.getText().toString(),health_text.getText().toString(),upload_time.getText().toString(),username);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserHttpUtil userHttpUtil=new UserHttpUtil();
                            String response=userHttpUtil.Insert_Text(healthydiet);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Add_text_Activity.this,response,Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });



    }
}