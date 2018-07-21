package com.example.starxin.fitness.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.starxin.fitness.activity.MainActivity;
import com.example.starxin.fitness.activity.RegisterActivity;

/**
 * Created by StarXin on 2018/4/9.
 */

public class LoginListener implements View.OnClickListener {

    private Context mContext;
    public LoginListener(Context context) {
        mContext=context;
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(mContext, RegisterActivity.class);
        mContext.startActivity(intent);
    }
}
