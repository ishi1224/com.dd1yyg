package com.ddyyyg.shop.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.ddyyyg.shop.Constants;
import com.ddyyyg.shop.PayJavaScript;
import com.ddyyyg.shop.R;
import com.ddyyyg.shop.utils.ToastUtil;
import com.ddyyyg.shop.view.NavigationBar;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

//http://weixin.dd1yyg.com/?/mobile/mobile/(1)
//http://weixin.dd1yyg.com/?/mobile/mobile/glist(2)
//http://weixin.dd1yyg.com/?/mobile/mobile/lottery(3)
//http://weixin.dd1yyg.com/?/mobile/cart/cartlist(4)
//http://weixin.dd1yyg.com/?/mobile/user/login(5)|http://weixin.dd1yyg.com/?/mobile/home/(5)

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private static final String ERROR_PAGE = "data:text/html,chromewebdata";
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private ProgressBar mBar;/* 进度条控件 */
    private WebView mWv;
    private Handler mHandler;
    private List<String> tabUrls;
    private NavigationBar mNavBar;
    private IWXAPI iwxapi;
    private boolean lock;

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_tip);
            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
            builder.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regToWx();
        getWebView().loadUrl(Constants.HOST);
        getNavigationBar().setIvLeft(R.mipmap.back_arrow);
        getNavigationBar().getFlLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getWebView().canGoBack()) {
                    getWebView().goBack();
                }
            }
        });
        getNavigationBar().setLeftInVisible();
        getWxapi().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getWxapi().handleIntent(intent, this);
    }

    public boolean isPaySupported(){
        boolean isPaySupported = getWxapi().getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {
            ToastUtil.makeText(WXPayEntryActivity.this, "微信版本不支持支付功能");
        }
        return  isPaySupported;
    }

    private class PayWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            getNavigationBar().setTvTitle(title);
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
            new AlertDialog.Builder(WXPayEntryActivity.this)
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
            new AlertDialog.Builder(WXPayEntryActivity.this)
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
                Log.d("Level-Url", getWebView().getUrl());
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
                getNavigationBar().setLeftInVisible();
            } else {
                getNavigationBar().setLeftVisible();
            }
            getNavigationBar().setTvTitle(getWebView().getTitle());
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
            if (isIndex() || !getWebView().canGoBack()) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    this.finish();
                    System.exit(0);
                }
            } else {
                getWebView().goBack();
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isIndex() {
        return getTabUrls().contains(getWebView().getUrl());
    }

    private ProgressBar getmBar(){
        if(mBar == null){
            mBar = (ProgressBar) findViewById(R.id.progress);
        }
        return mBar;
    }

    private WebView getWebView() {
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

    public NavigationBar getNavigationBar() {
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
