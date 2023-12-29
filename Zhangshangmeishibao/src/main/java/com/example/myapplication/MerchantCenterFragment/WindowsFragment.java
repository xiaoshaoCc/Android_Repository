package com.example.myapplication.MerchantCenterFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Databases.Window;
import com.example.myapplication.R;
import com.example.myapplication.Util.MerchantHttpUtil;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WindowsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    List<Window> windowsList = new ArrayList<>();
    MyAdapter mMyAdapter;
    String json;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_windows, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserHttpUtil userHttpUtil=new UserHttpUtil();
                json=userHttpUtil.WindowGet();
                Gson gson=new Gson();
                Type type = new TypeToken<List<Window>>() {}.getType();
                windowsList=gson.fromJson(json,type);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView = view.findViewById(R.id.merchant_window_list);
                        mMyAdapter = new MyAdapter();
                        mRecyclerView.setAdapter(mMyAdapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        mRecyclerView.setLayoutManager(layoutManager);
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
            View view = LayoutInflater.from(requireContext()).inflate(R.layout.windows_item_list,parent,false);

            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            Window window = windowsList.get(position);
            holder.window_status.setText(window.getWindow_status());
            holder.mTitleTv.setText(window.getWindow_name());
            // 修正的Glide加载行
            Glide.with(holder.itemView.getContext()).load(window.getWindow_img()).into(holder.mTitleContent);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),Food_edit_Activity.class);
                    intent.putExtra("window_id", window.getWindow_id());
                    startActivity(intent);
                }
            });

            //修改窗口状态
            holder.window_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
                    builder.setTitle("窗口状态");
                    builder.setMessage("打开或者关闭");
                    builder.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MerchantHttpUtil mhtl=new MerchantHttpUtil();
                                    String response=mhtl.Window_Statuschange("打开",window.getWindow_id());
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
                    builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MerchantHttpUtil mhtl=new MerchantHttpUtil();
                                    String response=mhtl.Window_Statuschange("关闭",window.getWindow_id());
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
                    builder.show();
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
