package com.example.library;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Obl on 2017/6/6.
 * com.ilanchuang.xiaoi.views
 */

public class ToSlideProgressBar extends FrameLayout {
    private int times_total;
    private int times_current;
    private float leftvalue, rightvalue;
    private float leftspeed, rightspeed;
    private Timer mTimer;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private LinearLayout mLlBar;
    private ProgressBar mLeftBar;
    private ProgressBar mRightBar;

    public ToSlideProgressBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ToSlideProgressBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.view_to_slide_progressbar, null);
        mLeftBar = (ProgressBar) view.findViewById(R.id.progress_left);
        mRightBar = (ProgressBar) view.findViewById(R.id.progress_right);
        mLlBar = (LinearLayout) view.findViewById(R.id.llBar);
        this.removeAllViews();
        this.addView(view);
    }

    /**
     * 设置滚动条动画
     */
    protected int timespace_hanlder = 100;
    private static final int ANIM_TIME = 15000;

    /**
     * 开启动画
     */
    public void setRateWithAnim() {
        resetTimer();
        mLlBar.setVisibility(VISIBLE);
        mLeftBar.setProgress(0);
        mRightBar.setProgress(0);

        leftvalue = 1500;
        rightvalue = 1500;

        leftspeed = leftvalue / ANIM_TIME * timespace_hanlder;//
        rightspeed = rightvalue / ANIM_TIME * timespace_hanlder;

        times_total = ANIM_TIME / timespace_hanlder;//1500
        mTimer = new Timer();
        mTimer.schedule(new BarTimerTask(), 0, timespace_hanlder);
    }

    public void resetTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
            times_current = 0;
        }
    }

    public void barShow(boolean isThread) {
        if (isThread) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mLlBar.setVisibility(View.GONE);
                }
            });
        } else {
            mLlBar.setVisibility(View.GONE);
        }
    }

    public void goneLeft() {
        resetTimer();
        mRightBar.setVisibility(VISIBLE);
        mLeftBar.setVisibility(GONE);
        setRateWithAnim();
    }

    public void goneRight() {
        resetTimer();
        mLeftBar.setVisibility(VISIBLE);
        mRightBar.setVisibility(GONE);
        setRateWithAnim();
    }

    public void resetView() {
        resetTimer();
        mLeftBar.setVisibility(VISIBLE);
        mRightBar.setVisibility(VISIBLE);
        setRateWithAnim();
    }

    /**
     * 时间任务
     */
    private class BarTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    times_current++;
                    Log.e("obl", times_current + "   " + (int) (times_current * leftspeed));
                    mLeftBar.setProgress((int) (times_current * leftspeed));
                    mRightBar.setProgress((int) (times_current * rightspeed));
                    if (times_current >= times_total) {
                        mLeftBar.setProgress((int) leftvalue);
                        mRightBar.setProgress((int) leftvalue);
                        resetTimer();
                    }
                }
            });
        }
    }

}
