package com.example.myapplication.MerchantCenterFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Databases.Food;
import com.example.myapplication.Databases.Order;
import com.example.myapplication.Databases.Order_detail;
import com.example.myapplication.R;
import com.example.myapplication.UserCenterFragment.Cfragment;
import com.example.myapplication.Util.MerchantHttpUtil;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {


    private List<Order> orders=new ArrayList<>();
    private RecyclerView recyclerView;
    private List<Order_detail> order_details=new ArrayList<>();
    String detail="";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_orders, container, false);


        orders.clear();
        recyclerView=view.findViewById(R.id.orders);
        new Thread(new Runnable() {
            @Override
            public void run() {
                MerchantHttpUtil mthl=new MerchantHttpUtil();
                Intent intent=getActivity().getIntent();
                String merchant_id=intent.getStringExtra("merchant_id");
                Gson gson=new Gson();
                Type type = new TypeToken<List<Order>>() {}.getType();
                String json=mthl.Merchant_GetOrder(merchant_id);
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
            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            Order order=orders.get(position);
            holder.order_id.setText("订单号:"+order.getOrder_id());
            holder.order_date.setText("下单时间："+order.getOrder_date());
            holder.order_price.setText(order.getOrder_price().toString());
            holder.order_status.setText(order.getOrder_status());
            holder.username.setText("id:"+order.getUser_id());
            holder.order_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detail="";
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MerchantHttpUtil mthl=new MerchantHttpUtil();
                            String json=mthl.Order_detail(order.getOrder_id());
                            Gson gson=new Gson();
                            Type type = new TypeToken<List<Order_detail>>() {}.getType();
                            try {
                                // 尝试解析为 List<Order>
                                order_details = gson.fromJson(json, type);
                            } catch (JsonSyntaxException e) {
                                // 如果解析为 List<Order> 失败，尝试解析为单个 Order 对象
                                Order_detail singleOrder = gson.fromJson(json, Order_detail.class);
                                order_details = new ArrayList<>();
                                if (singleOrder != null) {
                                    order_details.add(singleOrder);
                                }
                            }
                            //获得订单详情表
                            for(int i=0;i<order_details.size();i++){
                                String food_name= mthl.Get_food_name(order_details.get(i).getFood_id());
                                detail=detail+food_name+":"+order_details.get(i).getFood_quality()+" ";
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                    builder.setTitle("订单详情");
                                    builder.setMessage(detail);
                                    builder.setPositiveButton("确认",null);
                                    builder.show();
                                }
                            });
                        }
                    }).start();
                }
            });
            holder.order_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
                    builder.setTitle("订单状态");
                    builder.setMessage("订单是否完成？");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            order.setOrder_status("已完成");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MerchantHttpUtil mthl=new MerchantHttpUtil();
                                    mthl.Order_Status(order);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            getActivity().recreate();
                                        }
                                    });
                                }
                            }).start();

                        }
                    });
                    builder.setNegativeButton("否",null);
                    builder.show();
                }
            });
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
        ConstraintLayout order_layout;


        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            order_id=itemView.findViewById(R.id.order_id);
            order_date=itemView.findViewById(R.id.order_date);
            order_price=itemView.findViewById(R.id.total_price);
            order_status=itemView.findViewById(R.id.order_status);
            order_layout=itemView.findViewById(R.id.order_layout);
            username=itemView.findViewById(R.id.username);
        }
    }
}
