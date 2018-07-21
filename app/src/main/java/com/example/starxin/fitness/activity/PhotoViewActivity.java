package com.example.starxin.fitness.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.starxin.fitness.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhotoViewActivity extends Activity {

    @Bind(R.id.photo_view)
    PhotoView photoView;
    @Bind(R.id.photo_view_back_iv)
    ImageView photoViewBackIv;
    @Bind(R.id.photo_view_avi)
    AVLoadingIndicatorView photoViewAvi;

    private String pictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);
        pictureUrl = getIntent().getStringExtra("pictureUrl");
        Glide.with(this)
                .load(pictureUrl)
                .listener(glideRequestListener)
                .crossFade()
                .into(photoView);
    }


    /**
     * Glide的监听
     */
    private RequestListener<String, GlideDrawable> glideRequestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            Toast.makeText(PhotoViewActivity.this, "图片加载失败，请稍后再试。。。", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            loadCompleted();
            return false;
        }
    };


    /**
     * 图片加载完成隐藏loading和显示图片
     */
    private void loadCompleted() {
        photoViewAvi.setVisibility(View.GONE);
        photoView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.photo_view_back_iv)
    public void onClick() {
        finish();
    }
}
