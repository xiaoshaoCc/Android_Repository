package com.example.myapplication.UserCenterFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.myapplication.Databases.Food;
import com.example.myapplication.Databases.Order;
import com.example.myapplication.Databases.Window;
import com.example.myapplication.Login.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Afragment extends Fragment {

    private Banner banner;
    private RecyclerView mRecyclerView;
    private TextView username;
    List<Window> windowsList = new ArrayList<>();
    MyAdapter mMyAdapter;
    String json,msg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);

        username=view.findViewById(R.id.welcome);
        Intent intent=getActivity().getIntent();
        msg=intent.getStringExtra("username");
        username.setText("Hello!,"+msg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserHttpUtil userHttpUtil=new UserHttpUtil();
                json=userHttpUtil.WindowGet();
                Gson gson=new Gson();
                Type type = new TypeToken<List<Window>>() {}.getType();
                try {
                    // 尝试解析为 List<Order>
                    windowsList = gson.fromJson(json, type);
                } catch (JsonSyntaxException e) {
                    // 如果解析为 List<Order> 失败，尝试解析为单个 Order 对象
                    Window singleOrder = gson.fromJson(json, Window.class);
                    windowsList = new ArrayList<>();
                    if (singleOrder != null) {
                        windowsList.add(singleOrder);
                    }
                }
            }
        }).start();

        //加载banner控件
        banner=(Banner) view.findViewById(R.id.banner);

        List<Integer> images=new ArrayList();
        images.add(R.mipmap.pic1);
        images.add(R.mipmap.pic2);
        images.add(R.mipmap.pic3);

        banner.setAdapter(new BannerImageAdapter<Integer>(images) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        });
        banner.isAutoLoop(true);
        banner.setIndicator(new CircleIndicator(view.getContext()));
        banner.setIndicatorSelectedColor(Color.GREEN);
        banner.setIndicatorSpace(10);
        banner.start();
        mRecyclerView = view.findViewById(R.id.windwrecycleview);
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        return  view;
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(requireContext()).inflate(R.layout.windows_item_list,parent,false);
            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Afragment.MyViewHoder holder, int position) {
            Window window = windowsList.get(position);
            holder.window_status.setText(window.getWindow_status());
            holder.mTitleTv.setText(window.getWindow_name());
            // 修正的Glide加载行
            Glide.with(holder.itemView.getContext()).load(window.getWindow_img()).into(holder.mTitleContent);
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(window.getWindow_status().equals("关闭")) {
                        Snackbar.make(view,"窗口已关闭",Snackbar.LENGTH_LONG).show();
                    }else {
                        Intent intent = new Intent(requireContext(), FoodActivity.class);
                        intent.putExtra("window_id", window.getWindow_id());
                        intent.putExtra("username",msg);
                        startActivity(intent);
                    }
                }
            });

        }
        @Override
        public int getItemCount() {
            return windowsList.size();
        }
    }
    //添加item控件
    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        ImageView mTitleContent;
        ConstraintLayout linearLayout;
        TextView window_status;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.textView);
            mTitleContent = itemView.findViewById(R.id.imageview);
            linearLayout=itemView.findViewById(R.id.item_listview);
            window_status=itemView.findViewById(R.id.window_status);
        }
    }
}
