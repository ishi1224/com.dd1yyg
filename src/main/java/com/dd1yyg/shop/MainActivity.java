package com.dd1yyg.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//http://weixin.dd1yyg.com/?/mobile/mobile/(1)
//http://weixin.dd1yyg.com/?/mobile/mobile/glist(2)
//http://weixin.dd1yyg.com/?/mobile/mobile/lottery(3)
//http://weixin.dd1yyg.com/?/mobile/cart/cartlist(4)
//http://weixin.dd1yyg.com/?/mobile/user/login(5)|http://weixin.dd1yyg.com/?/mobile/home/(5)

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private ProgressBar mBar;/* 进度条控件 */
    private WebView mWv;
    private Handler mHandler;
    private List<String> tabUrls;
    private Navigationbar mNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        getmWv().loadUrl("http://weixin.dd1yyg.com");
        getmNavBar().setIvLeft(R.mipmap.back_arrow);
        getmNavBar().getFlLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getmWv().canGoBack()) {
                    getmWv().goBack();
                }
            }
        });
        getmNavBar().setLeftInVisible();
    }

    private class PayWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            getmNavBar().setTvTitle(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            getmBar().setProgress(newProgress);
            if (newProgress == 100) {
                getmBar().setVisibility(View.GONE);
            }
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示" )
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            return true;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            super.onJsAlert(view,url,message,result);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示" )
                    .setMessage(message)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            result.cancel();
                        }
                    })
                    .show();
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }

    private class PayWebViewClient extends WebViewClient {

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("url", url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);//结束
            Log.d("left", "加载完成:" + url);
            if (getTabUrls().contains(url)) {
                getmNavBar().setLeftInVisible();
            } else {
                getmNavBar().setLeftVisible();
            }
            getmNavBar().setTvTitle(getmWv().getTitle());
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);//开始
            getmBar().setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            getmBar().setVisibility(View.GONE);
        }
    }

    /**
     * 点击两次返回键退出应用程序
     */
    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isIndex() || !getmWv().canGoBack()) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    this.finish();
                    System.exit(0);
                }
            } else {
                getmWv().goBack();
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isIndex() {
        return getTabUrls().contains(getmWv().getUrl());
    }

    private ProgressBar getmBar(){
        if(mBar == null){
            mBar = (ProgressBar) findViewById(R.id.progress);
        }
        return mBar;
    }

    private WebView getmWv() {
        if (mWv == null) {
            this.mWv = (WebView) findViewById(R.id.wv);
            this.mWv.getSettings().setJavaScriptEnabled(true);
            this.mWv.addJavascriptInterface(new PayJavaScript(this, mHandler),
                    "payjavascript");
            this.mWv.setWebChromeClient(new PayWebChromeClient());
            this.mWv.setWebViewClient(new PayWebViewClient());

            this.mWv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
            // 开启 DOM storage API 功能
            this.mWv.getSettings().setDomStorageEnabled(true);
            //开启 database storage API 功能
            this.mWv.getSettings().setDatabaseEnabled(true);
            String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
            Log.i(TAG, "cacheDirPath=" + cacheDirPath);
            //设置  Application Caches 缓存目录
            this.mWv.getSettings().setAppCachePath(cacheDirPath);
            //开启 Application Caches 功能
            this.mWv.getSettings().setAppCacheEnabled(true);
            //this.mWv.loadUrl("file:///android_asset/index.html");
        }
        return mWv;
    }

    public Navigationbar getmNavBar() {
        if (mNavBar == null) {
            mNavBar = (Navigationbar) findViewById(R.id.nav);
        }
        return mNavBar;
    }

    public List<String> getTabUrls() {
        if (tabUrls == null) {
            tabUrls = new ArrayList<String>();
            tabUrls.add("http://weixin.dd1yyg.com/");
            tabUrls.add("http://weixin.dd1yyg.com");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/mobile/");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/mobile");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/mobile/glist/");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/mobile/glist");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/mobile/lottery/");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/mobile/lottery");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/cart/cartlist/");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/cart/cartlist");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/user/login/");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/user/login");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/home/");
            tabUrls.add("http://weixin.dd1yyg.com/?/mobile/home");
        }
        return tabUrls;
    }
}
