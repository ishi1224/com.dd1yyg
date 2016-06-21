package com.ddyyyg.shop.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by QuestZhang on 16/6/16.
 */
public class ToastUtil {

    public static void makeText(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
