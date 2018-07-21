package com.example.starxin.fitness.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.starxin.fitness.R;
import com.example.starxin.fitness.activity.AddKeepFitNoteActivity;
import com.example.starxin.fitness.activity.PhotoViewActivity;
import com.example.starxin.fitness.domain.KeepNoteEntity;
import com.example.starxin.fitness.utils.DecimalUtils;

import java.util.List;

/**
 * Created by Sean on 17/5/22.
 */

public class KeepFitAdapter extends BaseQuickAdapter<KeepNoteEntity, BaseViewHolder> {


    private Context mContext;

    public KeepFitAdapter(Context context, @Nullable List<KeepNoteEntity> data) {
        super(R.layout.keep_list_list_item, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder holder, final KeepNoteEntity item) {
        holder.setText(R.id.item_date, TextUtils.isEmpty(item.getDate()) ? "暂无数据" : item.getDate())
                .setText(R.id.item_exercise_length_tv, TextUtils.isEmpty(item.getExerciseDuration().toString()) ? "暂无数据" : DecimalUtils.formatDecimalWithZero2(item.getExerciseDuration()) + " (h)")
                .setText(R.id.item_run_length_tv, TextUtils.isEmpty(item.getRunLength().toString()) ? "暂无数据" : DecimalUtils.formatDecimalWithZero2(item.getRunLength()) + " (km)")
                .setText(R.id.item_situp_tv, TextUtils.isEmpty(item.getSitUps().toString()) ? "暂无数据" : item.getSitUps().toString() + " 个")
                .setText(R.id.item_sports_apparatus_tv, TextUtils.isEmpty(item.getSportsApparatusTimes().toString()) ? "暂无数据" : item.getSportsApparatusTimes().toString() + " 组");
        ImageView todayPictureNote = holder.getView(R.id.item_today_picture);
        if (item.getPictures() != null) {
            Glide.with(mContext)
                    .load(item.getPictures().get(0))
                    .placeholder(R.drawable.head)
                    .dontAnimate()
                    .thumbnail(0.1f)
                    .into(todayPictureNote);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddKeepFitNoteActivity.class);
                intent.putExtra("objectId", item.getObjectId());
                mContext.startActivity(intent);
            }
        });
        holder.setOnClickListener(R.id.item_today_picture, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoViewActivity.class);
                if (item.getPictures() != null && item.getPictures().size() > 0) {
                    intent.putExtra("pictureUrl", item.getPictures().get(0));
                } else {
                    intent.putExtra("pictureUrl", "http://bmob-cdn-12073.b0.upaiyun.com/2017/06/21/a3f32615604747338c388c5678d3ca07.jpg");
                }
                mContext.startActivity(intent);
            }
        });
    }
}
