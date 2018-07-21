package com.example.starxin.fitness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.starxin.fitness.R;
import com.example.starxin.fitness.activity.ShowNewsActivity;
import com.example.starxin.fitness.domain.News;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by StarXin on 2018/5/21.
 */

public class NewsAdapter extends BaseQuickAdapter<News,BaseViewHolder>{

    private Context mContext;
    private List<News> newItems;
    private ImageView news_im;
    private TextView news_title;
    private TextView news_text;
    private TextView news_time;

    public NewsAdapter(Context context, @Nullable List<News> data) {
        super(R.layout.news_item, data);
        mContext=context;
    }


    @Override
    protected void convert(BaseViewHolder holder, final News item) {
        holder.setText(R.id.news_title,TextUtils.isEmpty(item.getTitle())?"暂无数据":item.getTitle());
//                .setText(R.id.news_time,TextUtils.isEmpty(item.getTime())?"暂无数据":item.getTime());
        ImageView todayPictureNote = holder.getView(R.id.news_im);
        if(item.getImage()!=null){
            Glide.with(mContext)
                    .load(item.getImage())
                    .placeholder(R.drawable.head)
                    .dontAnimate()
                    .thumbnail(0.1f)
                    .into(todayPictureNote);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,ShowNewsActivity.class);
                intent.putExtra("link",item.getLink());
                mContext.startActivity(intent);
            }
        });
    }
}
