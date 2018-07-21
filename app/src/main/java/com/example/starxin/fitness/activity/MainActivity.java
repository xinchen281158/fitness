package com.example.starxin.fitness.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.starxin.fitness.R;
import com.example.starxin.fitness.listener.RadioGroupCheckedChangeListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private int position;//选中的Fragment的对应的位置
    private FragmentManager fragmentManager;
    private ImageView addKeepFitDate;
    private ImageView iv_knowlage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        fragmentManager=getSupportFragmentManager();
        //初始化View
        initView();
        //设置RadioGroup的监听
        setListener();
    }

    private void setListener() {
        rg_main.setOnCheckedChangeListener(new RadioGroupCheckedChangeListener(fragmentManager,position));
        rg_main.check(R.id.rb_run);//设置默认选中运动界面
    }



    private void initView() {
        setContentView(R.layout.activity_main);
        iv_knowlage=findViewById(R.id.iv_knowlage);
        rg_main = findViewById(R.id.rg_main);
        addKeepFitDate=findViewById(R.id.add_keep_fit_date);
        addKeepFitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddKeepFitNoteActivity.class));
            }
        });
        iv_knowlage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,KnowledgeActivity.class));
            }
        });
    }
}
