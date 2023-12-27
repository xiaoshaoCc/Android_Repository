package com.example.myapplication.UserCenterFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;


import com.example.myapplication.Databases.Food;

import com.example.myapplication.Databases.Order_detail;
import com.example.myapplication.R;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private List<Food> foods=new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView total_price;
    private Order_detail[] detail;
    private Button button;
    MyAdapter mMyAdapter;
    String json,msg;
    int[] number;
    double money=0.0;

    //创建回调函数接口
    public interface OrderArrayListener {
        void orederArrayChanged(Order_detail[] details);
    }
    //接口对象
    private OrderArrayListener orderArrayListener;
    //回调方法
    public void setNumberArrayListener(OrderArrayListener listener) {
        this.orderArrayListener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recyclerView=findViewById(R.id.food_view);
        total_price=findViewById(R.id.total_price);
        button=findViewById(R.id.button);
        new Thread(new Runnable() {
            @Override
            public void run() {
                foods.clear();
                UserHttpUtil userHttpUtil=new UserHttpUtil();
                Intent intent=getIntent();
                msg=intent.getStringExtra("window_id");
                json=userHttpUtil.FoodGet(msg);
                Gson gson=new Gson();
                Type type = new TypeToken<List<Food>>() {}.getType();
                foods=gson.fromJson(json,type);
                number=new int[foods.size()];
                detail=new Order_detail[foods.size()];

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMyAdapter = new MyAdapter();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(FoodActivity.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mMyAdapter);
                    }
                });
            }
        }).start();
        setNumberArrayListener(new OrderArrayListener() {
            @Override
            public void orederArrayChanged(Order_detail[] details) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_list, parent, false);
            return new FoodActivity.MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, @SuppressLint("RecyclerView") int position) {
            Food food = foods.get(position);
            detail[position]=new Order_detail();
            holder.food_name.setText(food.getFood_name());
            Double price=food.getFood_price();
            String prices=Double.toString(price);
            holder.food_price.setText(prices);
            holder.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(number[position]>0){
                        number[position]--;
                        String num=Integer.toString(number[position]);
                        holder.quality.setText(num);
                        money-=food.getFood_price();
                        total_price.setText(Double.toString(money));
                        //修改detail属性
                        detail[position].setFood_id(food.getFood_id());
                        detail[position].setFood_quality(String.valueOf(number[position]));
                        // 通知侦听器有关number数组的更改
                        if (orderArrayListener!= null) {
                            orderArrayListener.orederArrayChanged(detail);
                        }
                    }
                }
            });
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    number[position]++;
                    String num=Integer.toString(number[position]);
                    holder.quality.setText(num);
                    money+=food.getFood_price();
                    total_price.setText(Double.toString(money));
                    //修改detail属性
                    detail[position].setFood_id(food.getFood_id());
                    detail[position].setFood_quality(String.valueOf(number[position]));
                    // 通知侦听器有关number数组的更改
                    if (orderArrayListener!= null) {
                        orderArrayListener.orederArrayChanged(detail);
                    }
                }
            });

        }
        @Override
        public int getItemCount() {
            return foods.size();
        }

    }
    //添加item控件
    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView food_name;
        TextView food_price;
        TextView quality;
        Button sub;
        Button add;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.food_name);
            food_price = itemView.findViewById(R.id.food_price);
            quality = itemView.findViewById(R.id.quality);
            sub = itemView.findViewById(R.id.sub);
            add = itemView.findViewById(R.id.add);
        }
    }
}