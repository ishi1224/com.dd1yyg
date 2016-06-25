package com.ddyyyg.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ddyyyg.shop.utils.ToastUtil;
import com.ddyyyg.shop.view.NavigationBar;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

//http://weixin.dd1yyg.com/?/mobile/mobile/(1)
//http://weixin.dd1yyg.com/?/mobile/mobile/glist(2)
//http://weixin.dd1yyg.com/?/mobile/mobile/lottery(3)
//http://weixin.dd1yyg.com/?/mobile/cart/cartlist(4)
//http://weixin.dd1yyg.com/?/mobile/user/login(5)|http://weixin.dd1yyg.com/?/mobile/home/(5)

public class MainActivity extends Activity {

    private static final String ERROR_PAGE = "data:text/html,chromewebdata";
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private ProgressBar mBar;/* 进度条控件 */
    private WebView mWv;
    private Handler mHandler;
    private List<String> tabUrls;
    private NavigationBar mNavBar;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI iwxapi;
    private boolean lock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regToWx();
        getmWv().loadUrl(Constants.HOST);
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

    public boolean isPaySupported(){
        boolean isPaySupported = getWxapi().getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {
            ToastUtil.makeText(MainActivity.this, "微信版本不支持支付功能");
        }
        return  isPaySupported;
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

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            super.onConsoleMessage(consoleMessage);
            if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                Log.d("Level-Url",getmWv().getUrl());
            }
            return true;
        }
    }

    private class PayWebViewClient extends WebViewClient {

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
            Log.d("left", "加载开始:" + url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.loadUrl("file:///android_asset/html/error.html");
            Log.d("加载失败",failingUrl);
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
            this.mWv.addJavascriptInterface(new PayJavaScript(this, getmHandler()),
                    "payjavascript");
            this.mWv.setWebChromeClient(new PayWebChromeClient());
            this.mWv.setWebViewClient(new PayWebViewClient());

            this.mWv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
            // 开启 DOM storage API 功能
            this.mWv.getSettings().setDomStorageEnabled(true);
            //开启 database storage API 功能
            this.mWv.getSettings().setDatabaseEnabled(true);
            String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
            //设置  Application Caches 缓存目录
            this.mWv.getSettings().setAppCachePath(cacheDirPath);
            //开启 Application Caches 功能
            this.mWv.getSettings().setAppCacheEnabled(true);
            //this.mWv.loadUrl("file:///android_asset/index.html");
        }
        return mWv;
    }

    public Handler getmHandler(){
        if (mHandler == null){
            mHandler = new Handler();
        }
        return mHandler;
    }

    public NavigationBar getmNavBar() {
        if (mNavBar == null) {
            mNavBar = (NavigationBar) findViewById(R.id.nav);
        }
        return mNavBar;
    }

    public IWXAPI getWxapi() {
        if (iwxapi == null){
            regToWx();
        }
        return iwxapi;
    }

    private void regToWx(){
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        iwxapi = WXAPIFactory.createWXAPI(this, getResources().getString(R.string.APP_ID_WX),true);
        iwxapi.registerApp(getResources().getString(R.string.APP_ID_WX));
    }

    public boolean isLock() {
        return !lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public List<String> getTabUrls() {
        if (tabUrls == null) {
            tabUrls = new ArrayList<String>();
            tabUrls.add("data:text/html,chromewebdata");
            tabUrls.add("file:///android_asset/html/error.html");
            tabUrls.add(Constants.HOST+"/");
            tabUrls.add(Constants.HOST);
            tabUrls.add(Constants.HOST+"/?/mobile/mobile/");
            tabUrls.add(Constants.HOST+"/?/mobile/mobile");
            tabUrls.add(Constants.HOST+"/?/mobile/mobile/glist/");
            tabUrls.add(Constants.HOST+"/?/mobile/mobile/glist");
            tabUrls.add(Constants.HOST+"/?/mobile/mobile/lottery/");
            tabUrls.add(Constants.HOST+"/?/mobile/mobile/lottery");
            tabUrls.add(Constants.HOST+"/?/mobile/cart/cartlist/");
            tabUrls.add(Constants.HOST+"/?/mobile/cart/cartlist");
            tabUrls.add(Constants.HOST+"/?/mobile/user/login/");
            tabUrls.add(Constants.HOST+"/?/mobile/user/login");
            tabUrls.add(Constants.HOST+"/?/mobile/home/");
            tabUrls.add(Constants.HOST+"/?/mobile/home");
        }
        return tabUrls;
    }
}
