package com.dd1yyg.app;

/**
 * Created by QuestZhang on 16/6/12.
 */
public class AppContext {

    private AppContext context;

    private AppContext(){super();}

    public AppContext getInstance(){
        if (context == null){
            context = new AppContext();
        }
        return context;
    }


}
