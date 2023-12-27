package com.example.myapplication.MerchantCenterFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Databases.Food;
import com.example.myapplication.R;
import com.example.myapplication.UserCenterFragment.FoodActivity;
import com.example.myapplication.Util.MerchantHttpUtil;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Food_edit_Activity extends AppCompatActivity {

    private List<Food> foods=new ArrayList<>();
    private RecyclerView recyclerView;
    String json,window_id;
    MyAdapter mMyAdapter;
    private LinearLayout layout;

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

        Intent get=getIntent();
        window_id=get.getStringExtra("window_id");

        recyclerView=findViewById(R.id.food_view);
        layout=findViewById(R.id.add);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Food_edit_Activity.this,Add_Food_Activity.class);
                intent.putExtra("window_id",window_id);
                startActivity(intent);
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                foods.clear();
                UserHttpUtil userHttpUtil=new UserHttpUtil();
                Intent intent=getIntent();
                String msg=intent.getStringExtra("window_id");
                json=userHttpUtil.FoodGet(msg);
                Gson gson=new Gson();
                Type type = new TypeToken<List<Food>>() {}.getType();
                foods=gson.fromJson(json,type);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMyAdapter = new MyAdapter();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(Food_edit_Activity.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mMyAdapter);
                    }
                });
            }
        }).start();
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder>{

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Food_edit_Activity.this).inflate(R.layout.food_edititem_list,parent,false);
            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            Food food = foods.get(position);
            holder.food_name.setText(food.getFood_name());
            Double price=food.getFood_price();
            String prices=Double.toString(price);
            holder.food_price.setText(prices);
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(Food_edit_Activity.this);
                    builder.setTitle("删除");
                    builder.setMessage("确认删除吗？");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MerchantHttpUtil mthl=new MerchantHttpUtil();
                                    String response=mthl.Food_delete(food.getFood_id());
                                    Snackbar.make(view,response,Snackbar.LENGTH_LONG).show();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            recreate();
                                        }
                                    });
                                }
                            }).start();
                        }
                    });
                    builder.setNegativeButton("取消",null);
                    builder.show();

                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Food_edit_Activity.this,Add_Food_Activity.class);
                    intent.putExtra("food_id",food.getFood_id());
                    intent.putExtra("window_id",window_id);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return foods.size();
        }
    }
    class MyViewHoder extends RecyclerView.ViewHolder{

        TextView food_name;
        TextView food_price;
        Button del;
        Button edit;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            food_name = itemView.findViewById(R.id.food_name);
            food_price = itemView.findViewById(R.id.food_price);
            del = itemView.findViewById(R.id.del);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}