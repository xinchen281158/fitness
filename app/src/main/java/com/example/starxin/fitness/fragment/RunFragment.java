package com.example.starxin.fitness.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starxin.fitness.R;
import com.example.starxin.fitness.activity.RecordPathActivity;
import com.example.starxin.fitness.domain.KeepNoteEntity;
import com.example.starxin.fitness.utils.DecimalUtils;

import java.util.List;

import at.markushi.ui.CircleButton;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by StarXin on 2018/4/20.
 * 运动
 */

public class RunFragment extends BaseFragment {
    private View view;
    private CircleButton runing;
    float totalTime = 0f;//运动总时长
    float totalRunLength = 0f;//
    float totalSitUp = 0f;
    float totalSportsApparatusTimes = 0f;
    int listSize=0;
    private static final String TAG=RunFragment.class.getSimpleName();//得到这个类名称

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.keep_list_list_item_top,container,false);
        runing=view.findViewById(R.id.runing);
        runing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),RecordPathActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        initData();//初始化数据
    }

    @Override
    protected View initView() {
        Log.e("TAG", "运动Fragment页面被初始化了。。。");
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e("TAG", "运动Fragment页面数据被初始化了。。。");
        //getData();//获取数据
        getHeaderView(1,totalTime,
                totalRunLength,
                totalSitUp,
                totalSportsApparatusTimes,
                listSize);//设置数据

    }
    /**
     * 从网络中获取数据
     */
    private void getData(){
        BmobQuery<KeepNoteEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", BmobUser.getCurrentUser().getObjectId());
        query.setLimit(100);
        query.findObjects(new FindListener<KeepNoteEntity>() {
            @Override
            public void done(List<KeepNoteEntity> list, BmobException e) {
                if (e == null) {
                    initData(list);
                } else {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData(List<KeepNoteEntity> list) {
        // 计算总数
        for (int i = 0; i < list.size(); i++) {
            KeepNoteEntity bean = list.get(i);
            totalTime += bean.getExerciseDuration();
            totalRunLength += bean.getRunLength();
            totalSitUp += bean.getSitUps();
            totalSportsApparatusTimes += bean.getSportsApparatusTimes();
        }
        listSize=list.size();
        Log.e("First", String.valueOf(totalTime));
        Log.e("First", String.valueOf(totalRunLength));
        Log.e("First", String.valueOf(totalSitUp));
        Log.e("First", String.valueOf(totalSportsApparatusTimes));
        Log.e("First", String.valueOf(listSize));
        initData();
    }

    private void getHeaderView(int type,float string1, float string2, float string3, float string4, int string5) {
//        View view = getLayoutInflater().inflate(R.layout.keep_list_list_item_top,false);
        if (type == 1) {
            TextView totalTime = (TextView) view.findViewById(R.id.item_top_total_fit_length_tv);
            TextView totalRunLength = (TextView) view.findViewById(R.id.item_top_total_run_length_tv);
            TextView totalSitUp = (TextView) view.findViewById(R.id.item_top_total_situp_tv);
            TextView totalSportsApparatusTimes = (TextView) view.findViewById(R.id.item_top_total_sports_apparatus_tv);
            TextView totalDay = (TextView) view.findViewById(R.id.item_top_total_day_length_tv);
            totalTime.setText(DecimalUtils.formatDecimalWithZero(string1, 2));
            totalRunLength.setText(DecimalUtils.formatDecimalWithZero(string2, 2));
            totalSitUp.setText(String.valueOf((int) string3));
            totalSportsApparatusTimes.setText(String.valueOf((int) string4));
            totalDay.setText(string5 + "");
        }
    }
}
