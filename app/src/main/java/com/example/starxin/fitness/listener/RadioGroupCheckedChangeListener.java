package com.example.starxin.fitness.listener;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.starxin.fitness.R;
import com.example.starxin.fitness.fragment.BaseFragment;
import com.example.starxin.fitness.fragment.ClubFragment;
import com.example.starxin.fitness.fragment.FindFragment;
import com.example.starxin.fitness.fragment.PersonalFragment;
import com.example.starxin.fitness.fragment.RunFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StarXin on 2018/4/20.
 */

public class RadioGroupCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

    private int mPosition;//选中Fragment的对应的位置
    private List<BaseFragment> mBaseFragment;
    private FragmentManager mFragmentManager;
    private Fragment mContent;//上次切换的Fragment


    public RadioGroupCheckedChangeListener(FragmentManager fragmentManager,int position) {
        this.mPosition=position;
        this.mFragmentManager=fragmentManager;
        //初始化Fragment
        initFragment();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_run://运动
                mPosition=0;
                break;
            case R.id.rb_find://发现
                mPosition=1;
                break;
            case R.id.rb_club://社区
                mPosition=2;
                break;
            case R.id.rb_personal://个人中心
                mPosition=3;
                break;
            default://默认
                mPosition=0;
                break;
        }
        //根据位置得到对应的Fragment
        BaseFragment to=getFragment();
        //替换
        showFragment(mContent,to);
    }

//    private void switchFragment(BaseFragment fragment) {
//        //1、得到FragmentManger
//        //2、开启事务
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//        //3、替换
//        fragmentTransaction.replace(R.id.fl_content, fragment);
//        //4、提交事务
//        fragmentTransaction.commit();
//    }

    /**
     *
     * @param from 刚显示的Fragment，马上就要被隐藏
     * @param to  马上要切换到的Fragment，马上要显示
     */
    private void showFragment(Fragment from,Fragment to){
        if(from!=to){
            mContent=to;
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to没有被添加
                //from隐藏
                if (from!=null){
                    fragmentTransaction.hide(from);
                }

                //添加to
                if (to!=null){
                    fragmentTransaction.add(R.id.fl_content,to).commit();
                }
            }else {
                //to已经被添加
                // from隐藏
                if (from!=null){
                    fragmentTransaction.hide(from);
                }
                //显示to
                if (to!=null){
                    fragmentTransaction.show(to).commit();
                }
            }
        }
    }

    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment=mBaseFragment.get(mPosition);
        return  fragment;
    }

    private void initFragment() {
        mBaseFragment=new ArrayList<>();
        mBaseFragment.add(new RunFragment());//运动Fragment
        mBaseFragment.add(new FindFragment());//发现Fragment
        mBaseFragment.add(new ClubFragment());//社区Fragment
        mBaseFragment.add(new PersonalFragment());//个人中心Fragment
    }
}
