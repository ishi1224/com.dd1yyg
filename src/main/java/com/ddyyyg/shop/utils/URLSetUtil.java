package com.ddyyyg.shop.utils;

import com.ddyyyg.app.AppContext;
import com.ddyyyg.app.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuestZhang on 16/7/29.
 */
public class URLSetUtil {

    public static List<String> tabUrls;
    private static URLSetUtil instance;

    private URLSetUtil(){
        super();
    }

    public static URLSetUtil getInstance(){
        if (instance == null) {
            synchronized (AppContext.class) {
                if (instance == null)
                    instance = new URLSetUtil();
                    tabUrls = new ArrayList<String>();
                    initTabUrls();
            }
        }
        return instance;
    }

    private static void initTabUrls() {
        tabUrls.add("data:text/html,chromewebdata");
        tabUrls.add("file:///android_asset/html/error.html");
        tabUrls.add(Constants.HOST+"/");
        tabUrls.add(Constants.HOST);
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.MOBILE+"/");
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.MOBILE);
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.GOODSLIST+"/");
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.GOODSLIST);
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.LOTTERY+"/");
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.LOTTERY);
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.CARTLIST+"/");
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.CARTLIST);
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.LOGINURL+"/");
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.LOGINURL);
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.HOME+"/");
        tabUrls.add(Constants.HOST+ Constants.MobileUrl.HOME);
    }
}
