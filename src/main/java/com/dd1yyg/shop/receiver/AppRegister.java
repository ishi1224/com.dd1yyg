package com.dd1yyg.shop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dd1yyg.shop.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by QuestZhang on 16/6/11.
 */
public class AppRegister extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        msgApi.registerApp(context.getResources().getString(R.string.APP_ID_WX));
    }
}
