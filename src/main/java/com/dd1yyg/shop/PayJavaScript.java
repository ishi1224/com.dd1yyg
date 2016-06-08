package com.dd1yyg.shop;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;


/**
 * Created by QuestZhang on 16/6/5.
 */
public class PayJavaScript {

    private Context mContext;
    private Handler mHandler;
    private MainActivity main;

    public PayJavaScript(Context context, Handler handler) {
        super();
        this.mContext = context;
        this.mHandler = handler;
        this.main = (MainActivity) context;
    }

    /*
    '{
    "appId":"wx0952813220b9ef2b",
    "timeStamp":"1465137465",
    "nonceStr":"120flpvpu9rndbrku3hjz6hayb8gu6yb",
    "package":"prepay_id=",
    "signType":"MD5",
    "paySign":"A482B0C0FE1E8B3DC9D8779329EBD815"
    }'
    * */
    @JavascriptInterface
    public void payWx(String json) {//javascript:payjavascript.payWX
        Log.d("javascript",json);
    }

    @JavascriptInterface
    public void payAlipay(String json) {//javascript:payjavascript.payAlipay


    }

}
