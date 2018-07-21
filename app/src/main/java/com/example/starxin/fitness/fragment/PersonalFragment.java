package com.example.starxin.fitness.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.starxin.fitness.R;
import com.example.starxin.fitness.activity.LoginActivity;

import cn.bmob.v3.BmobUser;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by StarXin on 2018/4/20.
 * 个人中心
 */

public class PersonalFragment extends BaseFragment {

    private static final String TAG=PersonalFragment.class.getSimpleName();//得到这个类名称
    private ImageView personal_head_iv;
    private ImageView personal_back_iv;
    private LinearLayout personal_item_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal,container,false);
    }

    @Override
    protected View initView() {

        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e("TAG", "个人中心Fragment页面数据被初始化了。。。");
        personal_head_iv=getActivity().findViewById(R.id.personal_head_iv);
        personal_back_iv=getActivity().findViewById(R.id.personal_back_iv);
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(getContext(),25),new CenterCrop(getContext()))
                .into(personal_back_iv);
        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(personal_head_iv);
//        personal_item_view=getActivity().findViewById(R.id.logOut);
//        personal_item_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BmobUser.logOut();//清除缓存用户对象
//                startActivity(new Intent(getContext(), LoginActivity.class));
//            }
//        });
    }
}
