package com.ddyyyg.shop.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ddyyyg.app.App;
import com.ddyyyg.shop.view.NavigationBar;
import com.ddyyyg.shop.wxapi.WXPayEntryActivity;

/**
 * Created by QuestZhang on 16/7/29.
 */
public class PayWebChromeClient extends WebChromeClient {

    public final static int FILECHOOSER_RESULTCODE = 1;
    public static ValueCallback<Uri> mUploadMessage;
    private WebView webView;
    private ProgressBar progressBar;
    private NavigationBar navigationBar;



    public PayWebChromeClient(WebView webView, ProgressBar progressBar, NavigationBar navigationBar) {
        super();
        this.webView = webView;
        this.progressBar = progressBar;
        this.navigationBar = navigationBar;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        navigationBar.setTvTitle(title);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        progressBar.setProgress(newProgress);
        if (newProgress == 100) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        new AlertDialog.Builder(webView.getContext())
                .setTitle("提示")
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
        new AlertDialog.Builder(this.webView.getContext())
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

    //扩展浏览器上传文件
    //3.0++版本
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        ((WXPayEntryActivity)App.getApplication().getApplicationContext()).startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        super.onConsoleMessage(consoleMessage);
        if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
            Log.d("Level-Url", webView.getUrl());
        }
        return true;
    }
}
