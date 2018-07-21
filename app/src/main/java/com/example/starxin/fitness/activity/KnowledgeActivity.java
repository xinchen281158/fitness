package com.example.starxin.fitness.activity;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.starxin.fitness.R;
import com.example.starxin.fitness.adapter.KnowledgeAdapter;
import com.example.starxin.fitness.adapter.NewsAdapter;
import com.example.starxin.fitness.domain.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeActivity extends Activity {

    private SwipeRefreshLayout srl_knowledge;
    private RecyclerView recycler_knowledge;
    private KnowledgeAdapter adapter;
    private List<News> newItem=new ArrayList<>(50);
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);
        srl_knowledge=findViewById(R.id.srl_konwledge);
        recycler_knowledge=findViewById(R.id.recycler_konwledge);
        //初始化数据
        initData();
    }

    private void initData() {
        srl_knowledge.setRefreshing(false);
        Toast.makeText(this,"初始化数据",1).show();
        newItem.clear();//清除缓存数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getDataFromHI();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(mContext, newItem.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Knowledge", newItem.toString());
                        srl_knowledge.setRefreshing(false);
                        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recycler_knowledge.setLayoutManager(layoutManager);
                        adapter=new KnowledgeAdapter(getApplicationContext(),newItem);
                        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        recycler_knowledge.setAdapter(adapter);
//                        Glide.get(mContext).clearMemory();//清理缓存
                    }
                });
            }

        }).start();

    }
    private void getDataFromHI() throws IOException {
        srl_knowledge.setRefreshing(true);
        Document doc= Jsoup.connect("http://m.hiyd.com/bb/")
                .timeout(1000)
                .get();//获得Document对象
        Elements titleLinks=doc.select("ul.main-list");
        Elements card=titleLinks.select("li");
        Log.e("Knowledge", card.toString());
        for(Element element:card){
            News n=new News();
            n.setLink(element.select("a").attr("href"));
            n.setImage(element.select("img").attr("src"));
            n.setTitle(element.select("h3").text());
            n.setTag(element.select("em").text());
            newItem.add(n);
        }
    }
    public void onBack(View view){
        finish();
    }
}
