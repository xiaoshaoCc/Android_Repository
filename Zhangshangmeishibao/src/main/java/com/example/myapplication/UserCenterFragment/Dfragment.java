package com.example.myapplication.UserCenterFragment;

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
import androidx.fragment.app.Fragment;

import com.example.myapplication.Login.LoginActivity;
import com.example.myapplication.R;

public class Dfragment extends Fragment {

    private TextView username;
    private TextView edit_psw;
    private TextView change_user;

    @Nullable
    @Override
    //绑定布局id
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d, container, false);

        username=view.findViewById(R.id.person_name);
        Intent intent=getActivity().getIntent();
        String msg=intent.getStringExtra("username");
        username.setText(msg);

        edit_psw=view.findViewById(R.id.edit_password);
        edit_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getActivity(),Edit_password_Activity.class);
                intent1.putExtra("user_id",msg);
                startActivity(intent1);
            }
        });

        change_user=view.findViewById(R.id.change_user);
        change_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
                builder.setTitle("切换账号");
                builder.setMessage("是否切换用户");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent chang=new Intent(getActivity(), LoginActivity.class);
                        startActivity(chang);
                        getActivity().finish();
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });

        return view;

    }

}
