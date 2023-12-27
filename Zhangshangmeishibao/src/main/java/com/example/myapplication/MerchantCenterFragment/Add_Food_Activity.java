package com.example.myapplication.MerchantCenterFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Databases.Food;
import com.example.myapplication.R;
import com.example.myapplication.Util.MerchantHttpUtil;
import com.google.android.material.snackbar.Snackbar;

public class Add_Food_Activity extends AppCompatActivity {

    private EditText food_name;
    private EditText food_price;
    private Button submit;
    MerchantHttpUtil  mthl=new MerchantHttpUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        food_name=findViewById(R.id.food_name);
        food_price=findViewById(R.id.food_price);
        submit=findViewById(R.id.submit);



        Intent get=getIntent();
        String msg=get.getStringExtra("food_id");
        String window_id=get.getStringExtra("window_id");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(food_name.getText().toString()!=""&&food_price.getText().toString()!="") {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (msg == null) {
                                String response = mthl.Food_Insert(food_name.getText().toString(), food_price.getText().toString(), window_id);
                                Snackbar.make(view, response, Snackbar.LENGTH_LONG).show();

                            } else {
                                Food food = new Food(msg, food_name.getText().toString(), null, Double.parseDouble(food_price.getText().toString()), window_id);
                                String response = mthl.Food_update(food);
                                Snackbar.make(view, response, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }).start();
                }else{
                    Snackbar.make(view, "请填写完整", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}