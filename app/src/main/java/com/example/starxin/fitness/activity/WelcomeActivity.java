package com.example.starxin.fitness.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.starxin.fitness.R;

public class WelcomeActivity extends Activity {

    private Handler handler=new Handler();
    private boolean isStart=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);//自定义的code
        }
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                initView();
            }
        },2000);
    }

    private void initView() {
        if(isStart){
            isStart=false;
            Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }

}
