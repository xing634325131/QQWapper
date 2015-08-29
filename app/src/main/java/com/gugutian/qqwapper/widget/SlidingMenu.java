package com.gugutian.qqwapper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.gugutian.qqwapper.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by blueleaf on 15-8-29.
 */
public class SlidingMenu extends HorizontalScrollView{

    private LinearLayout mWapper;
    private ViewGroup mLeftMenuVg;
    private ViewGroup mMainContentVg;

    private boolean mOnce = false;

    private int mScreenWidth;
    // dp
    private int mLeftMenuPaddingRight = 50;
    private int mLeftMenuWidth;

    private boolean mIsLeftMenuShown = false;

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        mLeftMenuPaddingRight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                mLeftMenuPaddingRight,
                context.getResources().getDisplayMetrics());

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        for(int i = 0, n = typedArray.getIndexCount(); i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    mLeftMenuPaddingRight = typedArray.getDimensionPixelSize(attr, (int)TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            mLeftMenuPaddingRight,
                            context.getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();

    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!mOnce) {
            mWapper = (LinearLayout) getChildAt(0);
            mLeftMenuVg = (ViewGroup) mWapper.getChildAt(0);
            mMainContentVg = (ViewGroup) mWapper.getChildAt(1);
            mLeftMenuWidth = mLeftMenuVg.getLayoutParams().width = mScreenWidth - mLeftMenuPaddingRight;
            mMainContentVg.getLayoutParams().width = mScreenWidth;
            mOnce = !mOnce;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) {
            scrollTo(mLeftMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX >= mLeftMenuWidth / 2) {
                    smoothScrollTo(mLeftMenuWidth, 0);
                    mIsLeftMenuShown = false;
                } else {
                    smoothScrollTo(0, 0);
                    mIsLeftMenuShown = true;
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }

    public void showLeftMenu() {
        if(!isLeftMenuShown()) {
            smoothScrollTo(0, 0);
            mIsLeftMenuShown = true;
        }
    }

    public void hideLeftMenu() {
        if(isLeftMenuShown()) {
            smoothScrollTo(mLeftMenuWidth, 0);
            mIsLeftMenuShown = false;
        }
    }

    public boolean isLeftMenuShown() {
        return mIsLeftMenuShown;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);


        float scale = l * 1.0f / mLeftMenuWidth;//1.0~0 注意1.0f的乘法，使之成为float类型
        float contentScale = 0.8f + 0.2f * scale;
        ViewHelper.setPivotX(mMainContentVg, 0);
        ViewHelper.setPivotY(mMainContentVg, mMainContentVg.getHeight() / 2);
        ViewHelper.setScaleX(mMainContentVg, contentScale);
        ViewHelper.setScaleY(mMainContentVg, contentScale);

        float menuTranslationShow = 0.7f;
        ViewHelper.setTranslationX(mLeftMenuVg, l * menuTranslationShow);
        float menuScale = 1.0f - 0.2f * scale;
        ViewHelper.setScaleX(mLeftMenuVg, menuScale);
        ViewHelper.setScaleY(mLeftMenuVg, menuScale);
        float menuAlphaScale = 1.0f - 0.5f * scale;
        ViewHelper.setAlpha(mLeftMenuVg, menuAlphaScale);
    }
}
