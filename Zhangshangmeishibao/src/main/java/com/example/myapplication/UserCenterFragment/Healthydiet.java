package com.example.myapplication.UserCenterFragment;

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

import com.example.myapplication.Databases.Healthy_diet;
import com.example.myapplication.R;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Healthydiet extends Fragment {

    private FloatingActionButton add;
    private List<Healthy_diet> healthydiets=new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    String json;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_healthydiet,container,false);
        recyclerView=view.findViewById(R.id.healthydietRecyclerview);

        Intent msg=getActivity().getIntent();
        String username=msg.getStringExtra("username");
        new Thread(new Runnable() {
            @Override
            public void run() {
                healthydiets.clear();
                UserHttpUtil userHttpUtil=new UserHttpUtil();
                json=userHttpUtil.Healthydiet_Findall();
                Gson gson=new Gson();
                Type type = new TypeToken<List<Healthy_diet>>() {}.getType();
                try {
                    // 尝试解析为 List<Order>
                    healthydiets = gson.fromJson(json, type);
                } catch (JsonSyntaxException e) {
                    // 如果解析为 List<Order> 失败，尝试解析为单个 Order 对象
                    Healthy_diet singleOrder = gson.fromJson(json, Healthy_diet.class);
                    healthydiets = new ArrayList<>();
                    if (singleOrder != null) {
                        healthydiets.add(singleOrder);
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter = new MyAdapter();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(myAdapter);
                    }
                });
            }
        }).start();



        add=view.findViewById(R.id.add_text);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(requireContext(), Add_text_Activity.class);
                intent.putExtra("username",username);
                startActivity(intent);

            }
        });


        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(requireContext()).inflate(R.layout.healthydiet_item_list,parent,false);
            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            Healthy_diet healthy_diet=healthydiets.get(position);
            holder.title.setText(healthy_diet.getTitle());
            holder.username.setText("作者："+healthy_diet.getUsername());
            holder.upload_time.setText(healthy_diet.getUploadtime());
            holder.detail.setText("详情："+healthy_diet.getHealthtext());
            holder.detail_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(requireContext(),Detail_Activity.class);
                    intent.putExtra("healthy_diet",healthy_diet);
                    startActivity(intent);
                }
            });

        }
        @Override
        public int getItemCount() {
            return healthydiets.size();
        }
    }
    //添加item控件
    class MyViewHoder extends RecyclerView.ViewHolder {

        TextView title;
        TextView username;
        TextView detail;
        TextView upload_time;
        ConstraintLayout detail_layout;


        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            username=itemView.findViewById(R.id.username);
            detail=itemView.findViewById(R.id.text_detail);
            upload_time=itemView.findViewById(R.id.upload_time1);
            detail_layout=itemView.findViewById(R.id.healthy_diet_layout);

        }
    }
}
