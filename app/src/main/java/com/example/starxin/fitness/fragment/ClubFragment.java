package com.example.starxin.fitness.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.starxin.fitness.R;
import com.example.starxin.fitness.adapter.KeepFitAdapter;
import com.example.starxin.fitness.domain.KeepNoteEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by StarXin on 2018/4/20.
 * 社区
 */

public class ClubFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG=ClubFragment.class.getSimpleName();//得到这个类名称

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.add_keep_fit_date)
    ImageView addKeepFitDate;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    private KeepFitAdapter keepFitAdapter;//卡片布局

    private View view;
    List<KeepNoteEntity> keepNoteEntityList=new ArrayList<KeepNoteEntity>();//运动数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_club,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        srl=view.findViewById(R.id.srl);
//        initData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }



    @Override
    protected View initView() {
        return null;
    }

    @Override
    protected void initData(){

    }

    protected void initData(List<KeepNoteEntity> list) {
        super.initData();
        keepNoteEntityList=list;
        srl.setOnRefreshListener(this);
        Log.e("TAG", keepNoteEntityList.toString());
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        keepFitAdapter =new KeepFitAdapter(getActivity(),keepNoteEntityList);
        keepFitAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(keepFitAdapter);
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
                srl.setRefreshing(false);//隐藏刷新进度
                if (e == null) {
                    initData(list);
                } else {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
      srl.setRefreshing(true);//显示刷新进度
        getData();
    }

}
