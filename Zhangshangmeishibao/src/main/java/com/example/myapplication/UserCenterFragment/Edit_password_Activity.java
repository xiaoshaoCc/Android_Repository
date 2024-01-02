package com.example.myapplication.UserCenterFragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Databases.User;
import com.example.myapplication.Login.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.snackbar.Snackbar;

public class Edit_password_Activity extends AppCompatActivity {

    private EditText password;
    private EditText confirm_psw;
    private Button confirm;
    User usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        password=findViewById(R.id.password);
        confirm_psw=findViewById(R.id.confrim_psw);
        confirm=findViewById(R.id.confirm_button);
        Intent get=getIntent();
        String user_id=get.getStringExtra("user_id");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!password.getText().toString().equals("")&&!confirm_psw.getText().toString().equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            usr = new User(user_id, password.getText().toString());
                            UserHttpUtil userHttpUtil = new UserHttpUtil();
                            String response = userHttpUtil.LoginCheck(usr);
                            if (response.equals("登陆成功")) {
                                usr.setPassword(confirm_psw.getText().toString());
                                String respon = userHttpUtil.User_Edit(usr);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_password_Activity.this);
                                        builder.setMessage(respon);
                                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(Edit_password_Activity.this, LoginActivity.class);
                                                startActivity(intent);
                                                System.exit(0);
                                            }
                                        });
                                        builder.setNegativeButton("取消",null);
                                        builder.show();
                                    }
                                });
                            } else {
                                Snackbar.make(view, "密码错误", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }).start();
                }else{
                    Snackbar.make(view, "请填写完整！", Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }
}