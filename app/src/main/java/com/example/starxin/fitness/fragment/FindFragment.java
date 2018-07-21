package com.example.starxin.fitness.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.starxin.fitness.R;
import com.example.starxin.fitness.activity.ShowNewsActivity;
import com.example.starxin.fitness.adapter.NewsAdapter;
import com.example.starxin.fitness.domain.News;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by StarXin on 2018/4/20.
 * 发现
 */

public class FindFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG=FindFragment.class.getSimpleName();//得到这个类名称
    private SwipeRefreshLayout srl_news;
    private RecyclerView recycler_news;
    private NewsAdapter adapter;
    private List<News> newItem=new ArrayList<>(50);
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_find,container,false);
        srl_news=view.findViewById(R.id.srl_news);
        recycler_news=view.findViewById(R.id.recycler_news);
        return view;
    }

    @Override
    protected View initView() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }


    /**
     * 从网络中抓取数据
     */
    @Override
    protected void initData() {
        super.initData();
        srl_news.setRefreshing(false);
        Toast.makeText(getContext(),"初始化数据",1).show();
        newItem.clear();//清除缓存数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getDataFromHI();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(mContext, newItem.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("FindFragment", newItem.toString());
                        srl_news.setRefreshing(false);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recycler_news.setLayoutManager(layoutManager);
                        adapter=new NewsAdapter(getContext(),newItem);
                        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        recycler_news.setAdapter(adapter);
//                        Glide.get(mContext).clearMemory();//清理缓存
                    }
                });
            }

        }).start();

    }

    private void getDataFromJR() throws IOException {
        Document doc= Jsoup.connect("http://www.jirou.com/yingyang/")
                .timeout(1000)
                .get();//获得Document对象
        Elements titleLinks=doc.select("div.article-wrap");
        Elements card=titleLinks.select("div.article-list");
        for(Element element:card){
            News n=new News();
            n.setImage(element.select("img").attr("src"));
            n.setLink(element.select("a").attr("href"));
            n.setTitle(element.select("h2.article-list-title").text());
            n.setText(element.select("p.article-list-summary").text());
            n.setTime(element.select("span.article-time").text());
            newItem.add(n);
        }
    }

    private void getDataFromHI() throws IOException {
        srl_news.setRefreshing(true);
        Document doc= Jsoup.connect("http://m.hiyd.com/")
                .timeout(1000)
                .get();//获得Document对象
        Elements titleLinks=doc.select("div.index-newslist");
        Elements card=titleLinks.select("li");
        for(Element element:card){
            News n=new News();
            n.setLink(element.select("a").attr("href"));
            n.setImage(element.select("img").attr("src"));
            n.setTitle(element.select("p").text());
//            n.setText(element.select("p").text());
//            n.setTime(element.select("span.article-time").text());
            newItem.add(n);
        }
    }


    @Override
    public void onRefresh() {
        srl_news.setRefreshing(true);
        initData();
    }
}
