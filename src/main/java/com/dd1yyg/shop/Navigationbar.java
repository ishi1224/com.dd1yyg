package com.dd1yyg.shop;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by QuestZhang on 16/6/7.
 */
public class Navigationbar extends LinearLayout {

    private Context context;
    private FrameLayout flLeft;
    private TextView tvLeft;
    private ImageView ivLeft;
    private FrameLayout flTitle;
    private TextView tvTitle;
    private ImageView ivTitle;
    private FrameLayout flRight;
    private TextView tvRight;
    private ImageView ivRight;
    private static final int WIDTH = 48;
    private static final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private static final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    public Navigationbar(Context context) {
        super(context);
        setupViews(context);
    }

    public Navigationbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupViews(context);
    }

    private void setupViews(Context context) {
        this.context = context;
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.parseColor("#1b1a1f"));
        setGravity(Gravity.CENTER_VERTICAL);
        addView(getFlLeft());
        addView(getFlTitle());
        addView(getFlRight());
    }

    public void setTvLeft(String name) {
        getTvLeft().setText(name);
        if (getFlLeft().getChildCount() > 0) {
            getFlLeft().removeAllViews();
        }
        getFlLeft().addView(getTvLeft());
    }

    public void setIvLeft(int resid) {
        getIvLeft().setImageResource(resid);
        if (getFlLeft().getChildCount() > 0) {
            getFlLeft().removeAllViews();
        }
        getFlLeft().addView(getIvLeft());
    }

    public void setTvTitle(String name) {
        getTvTitle().setText(name);
        if (getFlTitle().getChildCount() > 0) {
            getFlTitle().removeAllViews();
        }
        getFlTitle().addView(getTvTitle());
    }

    public void setIvTitle(int resid) {
        getIvTitle().setImageResource(resid);
        if (getFlTitle().getChildCount() > 0) {
            getFlTitle().removeAllViews();
        }
        getFlTitle().addView(getIvTitle());
    }

    public void setTvRight(String name) {
        getTvRight().setText(name);
        if (getFlRight().getChildCount() > 0) {
            getFlRight().removeAllViews();
        }
        getFlRight().addView(getTvRight());
    }

    public void setIvRight(int resid) {
        getIvRight().setImageResource(resid);
        if (getFlRight().getChildCount() > 0) {
            getFlRight().removeAllViews();
        }
        getFlRight().addView(getIvRight());
    }

    public FrameLayout getFlLeft() {
        if (flLeft == null) {
            flLeft = new FrameLayout(context);
            int width = DensityUtil.dip2px(context, WIDTH);
            int height = DensityUtil.dip2px(context, 48);
            flLeft.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            flLeft.setBackgroundColor(Color.parseColor("#00000000"));
        }
        return flLeft;
    }

    public TextView getTvLeft() {
        if (tvLeft == null) {
            tvLeft = new TextView(context);
            tvLeft.setLayoutParams(new LinearLayout.LayoutParams(MP, MP));
            tvLeft.setTextColor(Color.parseColor("#FFFFFF"));
            tvLeft.setBackgroundColor(Color.parseColor("#00000000"));
            tvLeft.setGravity(Gravity.CENTER);
        }
        return tvLeft;
    }

    public ImageView getIvLeft() {
        if (ivLeft == null) {
            ivLeft = new ImageView(context);
            ivLeft.setLayoutParams(new LinearLayout.LayoutParams(MP, MP));
            ivLeft.setBackgroundColor(Color.parseColor("#00000000"));
            ivLeft.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        return ivLeft;
    }

    public FrameLayout getFlTitle() {
        if (flTitle == null) {
            flTitle = new FrameLayout(context);
            int height = DensityUtil.dip2px(context, 48);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WC, height);
            params.weight = 1;
            flTitle.setLayoutParams(params);
            flTitle.setBackgroundColor(Color.parseColor("#00000000"));
        }
        return flTitle;
    }

    public TextView getTvTitle() {
        if (tvTitle == null) {
            tvTitle = new TextView(context);
            tvTitle.setLayoutParams(new LinearLayout.LayoutParams(MP, MP));
            tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
            tvTitle.setBackgroundColor(Color.parseColor("#00000000"));
            tvTitle.setGravity(Gravity.CENTER);
        }
        return tvTitle;
    }


    public ImageView getIvTitle() {
        if (ivTitle == null) {
            ivTitle = new ImageView(context);
            ivTitle.setLayoutParams(new LinearLayout.LayoutParams(MP, MP));
            ivTitle.setBackgroundColor(Color.parseColor("#00000000"));
            ivTitle.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        return ivTitle;
    }

    public FrameLayout getFlRight() {
        if (flRight == null) {
            flRight = new FrameLayout(context);
            int width = DensityUtil.dip2px(context, WIDTH);
            int height = DensityUtil.dip2px(context, 48);
            flRight.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            flRight.setBackgroundColor(Color.parseColor("#00000000"));
        }
        return flRight;
    }

    public TextView getTvRight() {
        if (tvRight == null) {
            tvRight = new TextView(context);
            tvRight.setLayoutParams(new LinearLayout.LayoutParams(MP, MP));
            tvRight.setTextColor(Color.parseColor("#FFFFFF"));
            tvRight.setBackgroundColor(Color.parseColor("#00000000"));
            tvRight.setGravity(Gravity.CENTER);
        }
        return tvRight;
    }

    public ImageView getIvRight() {
        if (ivRight == null) {
            ivRight = new ImageView(context);
            ivRight.setLayoutParams(new LinearLayout.LayoutParams(MP, MP));
            ivRight.setBackgroundColor(Color.parseColor("#00000000"));
            ivRight.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
        return ivRight;
    }

    public void setLeftInVisible() {
        getFlLeft().setVisibility(View.INVISIBLE);
    }

    public void setTitleInVisible() {
        getFlTitle().setVisibility(View.INVISIBLE);
    }

    public void setRightInVisible() {
        getFlRight().setVisibility(View.INVISIBLE);
    }

    public void setLeftVisible() {
        getFlLeft().setVisibility(View.VISIBLE);
    }

    public void setTitleVisible() {
        getFlTitle().setVisibility(View.VISIBLE);
    }

    public void setRightVisible() {
        getFlRight().setVisibility(View.VISIBLE);
    }
}
