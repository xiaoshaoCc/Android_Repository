package com.example.myapplication.UserCenterFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.example.myapplication.Databases.Comment;
import com.example.myapplication.Databases.Healthy_diet;
import com.example.myapplication.R;
import com.example.myapplication.Util.UserHttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Detail_Activity extends AppCompatActivity {

    private TextView title;
    private TextView username;
    private TextView upload_time;
    private TextView text;
    private Healthy_diet healthy_diet;
    private Button upload;
    private EditText comments;
    private Comment comment;
    private RecyclerView comment_list;
    private List<Comment> commentList=new ArrayList<>();

    private MyAdapter myAdapter;

    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        if(intent.hasExtra("healthy_diet")){
            healthy_diet=(Healthy_diet) intent.getSerializableExtra("healthy_diet");
        }
        title=findViewById(R.id.healthy_title);
        username=findViewById(R.id.user);
        comments=findViewById(R.id.comment1);
        comment_list=findViewById(R.id.comment_list);
        upload=findViewById(R.id.upload);
        upload_time=findViewById(R.id.time);
        text=findViewById(R.id.healthy_text);


        title.setText(healthy_diet.getTitle());
        username.setText(healthy_diet.getUsername());
        upload_time.setText(healthy_diet.getUploadtime());
        text.setText("  "+healthy_diet.getHealthtext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                commentList=getCommentlist(healthy_diet.getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter = new MyAdapter();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(Detail_Activity.this){
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        };
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        comment_list.setLayoutManager(layoutManager);
                        comment_list.setAdapter(myAdapter);
                    }
                });
            }
        }).start();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//用于插入时间
                        Date date=new Date();
                        comment=new Comment(comments.getText().toString(), format1.format(date),healthy_diet.getUsername(),healthy_diet.getId());
                        UserHttpUtil userHttpUtil=new UserHttpUtil();
                        response=userHttpUtil.Insert_Comment(comment);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder=new AlertDialog.Builder(Detail_Activity.this);
                                builder.setMessage(response);
                                builder.setPositiveButton("确认",null);
                                builder.show();
                                recreate();
                            }
                        });
                    }
                }).start();

            }
        });

    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_list, parent, false);
            return new MyViewHoder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, @SuppressLint("RecyclerView") int position) {
            Comment comment1=commentList.get(position);
            holder.username.setText(comment1.getUsername());
            holder.comment.setText(comment1.getComment());
            holder.upload_time.setText(comment1.getUpload_time());
        }
        @Override
        public int getItemCount() {
            return commentList.size();
        }

    }
    //添加item控件
    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView username;
        TextView comment;
        TextView upload_time;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username1);
            upload_time = itemView.findViewById(R.id.upload_time1);
            comment = itemView.findViewById(R.id.comment1);

        }
    }

    public List<Comment> getCommentlist(int Healthy_diet_id){
        UserHttpUtil userHttpUtil=new UserHttpUtil();
        String json=userHttpUtil.Get_Comment(Healthy_diet_id);
        Type type=new TypeToken<List<Comment>>(){}.getType();
        Gson gson=new Gson();
        List<Comment> commentList1=new ArrayList<>();
        try{
            commentList1=gson.fromJson(json, type);
        }catch (JsonSyntaxException e){
            Comment single=gson.fromJson(json,Comment.class);
            commentList1.add(single);
        }
        return commentList1;
    }
}