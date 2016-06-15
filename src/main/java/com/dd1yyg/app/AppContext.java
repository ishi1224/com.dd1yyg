package com.dd1yyg.app;

/**
 * Created by QuestZhang on 16/6/12.
 */
public class AppContext {

    private static AppContext mInstance;

    private AppContext(){super();}

    public static AppContext getInstance() {

        if (mInstance == null) {
            synchronized (AppContext.class) {
                if (mInstance == null)
                    mInstance = new AppContext();
            }
        }
        return mInstance;
    }

}
