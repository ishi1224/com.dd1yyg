package com.ddyyyg.shop.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ddyyyg.app.Constants;
import com.ddyyyg.shop.utils.PayJavaScript;
import com.ddyyyg.shop.R;
import com.ddyyyg.shop.utils.PayWebChromeClient;
import com.ddyyyg.shop.utils.PayWebViewClient;

/**
 * Created by QuestZhang on 16/7/28.
 */

public class MainView extends LinearLayout {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private static final String ERROR_PAGE = "data:text/html,chromewebdata";
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private Context context;
    private ProgressBar mBar;/* 进度条控件 */
    private WebView mWv;
    private Handler mHandler;
    private NavigationBar mNavBar;

    public MainView(Context context) {
        super(context);
        setupViews(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupViews(context);
    }

    public MainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getWebView().loadUrl(Constants.HOST);
        getNavigationBar().setIvLeft(R.mipmap.back_arrow);
        getNavigationBar().setIvRight(R.mipmap.search);

        getNavigationBar().getFlLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getWebView().canGoBack()) {
                    getWebView().goBack();
                }
            }
        });

        getNavigationBar().getFlRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLString = Constants.HOST + Constants.MobileUrl.SEARCH;
                getWebView().loadUrl(URLString);
            }
        });

        getNavigationBar().setLeftInVisible();
    }

    private void setupViews(Context context){
        this.context = context;
        setId(R.id.main);
    }

    public WebView getWebView() {
        if (mWv == null) {
            this.mWv = (WebView) this.findViewById(R.id.wv);
            this.mWv.getSettings().setJavaScriptEnabled(true);
            this.mWv.addJavascriptInterface(new PayJavaScript(context, getHandler()),
                    "payjavascript");
            this.mWv.setWebChromeClient(new PayWebChromeClient(mWv, getProgressBar(), getNavigationBar()));
            this.mWv.setWebViewClient(new PayWebViewClient(mWv,getProgressBar(),getNavigationBar()));
            this.mWv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
            // 开启 DOM storage API 功能
            this.mWv.getSettings().setDomStorageEnabled(true);
            //开启 database storage API 功能
            this.mWv.getSettings().setDatabaseEnabled(true);
            String cacheDirPath = context.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
            //设置  Application Caches 缓存目录
            this.mWv.getSettings().setAppCachePath(cacheDirPath);
            //开启 Application Caches 功能
            this.mWv.getSettings().setAppCacheEnabled(true);
            //this.mWv.loadUrl("file:///android_asset/index.html");
        }
        return mWv;
    }

    public Handler getHandler(){
        if (mHandler == null){
            mHandler = new Handler();
        }
        return mHandler;
    }

    public NavigationBar getNavigationBar() {
        if (mNavBar == null) {
            mNavBar = (NavigationBar) this.findViewById(R.id.nav);
        }
        return mNavBar;
    }

    private ProgressBar getProgressBar(){
        if(mBar == null){
            mBar = (ProgressBar) this.findViewById(R.id.progress);
        }
        return mBar;
    }
}
