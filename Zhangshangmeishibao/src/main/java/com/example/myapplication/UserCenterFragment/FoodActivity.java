package com.example.myapplication.UserCenterFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.example.myapplication.Databases.Food;

import com.example.myapplication.Databases.Order;
import com.example.myapplication.Databases.Order_detail;
import com.example.myapplication.R;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FoodActivity extends AppCompatActivity {

    private List<Food> foods=new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView total_price;
    private Order_detail[] detail;
    private Button button;
    MyAdapter mMyAdapter;
    String json,msg,username;
    int[] number;
    double money=0.0;

    //创建回调函数接口
    public interface OrderArrayListener {
        void orederArrayChanged(Order_detail[] details,double total);
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
                username=intent.getStringExtra("username");
                json=userHttpUtil.FoodGet(msg);
                Gson gson=new Gson();
                Type type = new TypeToken<List<Food>>() {}.getType();
                try {
                    // 尝试解析为 List<Order>
                    foods = gson.fromJson(json, type);
                } catch (JsonSyntaxException e) {
                    // 如果解析为 List<Order> 失败，尝试解析为单个 Order 对象
                    Food singleOrder = gson.fromJson(json, Food.class);
                    foods = new ArrayList<>();
                    if (singleOrder != null) {
                        foods.add(singleOrder);
                    }
                }
                number=new int[foods.size()];
                Arrays.fill(number,0);
                detail=new Order_detail[foods.size()];
                for(int i=0;i<foods.size();i++){
                    detail[i]=new Order_detail();
                }

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

        //回调函数
        setNumberArrayListener(new OrderArrayListener() {
            @Override
            public void orederArrayChanged(Order_detail[] details,double total) {
            }
        });
        //提交订单逻辑
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(FoodActivity.this);
                builder.setTitle("订单");
                builder.setMessage("总价："+total_price.getText().toString());
                builder.setPositiveButton("支付订单", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String msgs="支付成功";
                        if(msgs.equals("支付成功")){
                            SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");//用于生成订单号
                            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//用于插入时间
                            Date date=new Date();
                            String order_id=format.format(date)+username;
                            Order order=new Order(order_id,username,format1.format(date),money,"已支付",msg);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    UserHttpUtil userHttpUtil=new UserHttpUtil();
                                    userHttpUtil.Order_submit(order);
                                    for(int i=0;i<detail.length;i++){
                                        if(number[i]!=0) {
                                            detail[i].setOrder_id(order_id);
                                            userHttpUtil.Order_detail_submit(detail[i]);
                                        }else{

                                        }
                                    }
                                }
                            }).start();
                            Snackbar.make(view,"支付成功",Snackbar.LENGTH_LONG).show();

                        }else {
                            AlertDialog.Builder builder1=new AlertDialog.Builder(FoodActivity.this);
                            builder1.setMessage("支付失败");
                            builder1.show();
                        }
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
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
                            orderArrayListener.orederArrayChanged(detail,money);
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
                        orderArrayListener.orederArrayChanged(detail,money);
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