package com.ddyyyg.shop.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by QuestZhang on 16/6/27.
 */
public class SPUtils {

    public static void putString(Context context,String key,String value){
        //实例化SharedPreferences对象（第一步）
        SharedPreferences sp = context.getSharedPreferences("share_data",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = sp.edit();
        //用putString的方法保存数据
        editor.putString(key, value);
        //提交当前数据
        editor.commit();
    }

    public static String getString(Context context,String key,String defaultValue){

        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sp= context.getSharedPreferences("share_data",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String string =sp.getString(key, defaultValue);
        return string;
    }
}
