package com.dd1yyg.app;

import android.app.Activity;
import android.app.Application;

import com.dd1yyg.shop.utils.LogUtil;

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
