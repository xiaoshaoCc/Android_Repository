package com.example.myapplication.UserCenterFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Databases.Food;
import com.example.myapplication.Databases.Order;
import com.example.myapplication.Databases.Window;
import com.example.myapplication.R;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Cfragment extends Fragment {

    private List<Order> orders=new ArrayList<>();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    //绑定布局id
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_c, container, false);

        orders.clear();
        recyclerView=view.findViewById(R.id.orders);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserHttpUtil userHttpUtil=new UserHttpUtil();
                Intent intent=getActivity().getIntent();
                String user_id=intent.getStringExtra("username");
                String json=userHttpUtil.Find_Order(user_id);
                Gson gson=new Gson();
                Type type = new TypeToken<List<Order>>() {}.getType();
                try {
                    // 尝试解析为 List<Order>
                    orders = gson.fromJson(json, type);
                } catch (JsonSyntaxException e) {
                    // 如果解析为 List<Order> 失败，尝试解析为单个 Order 对象
                    Order singleOrder = gson.fromJson(json, Order.class);
                    orders = new ArrayList<>();
                    if (singleOrder != null) {
                        orders.add(singleOrder);
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyAdapter mMyAdapter = new MyAdapter();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mMyAdapter);
                    }
                });
            }
        }).start();


        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(requireContext()).inflate(R.layout.order_item_list,parent,false);
            return new Cfragment.MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            Order order=orders.get(position);
            holder.order_id.setText("订单号:"+order.getOrder_id());
            holder.order_date.setText("下单时间："+order.getOrder_date());
            holder.order_price.setText(order.getOrder_price().toString());
            holder.order_status.setText(order.getOrder_status());
            holder.username.setText("id:"+order.getUser_id());
        }


        @Override
        public int getItemCount() {return  orders.size();}
    }
    //添加item控件
    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView order_id;
        TextView order_date;
        TextView order_price;
        TextView order_status;
        TextView username;


        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            order_id=itemView.findViewById(R.id.order_id);
            order_date=itemView.findViewById(R.id.order_date);
            order_price=itemView.findViewById(R.id.total_price);
            order_status=itemView.findViewById(R.id.order_status);
            username=itemView.findViewById(R.id.username);
        }
    }
}
