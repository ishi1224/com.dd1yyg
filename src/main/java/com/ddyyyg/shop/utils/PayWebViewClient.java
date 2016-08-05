package com.ddyyyg.shop.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ddyyyg.shop.view.NavigationBar;

/**
 * Created by QuestZhang on 16/7/29.
 */
public class PayWebViewClient extends WebViewClient {

    private  WebView webView;
    private ProgressBar progressBar;
    private NavigationBar navigationBar;

    public PayWebViewClient(WebView webView, ProgressBar progressBar, NavigationBar navigationBar) {
        super();
        this.webView = webView;
        this.progressBar = progressBar;
        this.navigationBar = navigationBar;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtil.d("webview-url",url);
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);//结束
        Log.d("left", "加载完成:" + url);
        if (URLSetUtil.getInstance().tabUrls.contains(webView.getUrl())) {
            navigationBar.setLeftInVisible();
        } else {
            navigationBar.setLeftVisible();
        }
        navigationBar.setTvTitle(webView.getTitle());
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);//开始
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        Log.d("left", "加载开始:" + url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.loadUrl("file:///android_asset/html/error.html");
        Log.d("加载失败",failingUrl);
    }
}
