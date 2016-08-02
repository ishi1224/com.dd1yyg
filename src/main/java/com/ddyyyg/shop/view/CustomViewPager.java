package com.ddyyyg.shop.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.ddyyyg.shop.utils.ScreenUtil;

/**
 * Created by QuestZhang on 16/8/1.
 */
public class CustomViewPager extends ViewPager {

    private Context context;

    private boolean isCanScroll = true;

    public CustomViewPager(Context context) {

        super(context);

        setupViews(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

        setupViews(context);
    }

    private void setupViews(Context context){

        this.context = context;
    }

    public void setScanScroll(boolean isCanScroll){

        this.isCanScroll = isCanScroll;
    }

    @Override

    public void scrollTo(int x, int y){

        if (isCanScroll){

            super.scrollTo(x, y);

            if (x == ScreenUtil.getScreenWidth(context)){

                isCanScroll = false;
            }
        }
    }
}
