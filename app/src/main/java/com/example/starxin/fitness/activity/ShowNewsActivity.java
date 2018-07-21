package com.example.starxin.fitness.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.starxin.fitness.R;

public class ShowNewsActivity extends Activity {

    private ImageView back_iv;//返回
    private WebView wb_show;//网页界面
    private ProgressBar pb_show;//进度条



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        String link = getIntent().getStringExtra("link");//所要打开界面的连接
        if(!link.startsWith("http")){
            StringBuffer stb=new StringBuffer(link);
            stb.insert(0,"http:");
            link= String.valueOf(stb);
        }
//        Toast.makeText(this, link.toString(), Toast.LENGTH_SHORT).show();
//        back_iv=findViewById(R.id.back_iv);
        wb_show=findViewById(R.id.wb_show_activity);
        pb_show=findViewById(R.id.pb_show_activity);
        initToolBar();//初始化ToolBar

        initWebView(link);//初始化WebView

        initWebSettings();

        initWebViewClient();

        initWebChromeClient();

    }

    private void initWebChromeClient() {
        wb_show.setWebChromeClient(new WebChromeClient(){
            private Bitmap mDefaultVideoPoster;//默认得视频展示图

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //设置ToolBar显示加载中
                super.onReceivedTitle(view, title);
                if (wb_show!=null){
                    wb_show.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            }

            public Bitmap getmDefaultVideoPoster() {
                if (mDefaultVideoPoster == null){
                    mDefaultVideoPoster = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    return mDefaultVideoPoster;
                }
                return super.getDefaultVideoPoster();
            }
        });
    }

    private void initWebViewClient() {
        wb_show.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb_show.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_show.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url.toString());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    private void initWebSettings() {
        WebSettings settings = wb_show.getSettings();
        //支持获取手势焦点
        wb_show.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);
        //隐藏原生得缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(wb_show.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19){
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true);
        settings.setSaveFormData(false);

    }

    /**
     * 初始化URL
     * @param link
     */
    private void initWebView(String link) {
        wb_show.loadUrl(link);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
//        back_iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }
}
