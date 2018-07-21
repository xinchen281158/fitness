package com.example.starxin.fitness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.starxin.fitness.R;
import com.example.starxin.fitness.activity.ShowKnowledgeActivity;
import com.example.starxin.fitness.activity.ShowNewsActivity;
import com.example.starxin.fitness.domain.News;

import java.util.List;

/**
 * Created by StarXin on 2018/5/21.
 */

public class KnowledgeAdapter extends BaseQuickAdapter<News,BaseViewHolder>{

    private Context mContext;
    private List<News> newItems;
    private ImageView knowledge_im;//图片
    private TextView knowledge_title;//标题
    private TextView knowledge_h;//标签

    public KnowledgeAdapter(Context context, @Nullable List<News> data) {
        super(R.layout.knowledge_item, data);
        mContext=context;
    }


    @Override
    protected void convert(BaseViewHolder holder, final News item) {
        holder.setText(R.id.knowledge_title,TextUtils.isEmpty(item.getTitle())?"暂无数据":item.getTitle())
                .setText(R.id.knowledge_h,TextUtils.isEmpty(item.getTag())?"暂无数据":item.getTag());
        ImageView todayPictureNote = holder.getView(R.id.knowledge_im);
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
                Intent intent=new Intent(mContext,ShowKnowledgeActivity.class);
                intent.putExtra("link",item.getLink());
                mContext.startActivity(intent);
            }
        });
    }
}
