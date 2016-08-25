package com.ddyyyg.app;

import android.app.Activity;
import android.app.Application;

import com.ddyyyg.shop.utils.LogUtil;
import com.github.yoojia.anyversion.AnyVersion;
import com.github.yoojia.anyversion.Version;
import com.github.yoojia.anyversion.VersionParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by QuestZhang on 16/6/12.
 */
public class App extends Application {

    private static App mApp;
    private static ExecutorService mExecutorService;
    private static int MAX_THREAD_NUM = 5;
    private final static int REQUEST_ENABLE_BT = 1;

    private Stack<Activity> mActivityStack = new Stack<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("Application", "app start...");
        mApp = this;
        AnyVersion.init(this, new VersionParser() {
            @Override
            public Version onParse(String response) {
                final JSONTokener tokener = new JSONTokener(response);
                try {
                    JSONObject json = (JSONObject) tokener.nextValue();
                    String name = new String(json.getString("name").getBytes("UTF-8"));
                    String note = new String(json.getString("note").getBytes("UTF-8"));
                    String url = new String(json.getString("url").getBytes("UTF-8"));
                    int code = json.getInt("code");
                    return new Version(name, note, url, code);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
    /**
     * 获取Application上下文
     * @return
     */
    public static App getApplication(){
        return mApp;
    }
    /**
     * 获取线程池对象，最多5个线程
     * @return
     */
    public static ExecutorService getThreadPool(){
        if(mExecutorService == null){
            mExecutorService = Executors.newFixedThreadPool(MAX_THREAD_NUM);
        }
        return mExecutorService;
    }
}
