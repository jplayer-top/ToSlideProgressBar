package com.example.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
    private int mColor;
    private int mMax;
    private int mCurProgress;

    public ToSlideProgressBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ToSlideProgressBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.to_slide_bar);//TypedArray是一个数组容器
        mColor = a.getColor(R.styleable.to_slide_bar_bar_color, Color.BLUE);
        mMax = a.getInteger(R.styleable.to_slide_bar_bar_max, 15000);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.view_to_slide_progressbar, null);
        mLeftBar = (ProgressBar) view.findViewById(R.id.progress_left);
        mRightBar = (ProgressBar) view.findViewById(R.id.progress_right);
        mLeftBar.setMax(mMax / 10);
        mRightBar.setMax(mMax / 10);
        mLlBar = (LinearLayout) view.findViewById(R.id.llBar);
        ANIM_TIME = mMax;
        this.removeAllViews();
        this.addView(view);
    }

    /**
     * 设置滚动条动画
     */
    protected int timespace_hanlder = 100;
    private int ANIM_TIME;

    /**
     * 开启动画
     */
    public void setRateWithAnim() {
        this.setRateWithAnim(0, 0);
    }

    public void setRateWithAnim(int leftStart, int rightStart) {
        resetTimer(leftStart/10);
        mLlBar.setVisibility(VISIBLE);
        mLeftBar.setProgress(leftStart);
        mRightBar.setProgress(rightStart);

        leftvalue = mMax / 10;
        rightvalue = mMax / 10;

        leftspeed = leftvalue / ANIM_TIME * timespace_hanlder;//
        rightspeed = rightvalue / ANIM_TIME * timespace_hanlder;

        times_total = ANIM_TIME / timespace_hanlder;//1500
        mTimer = new Timer();
        mTimer.schedule(new BarTimerTask(), 0, timespace_hanlder);
    }

    public void resetTimer() {
        this.resetTimer(0);
    }

    public void resetTimer(int curTime) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
            times_current = curTime;
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

    public void resumeView() {
        setRateWithAnim(mCurProgress, mCurProgress);
    }

    public void pauseView() {
        resetTimer();
        Log.e("Oblivion", "当前的" + mCurProgress);
    }

    public void resetView() {
        resetTimer();
        mLeftBar.setVisibility(VISIBLE);
        mRightBar.setVisibility(VISIBLE);
        setRateWithAnim();
    }

    public void setOnProgresslistener(ProgressListener mListener) {
        this.mListener = mListener;
    }

    protected ProgressListener mListener;

    public interface ProgressListener {
        void onProgressListener(int progress);
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
                    mCurProgress = (int) (times_current * leftspeed);
                    Log.e("obl", times_current + "   " + mCurProgress);
                    mLeftBar.setProgress(mCurProgress);
                    mRightBar.setProgress((int) (times_current * rightspeed));
                    if (mListener != null) {
                        mListener.onProgressListener(mCurProgress);
                    }
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
